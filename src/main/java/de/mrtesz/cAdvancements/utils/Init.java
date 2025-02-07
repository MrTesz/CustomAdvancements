package de.mrtesz.cAdvancements.utils;

import de.mrtesz.cAdvancements.CAdvancements;
import de.mrtesz.cAdvancements.commands.AdvancementCommand;
import de.mrtesz.cAdvancements.commands.CAdvancementsCommand;
import de.mrtesz.cAdvancements.commands.SaveRewardItemCommand;
import de.mrtesz.cAdvancements.commands.TabCompleter;
import de.mrtesz.cAdvancements.listeners.AdvancementListener;
import de.mrtesz.cAdvancements.listeners.PlayerInventoryClickListener;
import de.mrtesz.cAdvancements.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Init {

    private static CAdvancements cAdvancements;
    private ConnectionManager connectionManager;
    private static AdvancementManager advancementManager;

    public Init(CAdvancements cAdvancements, AdvancementManager advancementManager) {
        if(!cAdvancements.getConfig().contains("database.url")) {
            cAdvancements.getConfig().set("database.url", "");
        }
        if(!cAdvancements.getConfig().contains("database.user")) {
            cAdvancements.getConfig().set("database.user", "");
        }
        if(!cAdvancements.getConfig().contains("database.password")) {
            cAdvancements.getConfig().set("database.password", "");
        }
        this.connectionManager = new ConnectionManager(cAdvancements);
        this.cAdvancements = cAdvancements;
        Init.advancementManager = new AdvancementManager(connectionManager);

        connectionManager.connect();
        createTables();
        setInfoTable();

        registerEvents();
        registerCommands();
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new AdvancementListener(this, advancementManager), cAdvancements);
        manager.registerEvents(new PlayerJoinQuitListener(advancementManager), cAdvancements);
        manager.registerEvents(new PlayerInventoryClickListener(), cAdvancements);
        manager.registerEvents(new AdvancementManager(connectionManager), cAdvancements);
    }

    private void registerCommands() {
        cAdvancements.getCommand("cadvancement").setTabCompleter(new TabCompleter(advancementManager));
        cAdvancements.getCommand("advancements").setExecutor(new AdvancementCommand(advancementManager));
        cAdvancements.getCommand("cadvancement").setExecutor(new CAdvancementsCommand(advancementManager));
        cAdvancements.getCommand("saverewarditem").setExecutor(new SaveRewardItemCommand(this));
    }

    private void setInfoTable() {
        String sql = "INSERT IGNORE INTO infoTable (advancement, name, description, rarity) VALUES (?, ?, ?, ?)";
        for(String advancement : advancementManager.getAdvancements("all")) {
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
                if(advancement.equalsIgnoreCase("allKeys")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Süchtig nach Keys");
                    statement.setString(3, "Habe jede Keysorte im Inventar");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("homeless")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Trautes Heim Glück allein!");
                    statement.setString(3, "Benutze 200 mal /home");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("fastSpawn")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Der schnellere Weg zum Spawn");
                    statement.setString(3, "Benutze 200 mal /warp Spawn");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("fastShopping")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "In Shopping-Laune");
                    statement.setString(3, "Benutze 200 mal /warp Shopping");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("ownEC")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Ein zweites Inventar");
                    statement.setString(3, "Benutze 200 mal /echest oder /enderchest");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("fastFighting")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Kampfsucht");
                    statement.setString(3, "Benutze 200 mal /warp pvp");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("needHelp")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Hilfe!");
                    statement.setString(3, "Benutze 200 mal /help");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("notAlone")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "In Gesellschaft!");
                    statement.setString(3, "Benutze 200 mal /tpa");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("trash")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "Müll is Müll!");
                    statement.setString(3, "Benutze 200 mal /trash");
                    statement.setString(4, "common");
                }
                else if(advancement.equalsIgnoreCase("ghg")) {
                    statement.setString(1, advancement);
                    statement.setString(2, "GHG");
                    statement.setString(3, "Habe 787 Level");
                    statement.setString(4, "legendary");
                }
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTables() {
        String playerAdvancementCounter = "CREATE TABLE IF NOT EXISTS playerAdvancementCounter (" +
                "advancement ENUM('homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash') NOT NULL, " +
                "uuid VARCHAR(36) NOT NULL, " +
                "count INT DEFAULT 0, " +
                "PRIMARY KEY (advancement, uuid)" +
                ");";

        try (Connection connection = connectionManager.getConnection(); java.sql.Statement statement = connection.createStatement()) {
            statement.execute(playerAdvancementCounter);
            System.out.println("[CAdvancements] Created Table 'playerAdvancementCounter' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String infoTable = "CREATE TABLE IF NOT EXISTS infoTable (" +
                "advancement ENUM('allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg') NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "rarity ENUM('legendary', 'rare', 'common'), " +
                "PRIMARY KEY (advancement)" +
                ");";

        try (Connection connection = connectionManager.getConnection(); java.sql.Statement statement = connection.createStatement()) {
            statement.execute(infoTable);
            System.out.println("[CAdvancements] Created Table 'infoTable' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String playerAdvancements = "CREATE TABLE IF NOT EXISTS playerAdvancements (" +
                "advancement ENUM('allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg') NOT NULL, " +
                "uuid VARCHAR(36) NOT NULL, " +
                "owned BOOLEAN DEFAULT FALSE," +
                "PRIMARY KEY (advancement, uuid)" +
                ");";

        try (Connection connection = connectionManager.getConnection(); java.sql.Statement statement = connection.createStatement()) {
            statement.execute(playerAdvancements);
            System.out.println("[CAdvancements] Created Table 'playerAdvancements' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CAdvancements getInstance() {
        return cAdvancements;
    }

    public static AdvancementManager getAdvancementManager() {
        return advancementManager;
    }
}
