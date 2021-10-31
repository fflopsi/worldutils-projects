package me.frauenfelderflorian.wuprojects;

import me.frauenfelderflorian.worldutils.WorldUtils;
import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.commands.CAllItems;
import me.frauenfelderflorian.wuprojects.projects.AllItems;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main plugin class
 */
public final class WUProjects extends JavaPlugin {
    /**
     * The necessary WorldUtils instance
     */
    public WorldUtils utils;
    /**
     * The AllItems project
     */
    public AllItems allItems;

    /**
     * Done on plugin enabling
     */
    @Override
    public void onEnable() {
        //get the WorldUtils instance
        utils = WorldUtils.getInstance();
        //initialize the projects
        if (utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)) allItems = new AllItems(this);
        //set CommandExecutors and TabCompleters
        Objects.requireNonNull(getCommand(CAllItems.CMD)).setExecutor(new CAllItems(this));
        Objects.requireNonNull(getCommand(CAllItems.CMD)).setTabCompleter(new CAllItems(this));
        //register Listener
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
    }

    /**
     * Done on plugin disabling
     */
    @Override
    public void onDisable() {
    }
}
