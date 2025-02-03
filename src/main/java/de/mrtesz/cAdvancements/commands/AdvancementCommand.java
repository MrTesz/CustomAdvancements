package de.mrtesz.cAdvancements.commands;

import de.mrtesz.cAdvancements.CAdvancements;
import de.mrtesz.cAdvancements.utils.AdvancementManager;
import de.mrtesz.cAdvancements.utils.CustomInventoryHolder;
import de.mrtesz.cAdvancements.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AdvancementCommand implements CommandExecutor {

    private boolean formatBoolean = true;
    private AdvancementManager advancementManager;

    public AdvancementCommand(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl ist nur für Spieler.");
            return false;
        }
        Player player = (Player) sender;
        if(args.length > 1) {
            player.sendMessage("§cBitte benutze: §e/advancements");
            return false;
        }
        if (args.length == 0) {
            if (!player.hasPermission("cadvacnements.seeAdvancements")) {
                player.sendMessage("§cDu hast keine Berechtigung für diesen Befehl.");
                return false;
            }
            Inventory advancementInv = Bukkit.createInventory(null, 9 * 6, "§bDeine Erfolge:");
            // Initialize
            if (formatBoolean) {
                advancementInv.setItem(53, new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build());
            }
            // Süchtig nach Keys
            if (formatBoolean) {
                String advancement = "allKeys";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(1, new ItemBuilder(Material.BRICK).setCustomModelData(2).addEnchantments(Enchantment.FLAME, 2, false).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(10, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(10, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // Trautes Heim Glück allein
            if (formatBoolean) {
                String advancement = "homeless";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(2, new ItemBuilder(Material.OAK_STAIRS).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(11, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(11, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // Der schnellere Weg zum Spawn
            if (formatBoolean) {
                String advancement = "fastSpawn";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(3, new ItemBuilder(Material.END_CRYSTAL).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(12, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(12, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // FastShopping
            if(formatBoolean) {
                String advancement = "fastShopping";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(4, new ItemBuilder(Material.GOLD_NUGGET).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(13, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(13, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // own ec
            if(formatBoolean) {
                String advancement = "ownEC";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(5, new ItemBuilder(Material.ENDER_CHEST).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(14, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(14, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // fast fighting
            if(formatBoolean) {
                String advancement = "fastFighting";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(6, new ItemBuilder(Material.IRON_SWORD).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(15, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(15, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // needHelp
            if(formatBoolean) {
                String advancement = "needHelp";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(7, new ItemBuilder(Material.LIGHT).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(16, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(16, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // notAlone
            if(formatBoolean) {
                String advancement = "notAlone";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(8, new ItemBuilder(Material.ELYTRA).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(17, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // trash
            if(formatBoolean) {
                String advancement = "trash";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(0, new ItemBuilder(Material.DIRT).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(9, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // ghg
            if(formatBoolean) {
                String advancement = "ghg";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);

                advancementInv.setItem(18, new ItemBuilder(Material.EXPERIENCE_BOTTLE).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(27, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(27, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            player.openInventory(advancementInv);
            player.sendMessage("§aDu hast deine Erfolge geöffnet!");
        }
        if (args.length == 1) {
            if (!player.hasPermission("cadvacnements.seeOtherAdvancements")) {
                player.sendMessage("§cDu hast keine Berechtigung für diesen Befehl.");
                return false;
            }
            if(Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage("§cDer Spieler ist nicht Online");
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            Inventory advancementInv = Bukkit.createInventory(new CustomInventoryHolder(), 9 * 6, "§bDie Erfolge von " + args[0] + ":");
            // Initialize
            if (formatBoolean) {
                advancementInv.setItem(53, new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build());
            }
            // Süchtig nach Keys
            if (formatBoolean) {
                String advancement = "allKeys";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(1, new ItemBuilder(Material.BRICK).setCustomModelData(2).addEnchantments(Enchantment.FLAME, 2, false).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(10, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(10, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // Trautes Heim Glück allein
            if (formatBoolean) {
                String advancement = "homeless";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(2, new ItemBuilder(Material.OAK_STAIRS).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(11, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(11, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // Der schnellere Weg zum Spawn
            if (formatBoolean) {
                String advancement = "fastSpawn";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(3, new ItemBuilder(Material.END_CRYSTAL).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(12, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(12, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // FastShopping
            if(formatBoolean) {
                String advancement = "fastShopping";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(4, new ItemBuilder(Material.GOLD_NUGGET).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(13, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(13, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // own ec
            if(formatBoolean) {
                String advancement = "ownEC";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(5, new ItemBuilder(Material.ENDER_CHEST).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(14, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(14, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // fast fighting
            if(formatBoolean) {
                String advancement = "fastFighting";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(6, new ItemBuilder(Material.IRON_SWORD).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(15, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(15, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // needHelp
            if(formatBoolean) {
                String advancement = "needHelp";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(7, new ItemBuilder(Material.LIGHT).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(16, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(16, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // notAlone
            if(formatBoolean) {
                String advancement = "notAlone";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(8, new ItemBuilder(Material.ELYTRA).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(17, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(17, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // trash
            if(formatBoolean) {
                String advancement = "trash";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(0, new ItemBuilder(Material.DIRT).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(9, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            // ghg
            if(formatBoolean) {
                String advancement = "ghg";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);

                advancementInv.setItem(18, new ItemBuilder(Material.EXPERIENCE_BOTTLE).setCustomModelData(2).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(27, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(27, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
            }
            player.openInventory(advancementInv);
            player.sendMessage("§aDu hast die Erfolge von " + args[0] + " geöffnet!");
        }
        return true;
    }
}
