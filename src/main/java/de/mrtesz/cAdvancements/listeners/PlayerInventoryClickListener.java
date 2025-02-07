package de.mrtesz.cAdvancements.listeners;

import de.mrtesz.cAdvancements.CAdvancements;
import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.CustomInventoryHolder;
import de.mrtesz.cAdvancements.utils.Init;
import de.mrtesz.cAdvancements.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryClickListener implements Listener {

    private ItemStack initialize = new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build();
    private AdvancementManager advancementManager = Init.getAdvancementManager();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getInventory().getHolder() instanceof CustomInventoryHolder)) return;
        if(event.getInventory().getItem(53) == null) return;
        if(!(event.getInventory().getItem(53).equals(initialize))) return;
        event.setCancelled(true);
    }

}
