package com.drewhiggins.bukkit.autotame;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDeathListener implements Listener {
    
    private JavaPlugin plugin;
    
    public PlayerDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getServer().getLogger().info(event.getEntity().getName() + " died. Killing all owned wolves.");
        List<Wolf> ownedWolves = getOwnedWolves((Player)(event.getEntity()));
        for (Wolf w : ownedWolves) {
            w.setHealth(0);
        }
    }
    
    public List<Wolf> getOwnedWolves(Player player) {
        List<Wolf> allWolves = new ArrayList<Wolf>();
        List<World> worlds = plugin.getServer().getWorlds();
        for (World w : worlds) {
            List<Entity> allEntities = w.getEntities();
            for (Entity e : allEntities) {
                if (e.getType() == EntityType.WOLF) {
                    allWolves.add((Wolf)e);
                }
            }
        }
        List<Wolf> ownedWolves = new ArrayList<Wolf>();
        for (Wolf w : allWolves) {
            if (w.isTamed()) {
                if (w.getOwner() == (AnimalTamer)player) {
                    ownedWolves.add(w);
                }
            }
        }
        return ownedWolves;
    }
    
}
