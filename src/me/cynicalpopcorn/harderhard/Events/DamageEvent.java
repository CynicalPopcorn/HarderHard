/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.harderhard.Events;

import me.cynicalpopcorn.harderhard.HarderHard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * Damage event handling
 * @author Nicole
 */
public class DamageEvent implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        //Only done for player
        if (event.getEntity() instanceof Player) {
            //Get the player
            Player playerDamaged = (Player) event.getEntity();
            
            //Get configuration
            FileConfiguration config = HarderHard.getInstance().getConfig();
            
            switch (event.getCause()) {
                case STARVATION:
                    if (config.getBoolean("extra-damage.hunger.value.enabled")) event.setDamage(event.getDamage() + config.getDouble("extra-damage.hunger.value"));
                case FIRE_TICK:
                    if (config.getBoolean("extra-damage.hunger.fire-tick.enabled")) event.setDamage(event.getDamage() + config.getDouble("extra-damage.fire-tick.value"));
                case ENTITY_ATTACK:
                    if (config.getBoolean("extra-damage.mob-damage.enabled")) event.setDamage(event.getDamage() + config.getDouble("extra-damage.mob-damage.value"));
                case PROJECTILE:
                    if (config.getBoolean("extra-damage.projectiles.enabled")) event.setDamage(event.getDamage() + config.getDouble("extra-damage.projectiles.value"));
            }
        }
    }
}
