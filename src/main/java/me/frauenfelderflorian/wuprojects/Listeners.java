package me.frauenfelderflorian.wuprojects;

import me.frauenfelderflorian.worldutils.config.Prefs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listener class for triggered events
 */
public record Listeners(WUProjects plugin) implements Listener {
    /**
     * Executed when an entity picks up an item: Update AllItems project if the requirements are met
     *
     * @param event the triggered EntityPickupItemEvent
     */
    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player && plugin.utils.prefs.getBoolean(Prefs.Option.TIMER_RUNNING)
                && plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)
                && event.getItem().getItemStack().getType() == plugin.allItems.getNextItem()) {
            plugin.allItems.update(true);
        }
    }

    /**
     * Executed when a player joins: Add the player to the AllItems projects
     *
     * @param event the triggered PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING))
            plugin.allItems.addPlayer(event.getPlayer());
    }
}
