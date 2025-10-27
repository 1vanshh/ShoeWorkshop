package org.example.repository;

import org.example.db.DatabaseConnection;
import org.example.entities.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatusRepositoryImpl implements OrderStatusRepository {

    private String SELECT_BY_ID_SQL = "SELECT * FROM order_status WHERE status_id = ?";
    private String SELECT_ALL_SQL = "SELECT * FROM order_status";
    private String INSERT_SQL = "INSERT INTO order_status VALUES (?)";
    private String UPDATE_SQL = "UPDATE order_status SET status_name = ? WHERE status_id = ?";
    private String DELETE_SQL = "DELETE FROM order_status WHERE status_id = ?";

    @Override
    public OrderStatus findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapOrderStatus(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<OrderStatus> findAll() {
        List <OrderStatus> orderStatuses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orderStatuses.add(mapOrderStatus(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderStatuses;
    }

    @Override
    public void add(OrderStatus object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            stmt.setString(1, object.getStatusName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, OrderStatus newObject) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, newObject.getStatusName());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderStatus mapOrderStatus(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatusId(rs.getInt("status_id"));
        orderStatus.setStatusName(rs.getString("status_name"));
        return orderStatus;
    }
}
