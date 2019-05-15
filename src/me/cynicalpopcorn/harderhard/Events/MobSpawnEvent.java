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
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Handler(s) for hostile mob related events such as spawning
 * @author Nicole
 */
public class MobSpawnEvent implements Listener {
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        //Only work for monster
        if (event.getEntity() instanceof Monster) {
            //Get the monster
            Monster monsterSpawned = (Monster) event.getEntity();
            
            //Get configuration
            FileConfiguration config = HarderHard.getInstance().getConfig();
            
            //Set the monsters health
            if (config.getBoolean("mobs.spawning.health-boost.enabled")) {
                monsterSpawned.setHealth(config.getDouble("mobs.spawning.health-boost.scale") * monsterSpawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
        }
    }
}
