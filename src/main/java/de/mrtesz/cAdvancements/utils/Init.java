package de.mrtesz.cAdvancements.utils;

import de.mrtesz.cAdvancements.CAdvancements;
import de.mrtesz.cAdvancements.commands.*;
import de.mrtesz.cAdvancements.listeners.AdvancementListener;
import de.mrtesz.cAdvancements.listeners.HeartForMeListener;
import de.mrtesz.cAdvancements.listeners.PlayerInventoryClickListener;
import de.mrtesz.cAdvancements.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
        manager.registerEvents(new HeartForMeListener(advancementManager), cAdvancements);
    }

    private void registerCommands() {
        cAdvancements.getCommand("cadvancement").setTabCompleter(new TabCompleter(advancementManager));
        cAdvancements.getCommand("advancements").setExecutor(new AdvancementCommand(advancementManager, new Base64Manager()));
        cAdvancements.getCommand("cadvancement").setExecutor(new CAdvancementsCommand(advancementManager));
        cAdvancements.getCommand("casaveitem").setExecutor(new SaveItemCommand(new Base64Manager()));
        cAdvancements.getCommand("cagiveheart").setExecutor(new HeartCommand());
    }

    private void setInfoTable() {
        String sql = "INSERT IGNORE INTO infoTable (advancement, name, description, rarity) VALUES (?, ?, ?, ?)";
        for(String advancement : advancementManager.getAdvancements("all")) {
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
                switch (advancement) {
                    case "allKeys":
                        statement.setString(1, advancement);
                        statement.setString(2, "Süchtig nach Keys");
                        statement.setString(3, "Habe jede Keysorte im Inventar");
                        statement.setString(4, "common");
                        break;
                    case "homeless":
                        statement.setString(1, advancement);
                        statement.setString(2, "Trautes Heim Glück allein!");
                        statement.setString(3, "Benutze 200 mal /home");
                        statement.setString(4, "common");
                        break;
                    case "fastSpawn":
                        statement.setString(1, advancement);
                        statement.setString(2, "Der schnellere Weg zum Spawn");
                        statement.setString(3, "Benutze 200 mal /warp Spawn");
                        statement.setString(4, "common");
                        break;
                    case "fastShopping":
                        statement.setString(1, advancement);
                        statement.setString(2, "In Shopping-Laune");
                        statement.setString(3, "Benutze 200 mal /warp Shopping");
                        statement.setString(4, "common");
                        break;
                    case "ownEC":
                        statement.setString(1, advancement);
                        statement.setString(2, "Ein zweites Inventar");
                        statement.setString(3, "Benutze 200 mal /echest oder /enderchest");
                        statement.setString(4, "common");
                        break;
                    case "fastFighting":
                        statement.setString(1, advancement);
                        statement.setString(2, "Kampfsucht");
                        statement.setString(3, "Benutze 200 mal /warp pvp");
                        statement.setString(4, "common");
                        break;
                    case "needHelp":
                        statement.setString(1, advancement);
                        statement.setString(2, "Hilfe!");
                        statement.setString(3, "Benutze 200 mal /help");
                        statement.setString(4, "common");
                        break;
                    case "notAlone":
                        statement.setString(1, advancement);
                        statement.setString(2, "In Gesellschaft!");
                        statement.setString(3, "Benutze 200 mal /tpa");
                        statement.setString(4, "common");
                        break;
                    case "trash":
                        statement.setString(1, advancement);
                        statement.setString(2, "Müll is Müll!");
                        statement.setString(3, "Benutze 200 mal /trash");
                        statement.setString(4, "common");
                        break;
                    case "ghg":
                        statement.setString(1, advancement);
                        statement.setString(2, "GHG");
                        statement.setString(3, "Habe 787 Level");
                        statement.setString(4, "legendary");
                        break;
                    case "heartForMe":
                        statement.setString(1, advancement);
                        statement.setString(2, "Hast du ein Herz für mich?");
                        statement.setString(3, "Gebe einem anderen Spieler ein Herz");
                        statement.setString(4, "common");
                        break;
                    default:
                        break;
                }
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTables() {
        Connection connection = connectionManager.getConnection();
        String playerAdvancementCounter = "CREATE TABLE IF NOT EXISTS playerAdvancementCounter (" +
                "advancement ENUM('homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash') NOT NULL, " +
                "uuid VARCHAR(36) NOT NULL, " +
                "count INT DEFAULT 0, " +
                "PRIMARY KEY (advancement, uuid)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(playerAdvancementCounter);
            System.out.println("[CAdvancements] Created Table 'playerAdvancementCounter' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String infoTable = "CREATE TABLE IF NOT EXISTS infoTable (" +
                "advancement ENUM('allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg', 'heartForMe') NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "rarity ENUM('legendary', 'rare', 'common'), " +
                "PRIMARY KEY (advancement)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(infoTable);
            System.out.println("[CAdvancements] Created Table 'infoTable' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String alterIndoTable = "ALTER TABLE infoTable MODIFY COLUMN advancement ENUM(" +
                "'allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg', 'heartForMe') NOT NULL;";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(alterIndoTable);
            System.out.println("[CAdvancements] Altered Table infoTable");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String playerAdvancements = "CREATE TABLE IF NOT EXISTS playerAdvancements (" +
                "advancement ENUM('allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg', 'heartForMe') NOT NULL, " +
                "uuid VARCHAR(36) NOT NULL, " +
                "owned BOOLEAN DEFAULT FALSE," +
                "PRIMARY KEY (advancement, uuid)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(playerAdvancements);
            System.out.println("[CAdvancements] Created Table 'playerAdvancements' when not existent.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String alterPlayerAdvancementTable = "ALTER TABLE playerAdvancements MODIFY COLUMN advancement ENUM(" +
                "'allKeys', 'homeless', 'fastSpawn', 'fastShopping', 'fastFighting', 'ownEC', 'needHelp', 'notAlone', 'trash', 'ghg', 'heartForMe') NOT NULL;";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(alterPlayerAdvancementTable);
            System.out.println("[CAdvancements] Altered Table playerAdvancements");
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
