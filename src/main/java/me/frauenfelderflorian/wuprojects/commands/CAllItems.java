package me.frauenfelderflorian.wuprojects.commands;

import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class CAllItems implements TabExecutor {
    public static String command = "allitems";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        new AllItems();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
