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
package me.cynicalpopcorn.harderhard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import me.cynicalpopcorn.harderhard.Commands.ReloadConfiguration;
import me.cynicalpopcorn.harderhard.Events.DamageEvent;
import me.cynicalpopcorn.harderhard.Events.HealEvent;
import me.cynicalpopcorn.harderhard.Events.MiscEvents;
import me.cynicalpopcorn.harderhard.Events.MobSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Nicole
 */
public class HarderHard extends JavaPlugin {
     //Instance
    private static HarderHard instance;
    private static World overworld;
    
    @Override
    public void onEnable() {
        //Get instance
        instance = this;
        overworld = getServer().getWorlds().get(0);
        
        //Get the difficulty
        if (overworld.getDifficulty() != Difficulty.HARD) {
            getLogger().info("Disabling HarderHard as it's intended for hard difficulty.");
            return;
        }
        
        //Setup config
        SetupConfiguration();
        
        //Check for updates 
        UpdateCheck();
        
        //Setup Commands
        SetupCommands();
        
        //Add events
        SetupEvents();
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Disabled HarderHard");
    }
    
    public static HarderHard getInstance() {
        return instance;
    }
    
    public static World getOverworld() {
        return overworld;
    }
    
    public void SetupConfiguration() {
        //Default config     
        //Nerf regen
        getConfig().addDefault("nerf-regen.hunger.enabled", true);
        getConfig().addDefault("nerf-regen.hunger.scale", 0.75);
        getConfig().addDefault("nerf-regen.hunger.hardernight.enabled", true);
        getConfig().addDefault("nerf-regen.hunger.hardernight.scalenight", 0);
        getConfig().addDefault("nerf-regen.potion.instant.enabled", true);
        getConfig().addDefault("nerf-regen.potion.instant.scale", 0.75);
        getConfig().addDefault("nerf-regen.potion.regen.enabled", true);
        getConfig().addDefault("nerf-regen.potion.regen.scale", 0.75);
        
        //Damage extra
        getConfig().addDefault("extra-damage.hunger.enabled", true);
        getConfig().addDefault("extra-damage.fire-tick.enabled", true);
        getConfig().addDefault("extra-damage.hunger.value", 1);
        getConfig().addDefault("extra-damage.fire-tick.value", 1);
        getConfig().addDefault("extra-damage.mob-damage.enabled", true);
        getConfig().addDefault("extra-damage.mob-damage.value", 1.5);
        getConfig().addDefault("extra-damage.projectiles.enabled", true);
        getConfig().addDefault("extra-damage.projectiles.value", 1);
        
        //Mob spawning config
        getConfig().addDefault("mobs.spawning.health-boost.enabled", true);
        getConfig().addDefault("mobs.spawning.health-boost.scale", 1.5);
        
        //Experience
        getConfig().addDefault("misc.experience-nerf.enabled", true);
        getConfig().addDefault("misc.experience-nerf.scale", 0.5);
        getConfig().addDefault("misc.bed-hunger.enabled", true);
        getConfig().addDefault("misc.bed-hunger.value", 10);
        
        //DO NOT CHANGE
        //WILL BREAK AUTO-UPDATING
        getConfig().addDefault("do-not-edit.version", 0.1);
        
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    
    public void SetupCommands() {
        getCommand("hhreload").setExecutor(new ReloadConfiguration());
    }
    
    public void SetupEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new HealEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MiscEvents(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MobSpawnEvent(), this);
    }
    
    public void UpdateCheck() {
        URL url = null;
        URLConnection connection = null;
        BufferedReader br;
        
        try {
            url = new URL("https://plugins.mafuyu.club/harderhard.txt");
            connection = url.openConnection();            
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            if (br.readLine().equals(String.valueOf(getConfig().getDouble("do-not-edit.version")))) {
                getLogger().info("HarderHard is up to date!");
            } else {
                getLogger().info("HarderHard is outdated. You may wish to consider updating!");
            }
            
            return;
        } 
        catch (IOException ex) { } 
        
        getLogger().info("Error when executing update check. You may want to manually check.");
    }
}
