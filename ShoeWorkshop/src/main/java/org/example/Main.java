package org.example;

import org.example.cli.Menu;
import org.example.db.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Menu.run();
        DataBaseConnection db = DataBaseConnection.getInstance();
        Connection con = db.getConnection();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from clients");


        while (rs.next()) {
            String phone = rs.getString("full_name");
            System.out.println("Name: " + phone);
        }

        rs.close();
        stmt.close();
        db.closeConnection();
    }
}