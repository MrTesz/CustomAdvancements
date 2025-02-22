package de.mrtesz.cAdvancements.utils;

import de.mrtesz.cAdvancements.CAdvancements;
import org.bukkit.Bukkit;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private final CAdvancements cAdvancements;
    private Connection connection;

    public ConnectionManager(CAdvancements cAdvancements) {
        this.cAdvancements = cAdvancements;
    }

    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(cAdvancements.getConfig().getString("database.url"),
                        cAdvancements.getConfig().getString("database.user"),
                        cAdvancements.getConfig().getString("database.password"));
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
            Bukkit.getLogger().warning("Disconnection failed: Not connected");
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
