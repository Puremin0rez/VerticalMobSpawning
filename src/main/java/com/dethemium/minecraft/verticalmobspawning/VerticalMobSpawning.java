package com.dethemium.minecraft.verticalmobspawning;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

public final class VerticalMobSpawning extends JavaPlugin implements Listener {

    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("settings.global.height", 24);
        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMobSpawn(PreCreatureSpawnEvent event) {
        if (event.getReason() != SpawnReason.NATURAL || !isMonster(event.getType())) {
            return;
        }
        if (!isLocationWithinVerticalRangeOfPlayer(event.getSpawnLocation(), 24)) {
            event.setShouldAbortSpawn(true);
            event.setCancelled(true);
        }
    }

    private boolean isMonster(EntityType type) {
        if (type.getEntityClass() != null) {
            return Monster.class.isAssignableFrom(type.getEntityClass());
        }
        return false;
    }

    private boolean isLocationWithinVerticalRangeOfPlayer(Location location, int range) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            return getVerticalDistanceBetween(player.getLocation(), location) <= range;
        }
        return false;
    }

    private double getVerticalDistanceBetween(Location location1, Location location2) {
        return Math.abs(location1.getY() - location2.getY());
    }
}
