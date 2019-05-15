/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.harderhard.Events;

import me.cynicalpopcorn.harderhard.HarderHard;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

/**
 *
 * @author Nicole
 */
public class HealEvent implements Listener {
    @EventHandler
    public void onDamage(EntityRegainHealthEvent event) {
        //Only done for player
        if (event.getEntity() instanceof Player) {
            //Get the player
            Player playerHealed = (Player) event.getEntity();
            
            //Get configuration
            FileConfiguration config = HarderHard.getInstance().getConfig();
            
            switch(event.getRegainReason()) {
                case SATIATED:
                    if (config.getBoolean("nerf-regen.hunger.enabled")) {
                        if (config.getBoolean("nerf-regen.hunger.hardernight.enabled")) {
                            if (!isDay(playerHealed.getWorld())) {
                                event.setAmount(config.getDouble("nerf-regen.hunger.hardernight.scalenight") * event.getAmount());
                                return;
                            }
                        }
                    } 
                    
                    event.setAmount(config.getDouble("nerf-regen.hunger.scale") * event.getAmount());
                case MAGIC:
                    if (config.getBoolean("nerf-regen.potion.instant.enabled")) event.setAmount(config.getDouble("nerf-regen.potion.instant.scale") * event.getAmount());
                case MAGIC_REGEN:
                    if (config.getBoolean("nerf-regen.potion.regen.enabled")) event.setAmount(config.getDouble("nerf-regen.potion.regen.scale") * event.getAmount());
            }
        }
    }
    
    private boolean isDay(World w) {
        long time = w.getTime();
        return time < 12300 || time > 23850;
    }
}
