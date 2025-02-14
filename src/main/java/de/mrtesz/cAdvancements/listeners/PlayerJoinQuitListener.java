package de.mrtesz.cAdvancements.listeners;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.Init;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private final AdvancementManager advancementManager;

    public PlayerJoinQuitListener(Init init) {
        this.advancementManager = init.getAdvancementManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        advancementManager.saveOldAdvancements(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        advancementManager.saveAdvancements(event.getPlayer().getUniqueId());
    }
}
