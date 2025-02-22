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

    private final Base64Manager base64Manager;
    private final Init init;

    public SaveItemCommand(Base64Manager base64Manager, Init init) {
        this.base64Manager = base64Manager;
        this.init = init;
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
        FileConfiguration config = init.getInstance().getConfig();
        switch (args[0]) {
            case "reward":
                config.set("rewards.item", base64Manager.itemToBase64(item));
                init.getInstance().saveConfig();
                player.sendMessage("§a" + item + " ist nun der Erfolg-Reward!");
                break;
            case "heart":
                config.set("items.heart", null);
                init.getInstance().saveConfig();
                player.sendMessage("§cDieser Command wird wegen entfernung des Erfolges 'Hast du ein Herz für mich.' nicht mehr gebraucht, Herz Item wurde aus der config gelöscht.");
                break;
            default:
                player.sendMessage("§cBitte benutze: §e/saveitem <use>");
                break;
        }
        return true;
    }
}
