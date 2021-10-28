package me.frauenfelderflorian.wuprojects;

import me.frauenfelderflorian.worldutils.WorldUtils;
import me.frauenfelderflorian.wuprojects.commands.CAllItems;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class WUProjects extends JavaPlugin {
    public WorldUtils utils;
    public AllItems allItems;

    @Override
    public void onEnable() {
        utils = WorldUtils.getInstance();
        Objects.requireNonNull(getCommand(CAllItems.CMD)).setExecutor(new CAllItems(this));
        Objects.requireNonNull(getCommand(CAllItems.CMD)).setTabCompleter(new CAllItems(this));
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
