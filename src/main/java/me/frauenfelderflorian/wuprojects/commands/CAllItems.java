package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.wuprojects.WUProjects;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public record CAllItems(WUProjects plugin) implements TabExecutor {
    public static final String CMD = "allitems";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        plugin.allItems = new AllItems(plugin);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
