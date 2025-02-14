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
        if(event.getCursor() == null
                || !event.getCursor().hasItemMeta()
                || !event.getCursor().getItemMeta().hasCustomModelData()
                // 123098 = CMD der Scheiben
                || event.getCursor().getItemMeta().getCustomModelData() != 123098) return;
        if(event.getCursor().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            if(!event.getWhoClicked().hasPermission("cadvacnements.grantAdvancement")) return;
            Player target = null;
            if(event.getView().getTitle().equals("§bDeine Erfolge:")) {
                target = (Player) event.getWhoClicked();
            }
            else if(event.getView().getTitle().startsWith("§bDie Erfolge von ")) {
                String title = event.getView().getTitle();
                String[] parts = title.split(" ");
                String lastPart = parts[parts.length - 1];
                if(Bukkit.getPlayer(lastPart) == null) return;
                target = Bukkit.getPlayer(lastPart);
            }
            ItemStack advancementItem = event.getInventory().getItem(event.getSlot() - 9);
            if(target == null || !advancementItem.hasItemMeta()) return;
            advancementManager.grantAdvancement(target.getUniqueId(), advancementManager.getID(advancementItem.getItemMeta().getDisplayName()), event.getWhoClicked().getName());
        }
        if(event.getCursor().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
            if(!event.getWhoClicked().hasPermission("cadvancements.revokeAdvancement")) return;
            Player target = null;
            if(event.getView().getTitle().equals("§bDeine Erfolge:")) {
                target = (Player) event.getWhoClicked();
            }
            else if(event.getView().getTitle().startsWith("§bDie Erfolge von ")) {
                String title = event.getView().getTitle();
                String[] parts = title.split(" ");
                String lastPart = parts[parts.length - 1];
                if(Bukkit.getPlayer(lastPart) == null) return;
                target = Bukkit.getPlayer(lastPart);
            }
            ItemStack advancementItem = event.getInventory().getItem(event.getSlot() - 9);
            if(target == null || !advancementItem.hasItemMeta()) return;
            advancementManager.revokeAdvancement(target.getUniqueId(), advancementManager.getID(advancementItem.getItemMeta().getDisplayName()), event.getWhoClicked().getName());
        }
    }
}
