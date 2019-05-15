/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.harderhard.Events;

import java.util.ArrayList;
import java.util.List;
import me.cynicalpopcorn.harderhard.HarderHard;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 *
 * @author Nicole
 */
public class MiscEvents implements Listener {
    private List<Player> sleepingPlayers = new ArrayList<Player>();
    
    @EventHandler
    public void onXPGain(PlayerExpChangeEvent event) {
        //Get player
        Player playerGaining = (Player) event.getPlayer();
        
        //Get configuration
        FileConfiguration config = HarderHard.getInstance().getConfig();
        
        if (config.getBoolean("misc.experience-nerf.enabled")) {
            event.setAmount((int) Math.round(event.getAmount() * config.getDouble("misc.experience-nerf.scale")));
        }
    }
    
    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        //Get configuration
        FileConfiguration config = HarderHard.getInstance().getConfig();
        if (!config.getBoolean("misc.bed-hunger.enabled")) return;
        
        //Add the player to the sleep list
        if (event.useBed() == Event.Result.ALLOW) {
            sleepingPlayers.add(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onExitBed(PlayerBedLeaveEvent event) {
        //Get configuration
        FileConfiguration config = HarderHard.getInstance().getConfig();
        if (!config.getBoolean("misc.bed-hunger.enabled")) return;
        
        //Get the time on the server
        Player player = event.getPlayer();
        World curWorld = player.getWorld();
        
        //Is it day?
        if (isDay(curWorld)) {
            if (curWorld.isThundering()) {
                //Assume slept 
                int curFood = player.getFoodLevel();
                player.setFoodLevel(curFood - config.getInt("misc.bed-hunger.value"));
            }
        }
        
        sleepingPlayers.remove(player);
    }
    
    private boolean isDay(World w) {
        long time = w.getTime();
        return time < 12300 || time > 23850;
    }
}
