package com.drewhiggins.bukkit.autotame;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {
        try {
            getServer().getPluginManager().registerEvents(new WolfSpawnListener(this), this);
            getLogger().info("AutoTame successfully enabled.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onDisable() {
        getLogger().info("AutoTame has been disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("collar")) {
            if (sender instanceof Player && args.length == 1) {
                DyeColor selectedDyeColor = getDyeColorFromString(args[0], (Player)sender);
                Wolf closestOwnedWolf = getNearestOwnedWolf((Player)sender);
                closestOwnedWolf.setCollarColor(selectedDyeColor);
                return true;
            }
            return false;   
        }
        return false;
    }
    
    public List<Wolf> getNearbyOwnedWolves(Player player) {
        List<Wolf> nearbyOwnedWolves = new ArrayList<Wolf>();
        for(Entity entity : player.getNearbyEntities(25, 25, 25)){
            if (entity.getType() == EntityType.WOLF) {
                Wolf wolf = (Wolf)entity;
                if (wolf.getOwner().equals(player)) {
                    nearbyOwnedWolves.add(wolf);
                }
            }
        }
        return nearbyOwnedWolves;
    }
    
    public Wolf getNearestOwnedWolf(Player player) {
        List<Wolf> nearbyOwnedWolves = getNearbyOwnedWolves(player);
        Wolf closestWolf = nearbyOwnedWolves.get(0);
        for (Wolf w : nearbyOwnedWolves) {
            if (w.getLocation().distanceSquared(player.getLocation()) < closestWolf.getLocation().distanceSquared(player.getLocation())) {
                closestWolf = w;
            }
        }        
        return closestWolf;
    }
    
    public DyeColor getDyeColorFromString(String c, Player commander) {
        if (c.equalsIgnoreCase("red")) {
            return DyeColor.RED;
        }
        else if (c.equalsIgnoreCase("blue")) {
            return DyeColor.BLUE;
        }
        else if (c.equalsIgnoreCase("orange")) {
            return DyeColor.ORANGE;
        }
        else if (c.equalsIgnoreCase("magenta")) {
            return DyeColor.MAGENTA;
        }
        else {
            commander.sendMessage("Requested color not found!");
            return DyeColor.RED;
        }
    }
}