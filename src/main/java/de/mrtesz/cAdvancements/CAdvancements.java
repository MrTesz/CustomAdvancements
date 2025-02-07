package de.mrtesz.cAdvancements;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.ConnectionManager;
import de.mrtesz.cAdvancements.utils.Init;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CAdvancements extends JavaPlugin {

    private ConnectionManager connectionManager;

    @Override
    public void onEnable() {
        System.out.println("[CAdvancements] Hello :D");
        connectionManager = new ConnectionManager(this);

        new Init(this, new AdvancementManager(connectionManager));
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("§cServer is restarting...");
        }
        Bukkit.getLogger().info("Saved all Player Advancements");
        connectionManager.disconnect();
        System.out.println("[CAdvancements] Bye :C");
    }
}
