package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    private Properties properties = new Properties();
    private Connection connection;
    private static DataBaseConnection instance;

    private DataBaseConnection() {
        connect();
    }

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public Connection connect() {
        try {
            properties = readProperties();

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String pass = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, pass);
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

    private Properties readProperties() {
        try (InputStream input = DataBaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Не удалось найти файл db.properties");
                return null;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (properties == null) {
            System.out.println("Не удалось загрузить параметры подключения.");
            return null;
        }
        return properties;
    }
}
