package de.mrtesz.cAdvancements.utils;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdvancementManager implements Listener {

    private HashMap<UUID, HashMap<String, Boolean>> playerAdvancements;
    private HashMap<UUID, HashMap<String, Integer>> playerAdvancementCounter;
    private ConnectionManager connectionManager;
    private Random random = new Random();
    private Base64Manager base64Manager;

    public AdvancementManager(ConnectionManager connectionManager) {
        playerAdvancements = new HashMap<>();
        playerAdvancementCounter = new HashMap<>();
        this.connectionManager = connectionManager;
        base64Manager = new Base64Manager();
    }

    public void addAdvancementCount(UUID uuid, String advancement) {
        HashMap<String, Integer> mapCount = playerAdvancementCounter.getOrDefault(uuid, new HashMap<>());
        mapCount.put(advancement, getAdvancementCount(uuid, advancement) + 1);
        playerAdvancementCounter.put(uuid, mapCount);
    }

    public int getAdvancementCount(UUID uuid, String advancement) {
        HashMap<String, Integer> mapCount = playerAdvancementCounter.getOrDefault(uuid, new HashMap<>());
        return mapCount.getOrDefault(advancement, 0);
    }

    public boolean getAdvancement(UUID uuid, String advancement) {
        HashMap<String, Boolean> map = playerAdvancements.getOrDefault(uuid, new HashMap<>());
        return map.getOrDefault(advancement, false);
    }

    public ChatColor getRarity(String advancement) {
        String getRarity = "SELECT rarity FROM infoTable WHERE advancement = ?";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(getRarity)) {
            statement.setString(1, advancement);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String rar = resultSet.getString("rarity");
                    if(rar.equalsIgnoreCase("common")) {
                        return ChatColor.GREEN;
                    } else if(rar.equalsIgnoreCase("rare")) {
                        return ChatColor.DARK_PURPLE;
                    } else if(rar.equalsIgnoreCase("legendary")) {
                        return ChatColor.GOLD;
                    }
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ChatColor.STRIKETHROUGH;
    }
    public String getDescription(String advancement) {
        String getDescription = "SELECT description FROM infoTable WHERE advancement = ?";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(getDescription)) {
            statement.setString(1, advancement);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("description");
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
    public String getName(String advancement) {
        String getName = "SELECT name FROM infoTable WHERE advancement = ?";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(getName)) {
            statement.setString(1, advancement);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public void revokeAdvancement(UUID uuid, String advancement, String executor) {
        if(!getAdvancement(uuid, advancement)) {
            if(!executor.equalsIgnoreCase("sys")) {
                Bukkit.getPlayer(executor).sendMessage("§cDer Spieler hat das Advancement noch nicht.");
            }
            return;
        }
        HashMap<String, Boolean> map = playerAdvancements.get(uuid);
        map.put(advancement, false);
        playerAdvancements.put(uuid, map);
        if(executor.equalsIgnoreCase("sys")) {
            return;
        }
        Bukkit.getPlayer(executor).sendMessage("§cDem Spieler hat dieses Advancement nun nicht mehr.");
    }
    public void grantAdvancement(UUID uuid, String advancement, String executor) {
        if(Bukkit.getPlayer(uuid) == null) {
            if(!executor.equalsIgnoreCase("sys")) {
                Bukkit.getPlayer(executor).sendMessage("§cDer Spieler ist nicht online");
            }
            return;
        }
        if(getAdvancement(uuid, advancement)) {
            if(!executor.equalsIgnoreCase("sys")) {
                Bukkit.getPlayer(executor).sendMessage("§cDer Spieler hat dieses Advancement bereits");
            }
            return;
        }
        HashMap<String, Boolean> map = playerAdvancements.getOrDefault(uuid, new HashMap<>());
        map.put(advancement, true);
        playerAdvancements.put(uuid, map);
        String name = getName(advancement);
        String description = getDescription(advancement);
        ChatColor rarity = getRarity(advancement);

        net.md_5.bungee.api.chat.TextComponent advancementName =
                new net.md_5.bungee.api.chat.TextComponent(rarity + "[" + name + "]");
        advancementName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new Text(rarity +
                        name + "\n" +
                        description)));
        net.md_5.bungee.api.chat.TextComponent advancementName2 =
                new net.md_5.bungee.api.chat.TextComponent(getPrefix() + Bukkit.getPlayer(uuid).getName() + " hat den Erfolg ");
        net.md_5.bungee.api.chat.TextComponent advancementName3 =
                new net.md_5.bungee.api.chat.TextComponent(" bekommen.");

        advancementName2.addExtra(advancementName);
        advancementName2.addExtra(advancementName3);
        for(Player current : Bukkit.getOnlinePlayers()) {
            current.spigot().sendMessage(advancementName2);
        }
        if(rarity.equals(ChatColor.GOLD)) {
            Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        } else {
            Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.UI_TOAST_IN, 1, 1);
        }
        grandPlayerRandomReward(Bukkit.getPlayer(uuid));
    }

    public void saveAdvancements(UUID uuid) {
        try {
            if (connectionManager.getConnection() == null || connectionManager.getConnection().isClosed()) {
                connectionManager.connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String command = "INSERT INTO playerAdvancements (advancement, uuid, owned) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE owned = IF(owned <> VALUES(owned), VALUES(owned), ?)";
        Collection<String> advancements = getAdvancements("all");
        for(String advancement : advancements) {
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(command)) {
                HashMap<String, Boolean> map = playerAdvancements.getOrDefault(uuid, new HashMap<>());
                boolean owned = map.getOrDefault(advancement, false);
                statement.setString(1, advancement);
                statement.setString(2, uuid.toString());
                statement.setBoolean(3, owned);
                statement.setBoolean(4, owned);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String command2 = "INSERT INTO playerAdvancementCounter (advancement, uuid, count) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE count = IF(count <> VALUES(count), VALUES(count), ?)";
        Collection<String> advancementsCount = getAdvancements("allCounter");
        for(String advancement : advancementsCount) {
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(command2)) {
                HashMap<String, Integer> mapCount = playerAdvancementCounter.getOrDefault(uuid, new HashMap<>());
                int count = mapCount.getOrDefault(advancement, 0);
                statement.setString(1, advancement);
                statement.setString(2, uuid.toString());
                statement.setInt(3, count);
                statement.setInt(4, count);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveOldAdvancements(UUID uuid) {
        try {
            if (connectionManager.getConnection() == null || connectionManager.getConnection().isClosed()) {
                connectionManager.connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String command = "SELECT owned FROM playerAdvancements WHERE advancement = ? AND uuid = ?";
        Collection<String> advancements = getAdvancements("all");
        for(String advancement : advancements) {
            boolean owned = false;
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(command)) {
                statement.setString(1, advancement);
                statement.setString(2, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        owned = resultSet.getBoolean("owned");
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            playerAdvancements.putIfAbsent(uuid, new HashMap<>());
            HashMap<String, Boolean> map = playerAdvancements.getOrDefault(uuid, new HashMap<>());
            map.put(advancement, owned);
            playerAdvancements.put(uuid, map);
        }

        String command2 = "SELECT count FROM playerAdvancementCounter WHERE advancement = ? AND uuid = ?";
        Collection<String> advancementsCount = getAdvancements("allCounter");
        for(String advancement : advancementsCount) {
            int count = 0;
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(command2)) {
                statement.setString(1, advancement);
                statement.setString(2, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt("count");
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            playerAdvancementCounter.putIfAbsent(uuid, new HashMap<>());
            HashMap<String, Integer> mapCount = playerAdvancementCounter.getOrDefault(uuid, new HashMap<>());
            mapCount.put(advancement, count);
            playerAdvancementCounter.put(uuid, mapCount);
        }
    }

    public String getPrefix() {
        return ChatColor.BOLD + "§bAdvancements §7■§r ";
    }

    public Collection<String> getAdvancements(String usage) {
        Collection<String> advancements = new ArrayList<>(List.of());
        if(usage.equalsIgnoreCase("allCounter")) {
            // nur mit counter
            advancements.add("homeless");
            advancements.add("fastSpawn");
            advancements.add("fastShopping");
            advancements.add("ownEC");
            advancements.add("fastFighting");
            advancements.add("needHelp");
            advancements.add("notAlone");
            advancements.add("trash");

        } else if(usage.equalsIgnoreCase("all")) {
            // alle
            advancements.add("allKeys");

            advancements.add("homeless");
            advancements.add("fastSpawn");
            advancements.add("fastShopping");
            advancements.add("ownEC");
            advancements.add("fastFighting");
            advancements.add("needHelp");
            advancements.add("notAlone");
            advancements.add("trash");
            advancements.add("ghg");
            advancements.add("heartForMe");
        }
        return advancements;
    }

    private void grandPlayerRandomReward(Player player) {
        ItemStack item = new ItemBuilder(Material.MUSIC_DISC_11).build();
        if(Init.getInstance().getConfig().contains("rewards.item")) {
            item = base64Manager.itemFromBase64(Init.getInstance().getConfig().getString("rewards.item"));
        }

        int choice = random.nextInt(2);
        player.sendMessage("§aDu hast eine Belohnung für deinen Erfolg bekommen!");
        if (choice == 0) {
            int xp = random.nextInt(201) + 100;
            player.giveExp(xp);
            player.sendMessage("§aDu hast " + xp + " XP erhalten!");
        } else {
            if (!item.getType().equals(Material.MUSIC_DISC_11)) {
                item.setAmount(random.nextInt(5) + 1);

                player.getInventory().addItem(item);
                player.sendMessage("§aDu hast einen Belohnungs-Gegenstand bekommen!");
            } else {
                int xp = random.nextInt(201) + 100;
                player.giveExp(xp);
                player.sendMessage("§aDu hast " + xp + " XP erhalten!");
            }
        }
    }
}