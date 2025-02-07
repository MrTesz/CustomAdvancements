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

import java.util.List;

public class SaveRewardItemCommand implements CommandExecutor {

    private Init init;
    private Base64Manager base64Manager;

    public SaveRewardItemCommand(Init init) {
        this.init = init;
        base64Manager = new Base64Manager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cDieser Command ist nur für Spieler.");
            return false;
        }
        if(!player.hasPermission("cadvancements.saverewarditem")) {
            player.sendMessage("§cDu hast keine Berechtigung für diesen Command!");
            return false;
        }
        if(args.length != 0) {
            player.sendMessage("§cBitte benutze: §e/saverewarditem");
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType().equals(Material.AIR) || item.getType().equals(Material.MUSIC_DISC_11)) {
            player.sendMessage("§cDas Item kann nicht Luft sein!");
            return false;
        }
        init.getInstance().getConfig().set("rewards.item", base64Manager.itemToBase64(item));
        init.getInstance().saveConfig();
        player.sendMessage("§a" + item + " zu Rewards hinzugefügt!");

        return true;
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
