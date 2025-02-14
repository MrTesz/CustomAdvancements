package de.mrtesz.cAdvancements.listeners;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.Base64Manager;
import de.mrtesz.cAdvancements.utils.Init;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AdvancementListener implements Listener {

    private final Map<Material, List<Integer>> requiredItems = new HashMap<>();
    private final AdvancementManager advancementManager;
    private final Init init;

    public AdvancementListener(Init init, AdvancementManager advancementManager) {
        this.init = init;
        this.advancementManager = advancementManager;
        requiredItems.put(Material.BRICK, Arrays.asList(2, 6)); // BRICK mit CMD 2 = Floose Key Brick mit CMD 6 = Engel Key
        requiredItems.put(Material.NETHER_BRICK, Arrays.asList(2)); // Netherbrick mit CMD 2 = Legendärer Key
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        Player player = event.getPlayer();
            int countNeeded = 199; // Quantität vom Command -1

        String advancement = "homeless";
        String commandNeed = "home";
        if(command.startsWith("/" + commandNeed)) {
            if(advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
            } else {
                advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
            }
        }

        // warps
        boolean formatBoolean = true;
        if(formatBoolean) {
            advancement = "fastSpawn";
            commandNeed = "warp";
            String arg1 = "Spawn";
            if (command.startsWith("/" + commandNeed) && command.endsWith(arg1)) {
                if (advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                    advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
                } else {
                    advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
                }
            }

            advancement = "fastShopping";
            commandNeed = "warp";
            arg1 = "Shopping";
            if (command.startsWith("/" + commandNeed) && command.endsWith(arg1)) {
                if (advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                    advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
                } else {
                    advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
                }
            }

            advancement = "fastFighting";
            commandNeed = "warp";
            arg1 = "pvp";
            if (command.startsWith("/" + commandNeed) && command.endsWith(arg1)) {
                if (advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                    advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
                } else {
                    advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
                }
            }
        }

        advancement = "ownEC";
        commandNeed = "echest";
        String commandNeed2 = "enderchest";
        if(command.startsWith("/" + commandNeed) || command.startsWith("/" + commandNeed2)) {
            if(advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
            } else {
                advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
            }
        }

        advancement = "needHelp";
        commandNeed = "help";
        if(command.startsWith("/" + commandNeed)) {
            if(advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
            } else {
                advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
            }
        }

        advancement = "notAlone";
        commandNeed = "tpa";
        if(command.startsWith("/" + commandNeed)) {
            if(advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
            } else {
                advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
            }
        }

        advancement = "trash";
        commandNeed = "trash";
        if(command.startsWith("/" + commandNeed)) {
            if(advancementManager.getAdvancementCount(player.getUniqueId(), advancement) >= countNeeded) {
                advancementManager.grantAdvancement(player.getUniqueId(), advancement, "sys");
            } else {
                advancementManager.addAdvancementCount(player.getUniqueId(), advancement);
            }
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        String advancement = "ghg";
        int level = event.getNewLevel();
        int needLevel = 787;

        if(level >= needLevel) {
            advancementManager.grantAdvancement(event.getPlayer().getUniqueId(), advancement, "sys");
        }
        /*
        if(level < needLevel) {
            advancementManager.revokeAdvancement(event.getPlayer().getUniqueId(), advancement, "sys");
        }
         */
    }


    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        Inventory inventory = player.getInventory();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (hasRequiredItems(inventory)) {
                    advancementManager.grantAdvancement(player.getUniqueId(), "allKeys", "sys");
                }
            }
        }.runTaskLater(init.getInstance(), 5);
    }

    private boolean hasRequiredItems(Inventory inventory) {
        Map<Material, Boolean> foundItems = new HashMap<>();

        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
                    Material material = item.getType();
                    int customModelData = item.getItemMeta().getCustomModelData();
                    if (requiredItems.containsKey(material)) {
                        List<Integer> customModelDataList = requiredItems.get(material);
                        if (customModelDataList.contains(customModelData)) {
                            foundItems.put(material, true);
                        }
                    }
                }
            }
        }

        for (Material requiredMaterial : requiredItems.keySet()) {
            if (!foundItems.getOrDefault(requiredMaterial, false)) {
                return false;
            }
        }

        return true;
    }
}
