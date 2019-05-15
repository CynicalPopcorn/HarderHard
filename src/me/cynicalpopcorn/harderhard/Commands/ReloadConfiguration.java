/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.cynicalpopcorn.harderhard.Commands;

import me.cynicalpopcorn.harderhard.HarderHard;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Nicole
 */
public class ReloadConfiguration implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //Reload the config
        HarderHard.getInstance().reloadConfig();
        
        //Tell them
        cs.sendMessage(String.format("%sHarderHard v%s Configuration Reloaded.", Color.AQUA, HarderHard.getInstance().getConfig().getDouble("do-not-edit.version")));
        return true;
    }
}
