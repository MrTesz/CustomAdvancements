package de.mrtesz.cAdvancements.utils;

import org.bukkit.Bukkit;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private Connection connection;
    private String url = "";
    private String user = "";
    private String password = "";

    public void connect() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("config.properties"));

            url = properties.getProperty("database.url");
            user = properties.getProperty("database.user");
            password = properties.getProperty("database.password");

            // Verwende die geladenen Werte
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("[CAdvancements] Connected");
            }
        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getLogger().warning("Fehler beim Verbinden mit der MariaDB-Datenbank: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if(connection != null) {
            try {
                connection.close();
                System.out.println("[CAdvancements] Disconnected");
            } catch(SQLException e) {
                System.out.println("Error in disconnect: " + e.getMessage());
            }
        } else
            System.out.println("[ProjectMariaDB] Disconnection failed: Not connected");
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
