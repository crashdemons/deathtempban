/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.deathtempban;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
    
    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("Disabled.");
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        //getLogger().info(" PDE ");
        Player p = event.getEntity();
        String playerName = p.getName();
        if(playerName==null) return;//nullable.
        String uuid = p.getUniqueId().toString();
        String key = "players."+uuid;
        
        //getLogger().info("   "+key);
        
        int firstBanDuration = getConfig().getInt("configuration.initial-ban-seconds");
        int deathbanMultiplier = getConfig().getInt("configuration.death-ban-multiplier");
        String commandFormat = getConfig().getString("configuration.tempban-command");
        
        
        int deathcount = getConfig().getInt(key);
        //getLogger().info("    "+deathcount);
        deathcount++;
        getConfig().set(key, deathcount);
        
        double deathMultiplier = Math.pow(deathbanMultiplier, deathcount-1);
        double deathbanDuration = firstBanDuration * deathMultiplier;
        
        //getLogger().info(commandFormat);
        //getLogger().info("    "+String.format(commandFormat, playerName, (int) deathbanDuration));
        
        
        
        
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run()
            {
                getServer().dispatchCommand(getServer().getConsoleSender(), String.format(commandFormat, playerName, (int) deathbanDuration));
            }
        }, 20L);
        
        
        
        
       
        
        
        
        
        
        
        
        
        
        
    }
}
