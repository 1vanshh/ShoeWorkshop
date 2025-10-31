package org.example.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        connect();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection connect() {
        try {

            connection = DriverManager.getConnection(
                    DatabaseConfig.getProperty("db.url"),
                    DatabaseConfig.getProperty("db.user"),
                    DatabaseConfig.getProperty("db.password")
            );

            System.out.println("Соединение с базой данных установлено.");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
        }
        return null;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public Connection getConnection() {
        if (!isConnected()) {
            System.out.println("Соединение остановлено. Переподключение...");
            connect();
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение остановлено.");
            } catch (SQLException e) {
                System.out.println("Возникла ошибка при остановке соединения: " + e.getMessage());
            }
        }
    }
}
