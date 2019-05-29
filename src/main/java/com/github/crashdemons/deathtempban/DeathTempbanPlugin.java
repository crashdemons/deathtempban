/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.deathtempban;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class DeathTempbanPlugin extends JavaPlugin implements Listener {
    private void reload(){
        reloadConfig();

    }
    
    @Override
    public void onEnable(){
        saveDefaultConfig();
        reload();
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    public void onDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        String playerName = p.getName();
        if(playerName==null) return;//nullable.
        String uuid = p.getUniqueId().toString();
        String key = "players."+uuid;
        
        int firstBanDuration = getConfig().getInt("configuratiion.initial-ban-seconds");
        int deathbanMultiplier = getConfig().getInt("configuratiion.death-ban-multiplier");
        String commandFormat = getConfig().getString("configuratiion.tempban-command");
        
        
        int deathcount = getConfig().getInt(key);
        deathcount++;
        getConfig().set(key, deathcount);
        
        double deathMultiplier = Math.pow(deathbanMultiplier, deathcount-1);
        double deathbanDuration = firstBanDuration * deathMultiplier;
        
       getServer().dispatchCommand(getServer().getConsoleSender(), String.format(commandFormat, playerName, (int) deathbanDuration));
        
        
        
        
        
        
        
        
        
        
    }
}
