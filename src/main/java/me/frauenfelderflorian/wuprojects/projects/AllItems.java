package me.frauenfelderflorian.wuprojects.projects;

import me.frauenfelderflorian.worldutils.config.Prefs;
import me.frauenfelderflorian.wuprojects.WUProjects;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllItems {
    public BossBar itemBar;
    private int index;
    private final List<Material> items;
    private final List<Material> obtained;
    private final WUProjects plugin;

    public AllItems(WUProjects plugin) {
        this.plugin = plugin;
        if (plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS) == null) {
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
            obtained = new ArrayList<>();
            index = 0;
        } else {
            items = new ArrayList<>();
            for (Object item : plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS))
                items.add((Material) item);
            obtained = new ArrayList<>();
            for (Object item : plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITEMS_OBTAINED))
                items.add((Material) item);
            index = plugin.utils.prefs.getInt(Prefs.Option.WUP_ALLITMES_INDEX);
        }
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, true, true);
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITMES_ITEMS, items, true);
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_OBTAINED, obtained, true);
        itemBar = Bukkit.createBossBar("Next item: §b§l", BarColor.BLUE, BarStyle.SOLID);
        itemBar.setVisible(true);
        update(false);
    }

    public void update(boolean next) {
        if (next) {
            obtained.add(items.get(index));
            index++;
        }
        if (index < items.size()) {
            Bukkit.broadcastMessage("§bNext item to collect: §l" + itemName(items.get(index)));
            itemBar.setTitle("Next item: §b§l" + itemName(items.get(index)));
            itemBar.setProgress((double) index / items.size());
        } else {
            Bukkit.broadcastMessage("§bAll items collected! §lCongratulations, you finished the project §oAllItems!");
            index = -1;
            itemBar.setTitle("§b§lAll items collected!");
            plugin.utils.prefs.set(Prefs.Option.WUP_ALLITEMS_RUNNING, false, true);
        }
        plugin.utils.prefs.set(Prefs.Option.WUP_ALLITMES_INDEX, index, true);
    }

    private static String itemName(Material item) {
        return WordUtils.capitalizeFully(item.toString().replace('_', ' '));
    }
}
