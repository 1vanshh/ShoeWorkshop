package org.example;

import org.example.db.DatabaseConnection;
import org.example.entities.Client;
import org.example.repository.ClientRepositoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Menu.run();
        ClientRepositoryImpl repo = new ClientRepositoryImpl();
        Client client = new Client.Builder("Shama", "VRN").email("Shama228@vsu.ru").build();

//        repo.add(client);

        repo.delete(1008);

    }
}