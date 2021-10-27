package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public record CAllItems(WUProjects plugin) implements TabExecutor {
    public static final String CMD = "allitems";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1)
            switch (args[0]) {
                case "new" -> {
                    if (plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS) == null) {
                        plugin.allItems = new AllItems(plugin);
                        Bukkit.broadcastMessage("§bNew AllItems project started!");
                    } else {
                        sender.sendMessage("§ePlease reset the running AllItems project before starting a new one.");
                        sender.sendMessage("§cType \"/allitems reset\" to reset the ongoing AllItems project.");
                    }
                    return true;
                }
                case "skip" -> {
                    if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) plugin.allItems.update(true);
                    else sender.sendMessage("§eThe AllItems project is not running.");
                    return true;
                }
                case "reset" -> {
                    Bukkit.broadcastMessage("§cResetting AllItems project.");
                    Bukkit.broadcastMessage("§eStart a new AllItems project with \"/allitems new\".");
                    plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, false, true);
                    plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_ITEMS);
                    plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITEMS_OBTAINED);
                    plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_INDEX);
                    plugin.allItems = null;
                    return true;
                }
            }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
