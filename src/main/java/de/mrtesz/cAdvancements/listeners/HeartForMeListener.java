package de.mrtesz.cAdvancements.listeners;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartForMeListener implements Listener {

    private final Map<UUID, UUID> dropMapping = new HashMap<>();
    private final AdvancementManager advancementManager;

    public HeartForMeListener(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if(!item.getType().equals(Material.PLAYER_HEAD)) return;
        if(!isHeart(item)) return;
        UUID itemUUID = event.getItemDrop().getUniqueId();
        UUID dropperUUID = event.getPlayer().getUniqueId();
        dropMapping.put(itemUUID, dropperUUID);
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        UUID itemUUID = event.getItem().getUniqueId();
        if (dropMapping.containsKey(itemUUID)) {
            UUID dropperUUID = dropMapping.get(itemUUID);
            UUID pickerUUID = event.getEntity().getUniqueId();
            if (!pickerUUID.equals(dropperUUID)) {
                advancementManager.grantAdvancement(player.getUniqueId(), "sys", "heartForMe");
            }
            dropMapping.remove(itemUUID);
        }
    }

    private boolean isHeart(ItemStack item) {
        if (!item.hasItemMeta()) return false;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey("smp", "isheart");

        return container.has(key, PersistentDataType.STRING) &&
                "true".equals(container.get(key, PersistentDataType.STRING));
    }
}
