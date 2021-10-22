package me.frauenfelderflorian.wuprojects.projects;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class AllItems {
    public AllItems() {
        //get all items
        ArrayList<Material> items = (ArrayList<Material>) Arrays.asList(Material.values());
        //remove all items that are not obtainable in survival //async?
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
                || mat.name().contains("CANDLE_CAKE")
                || mat.name().contains("_CAULDRON")
                || mat.name().contains("CAVE_VINES")
                || mat.name().contains("COMMAND_BLOCK")
                || mat.name().contains("INFESTED")
                || mat.name().contains("LEGACY")
                || mat.name().contains("MELON_STEM")
                || mat.name().contains("PLANT")
                || mat.name().contains("PORTAL")
                || mat.name().contains("POTTED")
                || mat.name().contains("PUMPKIN_STEM")
                || mat.name().contains("SPAWN_EGG")
                || mat.name().contains("STRUCTURE")
                || mat.name().contains("TALL")
                || mat.name().contains("WALL_")
        ); //should be 1003 now, some not yet obtainable in 1.17.1
    }
}
