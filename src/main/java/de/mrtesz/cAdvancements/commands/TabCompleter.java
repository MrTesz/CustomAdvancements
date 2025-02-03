package de.mrtesz.cAdvancements.commands;

import de.mrtesz.cAdvancements.CAdvancements;
import de.mrtesz.cAdvancements.utils.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private AdvancementManager advancementManager;

    public TabCompleter(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>(List.of());
        Collection<String> advancements = advancementManager.getAdvancements("all");

        List<String> onlinePlayers = new ArrayList<>(List.of());
        for(Player current : Bukkit.getOnlinePlayers()) {
            onlinePlayers.add(current.getName());
        }

        if(label.equalsIgnoreCase("cadvancement")) {
            if(args.length == 1) {
                completions.add("revoke");
                completions.add("grant");
            }
            if(args.length == 2) {
                completions.add("<Spieler>");
                completions.addAll(onlinePlayers);
            }
            if(args.length == 3) {
                completions.add("<AdvancementID>");
                completions.addAll(advancements);
            }
        }

        return completions;
    }
}
