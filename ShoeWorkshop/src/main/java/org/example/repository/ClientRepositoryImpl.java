package org.example.repository;

import org.example.db.DatabaseConfig;
import org.example.db.DatabaseConnection;
import org.example.entities.Client;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

public class ClientRepositoryImpl implements ClientRepository {

    private final String INSERT_SQL = "INSERT INTO clients(full_name, address, phone, email) VALUES (?,?,?,?)";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM clients WHERE client_id = ?";
    private final String SELECT_BY_EMAIL_SQL = "SELECT * FROM clients WHERE email = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM clients ORDER BY client_id";
    private final String UPDATE_SQL = "UPDATE clients SET full_name = ?, phone = ?, email = ?, address = ? WHERE client_id = ?";
    private final String DELETE_SQL = "DELETE FROM clients WHERE client_id = ?";

    public ClientRepositoryImpl() {

    }

    @Override
    public Client findByEmail(String email) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Client findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clients.add(mapClient(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    @Override
    public void add(Client object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.getFullName());
            ps.setString(2, object.getAddress());
            ps.setString(3, object.getPhoneNumber());
            ps.setString(4, object.getEmail());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Client newObject) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, newObject.getFullName());
            ps.setString(2, newObject.getPhoneNumber());
            ps.setString(3, newObject.getEmail());
            ps.setString(4, newObject.getAddress());
            ps.setInt(5, id);

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

    private Client mapClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setClientId(rs.getInt("client_id"));
        client.setFullName(rs.getString("full_name"));
        client.setAddress(rs.getString("phone"));
        client.setEmail(rs.getString("email"));
        client.setAddress(rs.getString("address"));
        return client;
    }
}
