package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.worldutils.Messages;
import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1)
            switch (args[0]) {
                case "start" -> {
                    //start a new project if possible
                    if (!plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) {
                        Messages.sendMessage(plugin, "§bNew AllItems project started!");
                        plugin.allItems = new AllItems(plugin);
                        for (Player player : Bukkit.getOnlinePlayers()) plugin.allItems.addPlayer(player);
                    } else {
                        Messages.sendMessage(plugin, sender,
                                "§ePlease reset the AllItems project before starting a new one.");
                        Messages.sendMessage(plugin, sender,
                                "§cType \"/allitems reset\" to reset the AllItems project.");
                    }
                    return true;
                }
                case "skip" -> {
                    //skip the current item if possible
                    if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) plugin.allItems.update(true);
                    else Messages.sendMessage(plugin, sender, "§eThe AllItems project is not running.");
                    return true;
                }
                case "reset" -> {
                    //reset the project
                    if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) {
                        Messages.sendMessage(plugin, "§cResetting AllItems project.");
                        Messages.sendMessage(plugin, "§eStart the AllItems project with \"/allitems start\".");
                        plugin.allItems.reset();
                        plugin.allItems = null;
                    } else {
                        Messages.sendMessage(plugin, sender, "§eThe AllItems project is not running.");
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
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING))
            StringUtil.copyPartialMatches(args[0], List.of("skip", "reset"), completions);
        else StringUtil.copyPartialMatches(args[0], List.of("start"), completions);
        return completions;
    }
}
