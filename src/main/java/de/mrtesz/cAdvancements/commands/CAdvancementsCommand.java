package de.mrtesz.cAdvancements.commands;

import de.mrtesz.cAdvancements.utils.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CAdvancementsCommand implements CommandExecutor {

    private final AdvancementManager advancementManager;

    public CAdvancementsCommand(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl ist nur für Spieler.");
        }
        Player player = (Player) sender;
        if(!player.hasPermission("cadvancements.grantAdvancement") && !player.hasPermission("cadvancements.revokeAdvancement")) {
            player.sendMessage("§cDu hast keine Berechtigungen für diesen Befehl.");
            return false;
        }
        if(args.length != 3) {
            player.sendMessage("§cBitte benutze: §e/cadvancement <grant|revoke> <Spieler> <AdvancementID>");
            return false;
        }
        if(Bukkit.getPlayer(args[1]) == null) {
            player.sendMessage("§cDer Spieler ist nicht online");
            return false;
        }
        Collection<String> advancements = advancementManager.getAdvancements("all");
        if(!advancements.contains(args[2])) {
            player.sendMessage("§cDer Erfolg existiert nicht!");
            return false;
        }
        if(args[0].equalsIgnoreCase("grant")) {
            if(player.hasPermission("cadvancements.grantAdvancement")) {
                advancementManager.grantAdvancement(Bukkit.getPlayer(args[1]).getUniqueId(), args[2], player.getName());
            } else
                player.sendMessage("§cDu hast keine Berechtigung für diesen Command.");
        } else if(args[0].equalsIgnoreCase("revoke")) {
            if(player.hasPermission("cadvancements.revokeAdvancement")) {
                advancementManager.revokeAdvancement(Bukkit.getPlayer(args[1]).getUniqueId(), args[2], player.getName());
            } else
                player.sendMessage("§cDu hast keine Berechtigung für diesen Command.");
        } else {
            player.sendMessage("§cBitte benutze: §e/cadvancement <grant|revoke> <Spieler> <AdvancementID>");
        }
        return true;
    }
}