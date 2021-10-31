package me.frauenfelderflorian.wuprojects.projects;

import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class containing the AllItems project (objective: collect all survival-obtainable items)
 */
public class AllItems {
    /**
     * Displays the next item and the overall progress
     */
    private final BossBar itemBar;
    /**
     * List of all items to obtaine
     */
    private final List<Material> items;
    /**
     * Index of the next item
     */
    private int index;
    /**
     * The WUProjects instance which this project belongs to
     */
    private final WUProjects plugin;

    /**
     * Create a new AllItems project
     *
     * @param plugin the plugin which the project belongs to
     */
    public AllItems(WUProjects plugin) {
        this.plugin = plugin;
        if (plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS) == null) {
            //create an all-new project
            //get all items
            items = new ArrayList<>(Arrays.asList(Material.values()));
            //remove all items that are not obtainable in survival
            items.removeIf(mat -> mat.isAir()
                    || mat == Material.BAMBOO_SAPLING
                    || mat == Material.BARRIER
                    || mat == Material.BEDROCK
                    || mat == Material.BEETROOTS
                    || mat == Material.BIG_DRIPLEAF_STEM
                    || mat == Material.BUBBLE_COLUMN
                    || mat == Material.BUDDING_AMETHYST
                    || mat == Material.CARROTS
                    || mat == Material.COCOA
                    || mat == Material.DEBUG_STICK
                    || mat == Material.DIRT_PATH
                    || mat == Material.END_GATEWAY
                    || mat == Material.FARMLAND
                    || mat == Material.FIRE
                    || mat == Material.FROSTED_ICE
                    || mat == Material.JIGSAW
                    || mat == Material.KNOWLEDGE_BOOK
                    || mat == Material.LARGE_FERN
                    || mat == Material.LAVA
                    || mat == Material.LIGHT
                    || mat == Material.MOVING_PISTON
                    || mat == Material.PETRIFIED_OAK_SLAB
                    || mat == Material.PISTON_HEAD
                    || mat == Material.PLAYER_HEAD
                    || mat == Material.POTATOES
                    || mat == Material.POWDER_SNOW
                    || mat == Material.REDSTONE_WIRE
                    || mat == Material.SOUL_FIRE
                    || mat == Material.SPAWNER
                    || mat == Material.SWEET_BERRY_BUSH
                    || mat == Material.TRIPWIRE
                    || mat == Material.WATER
                    || mat.toString().contains("CANDLE_CAKE")
                    || mat.toString().contains("_CAULDRON")
                    || mat.toString().contains("CAVE_VINES")
                    || mat.toString().contains("COMMAND_BLOCK")
                    || mat.toString().contains("INFESTED")
                    || mat.toString().contains("LEGACY")
                    || mat.toString().contains("MELON_STEM")
                    || mat.toString().contains("PLANT")
                    || mat.toString().contains("PORTAL")
                    || mat.toString().contains("POTTED")
                    || mat.toString().contains("PUMPKIN_STEM")
                    || mat.toString().contains("SPAWN_EGG")
                    || mat.toString().contains("STRUCTURE")
                    || mat.toString().contains("TALL")
                    || mat.toString().contains("WALL_")
            ); //makes 1003 survival items, some not yet obtainable in 1.17.1
            Collections.shuffle(items);
            index = 0;
            //save to preferences
            List<String> itemStrings = new ArrayList<>();
            for (Material item : items) itemStrings.add(item.toString());
            plugin.utils.prefs.set(Prefs.Option.WUP_ALLITMES_ITEMS, itemStrings, true);
        } else {
            //load the existing project
            items = new ArrayList<>();
            for (Object item : plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS))
                items.add(Material.getMaterial((String) item));
            index = plugin.utils.prefs.getInt(Prefs.Option.WUP_ALLITMES_INDEX);
        }
        //save to preferences
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, true, true);
        //set up the BossBar
        itemBar = Bukkit.createBossBar("Next item: §b§l", BarColor.BLUE, BarStyle.SOLID);
        itemBar.setVisible(true);
        update(false);
    }

    /**
     * Update the project
     *
     * @param next true if the next item should be displayed
     */
    public void update(boolean next) {
        //display next item
        if (next) index++;
        //set (new) progress
        itemBar.setProgress((double) index / items.size());
        if (index < items.size()) {
            //there are more items to collect
            Bukkit.broadcastMessage("§bNext item to collect: §l" + itemName(getNextItem()));
            itemBar.setTitle("Next item: §b§l" + itemName(getNextItem()));
        } else {
            //all items are collected
            Bukkit.broadcastMessage("§bAll items collected! §lCongratulations, you finished the project §oAllItems!");
            index = -1;
            itemBar.setTitle("§b§lAll items collected!");
            plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, false, true);
        }
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITMES_INDEX, index, true);
    }

    /**
     * Get the next item to collect
     *
     * @return Material type of the next item
     */
    public Material getNextItem() {
        return items.get(index);
    }

    /**
     * Add a player to the project, so that they can see the BossBar
     *
     * @param player the Player to be added
     */
    public void addPlayer(Player player) {
        itemBar.addPlayer(player);
    }

    /**
     * Reset the whole project
     */
    public void reset() {
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, false, true);
        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_ITEMS);
        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITEMS_OBTAINED);
        plugin.utils.prefs.remove(Prefs.Option.WUP_ALLITMES_INDEX);
        itemBar.removeAll();
    }

    /**
     * Get a nicely formatted (Title Case) item name from a Material
     * <p>
     * Example: item.toString() returns "TITLE_CASE" --> this method returns "Title Case"
     *
     * @param item the Material whose name to get
     * @return the formatted String containing the name of the item
     */
    private static String itemName(Material item) {
        return WordUtils.capitalizeFully(item.toString().replace('_', ' '));
    }
}
