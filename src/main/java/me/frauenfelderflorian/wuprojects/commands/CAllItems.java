package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.apache.commons.lang.WordUtils;
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
                    if (plugin.utils.prefs.getInt(Prefs.Option.WUP_ALLITMES_INDEX) == 0) {
                        plugin.allItems = new AllItems(plugin);
                        Bukkit.broadcastMessage("§bNew AllItems project started!");
                    } else {
                        sender.sendMessage("§ePlease reset the running AllItems project before starting a new one.");
                        sender.sendMessage("§cType \"/allitems reset\" to reset the ongoing AllItems project.");
                    }
                    return true;
                }
                case "skip" -> {
                    sender.sendMessage("§eSkipping " + WordUtils.capitalizeFully(
                            plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS)
                                    .get(plugin.utils.prefs.getInt(Prefs.Option.WUP_ALLITMES_INDEX) - 1)
                                    .toString().replace('_', ' ')));
                    plugin.allItems.update(true);
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
