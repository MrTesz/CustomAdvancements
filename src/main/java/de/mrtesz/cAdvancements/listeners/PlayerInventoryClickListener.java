package de.mrtesz.cAdvancements.listeners;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.CustomInventoryHolder;
import de.mrtesz.cAdvancements.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryClickListener implements Listener {

    private final ItemStack initialize = new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build();
    private final AdvancementManager advancementManager;

    public PlayerInventoryClickListener(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getInventory().getHolder() instanceof CustomInventoryHolder)
                || event.getInventory().getItem(53) == null
                || !(event.getInventory().getItem(53).equals(initialize))) return;
        event.setCancelled(true);
    }
}
