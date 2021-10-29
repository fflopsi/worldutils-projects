package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * CommandExecutor and TabCompleter for command allitems
 */
public record CAllItems(WUProjects plugin) implements TabExecutor {
    public static final String CMD = "allitems";

    /**
     * Done when command sent
     *
     * @param sender  sender of the command
     * @param command sent command
     * @param alias   used alias
     * @param args    used arguments
     * @return true if correct command syntax used and no errors, false otherwise
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1)
            switch (args[0]) {
                case "start" -> {
                    //start a new project if possible
                    if (!plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) {
                        plugin.allItems = new AllItems(plugin);
                        Bukkit.broadcastMessage("§bNew AllItems project started!");
                    } else {
                        sender.sendMessage("§ePlease reset the AllItems project before starting a new one.");
                        sender.sendMessage("§cType \"/allitems reset\" to reset the AllItems project.");
                    }
                    return true;
                }
                case "skip" -> {
                    //skip the current item if possible
                    if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) plugin.allItems.update(true);
                    else sender.sendMessage("§eThe AllItems project is not running.");
                    return true;
                }
                case "reset" -> {
                    //reset the project
                    if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) {
                        Bukkit.broadcastMessage("§cResetting AllItems project.");
                        Bukkit.broadcastMessage("§eStart the AllItems project with \"/allitems start\".");
                        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, false, true);
                        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_ITEMS);
                        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITEMS_OBTAINED);
                        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_INDEX);
                        plugin.allItems.itemBar.setVisible(false);
                        plugin.allItems = null;
                    } else {
                        sender.sendMessage("§eThe AllItems project is not running.");
                    }
                    return true;
                }
            }
        return false;
    }

    /**
     * Done while entering command
     *
     * @param sender  sender of the command
     * @param command sent command
     * @param alias   used alias
     * @param args    used arguments
     * @return List of Strings for tab completion
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING))
            completions.addAll(List.of("skip", "reset"));
        else completions.add("start");
        return completions;
    }
}
