package me.josh.bountys;

import me.josh.bountys.commands.BountyCommand;
import me.josh.bountys.listeners.AsyncChat;
import me.josh.bountys.listeners.BountysPlayerDeath;
import me.josh.bountys.listeners.InventoryClick;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Bountys extends JavaPlugin {
	
	private static Bountys instance;
	public static boolean broadcastBounty;
	public static Economy economy;
	
	public static Bountys getInstance() {
		return instance;
	}
	
	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
	    	System.out.println("[Bountys] Error: Vault not found, disabling Bountys");
	    	getServer().getPluginManager().disablePlugin(this);
	    }
		
		instance = this;
		broadcastBounty = getConfig().getBoolean("broadcastBounty"); //Values: true = Broadcast, false = Dont Broadcast
		
		saveDefaultConfig();
		
	    FileUtils.loadBountiesFile();
	    setupEconomy();
		
		getCommand("bounty").setExecutor(new BountyCommand());
		
		Bukkit.getServer().getPluginManager().registerEvents(new BountysPlayerDeath(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new AsyncChat(), this);
		
		System.out.println("[Bountys] Successfully enabled Bountys v0.1 by MinecraftJoshjr, thanks for using my plugin and please report all bugs :D");
	}
	
	public void onDisable() {
		System.out.println("[Bountys] Successfully disabled Bountys v0.1 by MinecraftJoshjr, goodbye");
	}
	
	private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

}
