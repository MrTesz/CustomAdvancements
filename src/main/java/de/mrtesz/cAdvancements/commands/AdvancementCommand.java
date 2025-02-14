package de.mrtesz.cAdvancements.commands;

import de.mrtesz.cAdvancements.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AdvancementCommand implements CommandExecutor {

    private final AdvancementManager advancementManager;
    private final Base64Manager base64Manager;
    private final Init init;

    public AdvancementCommand(AdvancementManager advancementManager, Base64Manager base64Manager, Init init) {
        this.advancementManager = advancementManager;
        this.base64Manager = base64Manager;
        this.init = init;
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
        boolean formatBoolean = true;
        if (args.length == 0) {
            if (!player.hasPermission("cadvacnements.seeAdvancements")) {
                player.sendMessage("§cDu hast keine Berechtigung für diesen Befehl.");
                return false;
            }
            Inventory advancementInv = Bukkit.createInventory(new CustomInventoryHolder(), 9 * 6, "§bDeine Erfolge:");
            // Initialize
            if (formatBoolean) {
                advancementInv.setItem(53, new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build());
            }
            int slot = 1;
            for(String advancement : advancementManager.getAdvancements("all")) {
                if(advancement.equalsIgnoreCase("heartForMe")) continue;

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);
                Material material = advancementManager.getMaterial(advancement);

                advancementInv.setItem(slot, new ItemBuilder(material).setCustomModelData(2).addEnchantments(Enchantment.FLAME, 2, false).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(slot + 9, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(slot + 9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
                if(slot == 8) {
                    slot = 18;
                } else
                    slot++;
            }
            // heartForMe extra fall
            if(formatBoolean) {
                String advancement = "heartForMe";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);
                ItemStack heart = new ItemBuilder(Material.MUSIC_DISC_11).build();
                if(init.getInstance().getConfig().contains("items.heart")) {
                    String base64Heart = init.getInstance().getConfig().getString("items.heart");
                    heart = base64Manager.itemFromBase64(base64Heart);
                    ItemMeta meta = heart.getItemMeta();
                    meta.setCustomModelData(2);
                    meta.setDisplayName(chatColor + advancementManager.getName(advancement));
                    meta.setLore(List.of(chatColor + advancementManager.getDescription(advancement)));
                    heart.setItemMeta(meta);
                }

                advancementInv.setItem(0, heart);

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
            Inventory advancementInv = Bukkit.createInventory(new CustomInventoryHolder(), 9 * 6, "§bDie Erfolge von " + args[0]);
            // Initialize
            if (formatBoolean) {
                advancementInv.setItem(53, new ItemBuilder(Material.SPECTRAL_ARROW).setDisplayname("Next Page").setCustomModelData(100).build());
            }
            int slot = 1;
            for(String advancement : advancementManager.getAdvancements("all")) {
                if(advancement.equalsIgnoreCase("heartForMe")) continue;

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(player.getUniqueId(), advancement);
                Material material = advancementManager.getMaterial(advancement);

                advancementInv.setItem(slot, new ItemBuilder(material).setCustomModelData(2).addEnchantments(Enchantment.FLAME, 2, false).setDisplayname(chatColor +
                        advancementManager.getName(advancement)).setLore(chatColor +
                        advancementManager.getDescription(advancement)).build());

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(slot + 9, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(slot + 9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✗").build());
                }
                slot++;
            }
            // heartForMe
            if(formatBoolean) {
                String advancement = "heartForMe";

                ChatColor chatColor = advancementManager.getRarity(advancement);
                boolean status = advancementManager.getAdvancement(target.getUniqueId(), advancement);
                ItemStack heart = new ItemBuilder(Material.MUSIC_DISC_11).build();
                if(init.getInstance().getConfig().contains("items.heart")) {
                    String base64Heart = init.getInstance().getConfig().getString("items.heart");
                    heart = base64Manager.itemFromBase64(base64Heart);
                    ItemMeta meta = heart.getItemMeta();
                    meta.setCustomModelData(2);
                    meta.setDisplayName(chatColor + advancementManager.getName(advancement));
                    meta.setLore(List.of(chatColor + advancementManager.getDescription(advancement)));
                    heart.setItemMeta(meta);
                }

                if (status) {
                    chatColor = ChatColor.GREEN;
                    advancementInv.setItem(28, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setCustomModelData(123098).
                            setDisplayname(chatColor + advancementManager.getName(advancement))
                            .setLore(chatColor + "Geschafft: ✓").build());
                } else {
                    chatColor = ChatColor.RED;
                    advancementInv.setItem(28, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setCustomModelData(123098).
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
