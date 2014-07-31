package com.drewhiggins.bukkit.autotame;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfSpawnListener implements Listener {
    
    private JavaPlugin plugin;
    
    public WolfSpawnListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onWolfSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.SPAWNER_EGG && event.getEntity().getType() == EntityType.WOLF) {
            Player spawner = getClosestPlayer(event.getLocation());
            Wolf spawnedWolf = (Wolf)event.getEntity();
            spawnedWolf.setTamed(true);
            spawnedWolf.setOwner(spawner);
            plugin.getServer().getLogger().info("[AutoTame] A wolf spawn egg was used. Set owner as " + spawner.getName() + ".");
        }
    }
    
    // returns the player closest to the given location
    public Player getClosestPlayer(Location loc) {
        Player closestPlayer = plugin.getServer().getOnlinePlayers()[0];
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.getLocation().distanceSquared(loc) < closestPlayer.getLocation().distanceSquared(loc)) {
                closestPlayer = p;
            }
        }
        return closestPlayer;
    }
    
}
