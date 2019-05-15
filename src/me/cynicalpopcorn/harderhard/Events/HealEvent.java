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
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

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
