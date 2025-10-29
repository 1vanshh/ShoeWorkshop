package org.example.repository;

import org.example.db.DatabaseConfig;
import org.example.db.DatabaseConnection;
import org.example.entities.Favour;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

public class  FavourRepositoryImpl implements FavourRepository {

    private final String INSERT_SQL = "INSERT INTO favours(favour_name, base_price) VALUES (?, ?)";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM favours WHERE favour_id = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM favours ORDER BY favour_id";
    private final String UPDATE_SQL = "UPDATE favours SET favour_name = ?, base_price = ? WHERE favour_id = ?";
    private final String DELETE_SQL = "DELETE FROM favours WHERE favour_id = ?";

    @Override
    public Favour findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFavour(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Favour> findAll() {
        List<Favour> favours = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                favours.add(mapFavour(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return favours;
    }

    @Override
    public void add(Favour object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.toString());
            ps.setDouble(2, object.getBasePrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Favour newObject) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, newObject.toString());
            ps.setDouble(2, newObject.getBasePrice());
            ps.setInt(3, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Favour mapFavour(ResultSet rs) throws SQLException {
        Favour favour = new Favour();
        favour.setFavourId(rs.getInt("favour_id"));
        favour.setFavourName(rs.getString("favour_name"));
        favour.setBasePrice(rs.getDouble("base_price"));
        return favour;
    }
}
