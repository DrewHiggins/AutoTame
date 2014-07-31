/**
 * @author Drew Higgins (www.drewhiggins.com)
 * 
 * Licensed under the MIT License
 */

package com.drewhiggins.bukkit.autotame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            saveDefaultConfig();
            getServer().getPluginManager().registerEvents(new WolfSpawnListener(this), this);
            if (this.getConfig().getBoolean("killWolvesOnDeath")) {
                getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
            }
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
                Player playerSender = (Player)sender;
                Wolf closestOwnedWolf = getNearestOwnedWolf(playerSender);
                DyeColor selectedDyeColor;
                if (args[0].trim().equalsIgnoreCase("random")) {
                    selectedDyeColor = getRandomDyeColor();
                } else {
                    selectedDyeColor = getDyeColorFromString(args[0], playerSender, closestOwnedWolf.getCollarColor());
                }
                closestOwnedWolf.setCollarColor(selectedDyeColor);
                getLogger().info(playerSender.toString() + " changed a wolf's collar color to " + selectedDyeColor.toString());
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
    
    // converts a string color to a DyeColor enum, or leaves the color unchanged if not found
    public DyeColor getDyeColorFromString(String c, Player commander, DyeColor currentColor) {
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
        else if (c.equalsIgnoreCase("light_blue")) {
            return DyeColor.LIGHT_BLUE;
        }
        else if (c.equalsIgnoreCase("yellow")) {
            return DyeColor.YELLOW;
        }
        else if (c.equalsIgnoreCase("lime")) {
            return DyeColor.LIME;
        }
        else if (c.equalsIgnoreCase("pink")) {
            return DyeColor.PINK;
        }
        else if (c.equalsIgnoreCase("gray")) {
            return DyeColor.GRAY;
        }
        else if (c.equalsIgnoreCase("silver")) {
            return DyeColor.SILVER;
        }
        else if (c.equalsIgnoreCase("cyan")) {
            return DyeColor.CYAN;
        }
        else if (c.equalsIgnoreCase("pruple")) {
            return DyeColor.PURPLE;
        }
        else if (c.equalsIgnoreCase("brown")) {
            return DyeColor.BROWN;
        }
        else if (c.equalsIgnoreCase("green")) {
            return DyeColor.GREEN;
        }
        else if (c.equalsIgnoreCase("black")) {
            return DyeColor.BLACK;
        }
        else {
            commander.sendMessage("Requested color not found!");
            return currentColor;
        }
    }
    
    public DyeColor getRandomDyeColor() {
        DyeColor[] possibleColors = DyeColor.class.getEnumConstants();
        Random random = new Random();
        int randIndex = random.nextInt(possibleColors.length);
        return possibleColors[randIndex];
    }
}
