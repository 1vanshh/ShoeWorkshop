package org.example.repository;

import org.example.db.DatabaseConnection;
import org.example.entities.ReceiptItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReceiptItemRepositoryImpl implements ReceiptItemRepository {

    private String SELECT_BY_RECEIPT_SQL = "SELECT * FROM receipt_items WHERE receipt_id = ?";
    private String SELECT_BY_ID_SQL = "SELECT * FROM receipt_items WHERE item_id = ?";
    private String SELECT_ALL_SQL = "SELECT * FROM receipt_items";
    private String INSERT_SQL = "INSERT INTO recepts_items (receipt_id, favour_id, price, quantity) VALUES (?, ?, ?, ?)";
    private String UPDATE_SQL = "UPDATE receipt_items SET receipt_id = ?, favour_id = ?, price = ?, quantity = ? WHERE item_id = ?";
    private String DELETE_SQL = "DELETE FROM receipt_items WHERE item_id = ?";

    @Override
    public List<ReceiptItem> findByReceiptID(int receiptID) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pr = conn.prepareStatement(SELECT_BY_RECEIPT_SQL)) {

            pr.setInt(1, receiptID);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    receiptItems.add(mapReceiptItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receiptItems;
    }

    @Override
    public ReceiptItem findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pr.setInt(1, id);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    return mapReceiptItem(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ReceiptItem> findAll() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pr = conn.prepareStatement(SELECT_ALL_SQL)) {

            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    receiptItems.add(mapReceiptItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receiptItems;
    }

    @Override
    public void add(ReceiptItem object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pr = conn.prepareStatement(INSERT_SQL)) {

            pr.setInt(1, object.getReceiptId());
            pr.setInt(2, object.getFavourId());
            pr.setDouble(3, object.getPrice());
            pr.setInt(4, object.getQuantity());
            pr.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, ReceiptItem newObject) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pr = conn.prepareStatement(UPDATE_SQL)) {

            pr.setInt(1, newObject.getReceiptId());
            pr.setInt(2, newObject.getFavourId());
            pr.setDouble(3, newObject.getPrice());
            pr.setInt(4, newObject.getQuantity());
            pr.setInt(5, id);
            pr.executeUpdate();
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

    private ReceiptItem mapReceiptItem(ResultSet rs) throws SQLException {
        ReceiptItem receiptItem = new ReceiptItem();
        receiptItem.setItemId(rs.getInt("item_id"));
        receiptItem.setReceiptId(rs.getInt("receipt_id"));
        receiptItem.setFavourId(rs.getInt("favour_id"));
        receiptItem.setPrice(rs.getInt("price"));
        receiptItem.setQuantity(rs.getInt("quantity"));
        return receiptItem;
    }
}
