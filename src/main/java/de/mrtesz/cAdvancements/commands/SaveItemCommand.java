package de.mrtesz.cAdvancements.commands;

import de.mrtesz.cAdvancements.utils.Base64Manager;
import de.mrtesz.cAdvancements.utils.Init;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SaveItemCommand implements CommandExecutor {

    private Base64Manager base64Manager;

    public SaveItemCommand(Base64Manager base64Manager) {
        this.base64Manager = base64Manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cDieser Command ist nur für Spieler.");
            return false;
        }
        if(!player.hasPermission("cadvancements.saveItems")) {
            player.sendMessage("§cDu hast keine Berechtigung für diesen Command!");
            return false;
        }
        if(args.length != 1) {
            player.sendMessage("§cBitte benutze: §e/saveitem <benutzung>");
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType().equals(Material.AIR) || item.getType().equals(Material.MUSIC_DISC_11)) {
            player.sendMessage("§cDas Item kann nicht Luft sein!");
            return false;
        }
        FileConfiguration config = Init.getInstance().getConfig();
        switch (args[0]) {
            case "reward":
                config.set("rewards.item", base64Manager.itemToBase64(item));
                Init.getInstance().saveConfig();
                player.sendMessage("§a" + item + " ist nun der Erfolg-Reward!");
                break;
            case "heart":
                config.set("items.heart", base64Manager.itemToBase64(item));
                Init.getInstance().saveConfig();
                player.sendMessage("§a" + item + " ist nun das Herz-Item!");
                break;
            default:
                player.sendMessage("§cBitte benutze: §e/saveitem <benutzung>");
                break;
        }
        return true;
    }
}
