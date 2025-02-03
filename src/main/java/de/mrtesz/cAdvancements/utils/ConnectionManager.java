package de.mrtesz.cAdvancements.utils;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection;
    private final String url = "jdbc:mariadb://localhost:3306/customAdvancements";
    private final String user = "mrteszneu";
    private final String password = "hokEja";

    public void connect() {
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
