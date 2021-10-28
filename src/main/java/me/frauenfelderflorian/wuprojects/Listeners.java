package me.frauenfelderflorian.wuprojects;

import me.frauenfelderflorian.worldutils.config.Prefs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public record Listeners(WUProjects plugin) implements Listener {
    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player && plugin.utils.prefs.getBoolean(Prefs.Option.TIMER_RUNNING)
                && plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING)
                && event.getItem().getItemStack().getType()
                == plugin.utils.prefs.getList(Prefs.Option.WUP_ALLITMES_ITEMS)
                .get(plugin.utils.prefs.getInt(Prefs.Option.WUP_ALLITMES_INDEX))) {
            plugin.allItems.update(true);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (plugin.utils.prefs.getBoolean(Prefs.Option.WUP_ALLITEMS_RUNNING))
            plugin.allItems.itemBar.addPlayer(event.getPlayer());
    }
}
