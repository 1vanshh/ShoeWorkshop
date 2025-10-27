package org.example.repository;

import org.example.db.DatabaseConnection;
import org.example.entities.Receipt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptRepositoryImpl implements ReceiptRepository {

    private String SELECT_BY_CLIENT_SQL = "SELECT * FROM receipts WHERE client_id = ?";
    private String UPDATE_STATUS_SQL = "UPDATE receipts SET status_id = ? WHERE receipt_id = ?";
    private String SELECT_BY_ID_SQL = "SELECT * FROM receipts WHERE receipt_id = ?";
    private String SELECT_ALL_SQL = "SELECT * FROM receipts";
    private String INSERT_SQL = "INSERT INTO receipts (client_id, status_id, order_date) VALUES (?, ?, ?)";
    private String UPDATE_SQL = "UPDATE receipts SET client_id = ?, status_id = ?, order_date = ? WHERE id = ?";
    private String DELETE_SQL = "DELETE FROM receipts WHERE receipt_id = ?";

    @Override
    public List<Receipt> findByClientId(int clientId) {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CLIENT_SQL)) {

            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    receipts.add(mapReceipt(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receipts;
    }

    @Override
    public void updateStatus(int receiptId, int newStatusId) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS_SQL)) {

            ps.setInt(1, newStatusId);
            ps.setInt(2, receiptId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Receipt findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReceipt(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    receipts.add(mapReceipt(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receipts;
    }

    @Override
    public void add(Receipt object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, object.getClientId());
            ps.setInt(2, object.getStatusId());
            ps.setDate(3, Date.valueOf(object.getOrderDate()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Receipt newObject) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, newObject.getClientId());
            ps.setInt(2, newObject.getStatusId());
            ps.setDate(3, Date.valueOf(newObject.getOrderDate()));
            ps.setInt(4, id);
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

    private Receipt mapReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setClientId(rs.getInt("client_id"));
        receipt.setClientId(rs.getInt("client_id"));
        receipt.setStatusId(rs.getInt("status_id"));
        receipt.setOrderDate(rs.getDate("orderDate").toLocalDate());
        return receipt;
    }
}
