package de.mrtesz.cAdvancements;

import de.mrtesz.cAdvancements.utils.ConnectionManager;
import de.mrtesz.cAdvancements.utils.Init;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CAdvancements extends JavaPlugin {

    private ConnectionManager connectionManager;

    @Override
    public void onEnable() {
        connectionManager = new ConnectionManager(this);

        new Init(this);
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("§cServer is restarting...");
        }
        Bukkit.getLogger().info("Saved all Player Advancements");
        connectionManager.disconnect();
    }
}
