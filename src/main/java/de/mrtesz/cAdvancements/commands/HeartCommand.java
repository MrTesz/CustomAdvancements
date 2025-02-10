package de.mrtesz.cAdvancements.commands;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HeartCommand implements CommandExecutor {

    private final NamespacedKey key;

    public HeartCommand() {
        this.key = new NamespacedKey("smp", "isheart");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cNur Spieler können diesen Befehl nutzen!");
            return false;
        }
        if(!player.hasPermission("cadvancements.getheart")) {
            player.sendMessage("§cDu hast keine Berechtigung für diesen Befehl!");
            return false;
        }

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = skull.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(key, PersistentDataType.STRING, "true");

            skull.setItemMeta(meta);
        }

        player.getInventory().addItem(skull);
        player.sendMessage("§aDu hast einen Herz erhalten!");

        return true;
    }
}
