package me.frauenfelderflorian.wuprojects;

import me.frauenfelderflorian.wuprojects.commands.CAllItems;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WUProjects extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("allitems")).setExecutor(new CAllItems());
        Objects.requireNonNull(getCommand("allitems")).setTabCompleter(new CAllItems());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
