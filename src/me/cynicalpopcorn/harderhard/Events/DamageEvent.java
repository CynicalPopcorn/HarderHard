/* 
 * Copyright (C) 2019 Nicole
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.cynicalpopcorn.harderhard.Events;

import me.cynicalpopcorn.harderhard.HarderHard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

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
