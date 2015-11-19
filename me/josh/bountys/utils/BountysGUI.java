package me.josh.bountys.utils;

import java.util.ArrayList;
import java.util.Arrays;

import me.josh.bountys.Bountys;
import me.josh.bountys.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BountysGUI {
	
	static FileConfiguration config = Bountys.getInstance().getConfig();
	public static ArrayList<Inventory> invs = new ArrayList<Inventory>();
	
	@SuppressWarnings("deprecation")
	public static void openGUI(Player p) {
	    Inventory bountysGUI = Bukkit.createInventory(p, config.getInt("inventory.size"), config.getString("inventory.name").replace("&", "¤"));
		
	    int current_page = 1;
	    
	    for (Player all : Bukkit.getOnlinePlayers()) {
	   		if (Bukkit.getOnlinePlayers().size() <= (config.getInt("inventory.size") - 9)) {
	   			if (FileUtils.hasBounty(all.getName())) {
		   			ItemStack skull = new ItemStack(Material.getMaterial(config.getInt("inventory.heads.id")), 1, (byte)config.getInt("inventory.heads.data"));
		   			ItemMeta skull_meta = skull.getItemMeta();
		    				
	  				skull_meta.setDisplayName(config.getString("inventory.heads.displayName").replace("%player", all.getName()).replace("&", "¤"));
	   				skull_meta.setLore(Arrays.asList(config.getString("inventory.heads.withBounty.lore").replace("%bounty", FileUtils.getBounty(all.getName()).toString()).replace("&", "¤")));
		    		skull.setItemMeta(skull_meta);
		    				
		    		bountysGUI.addItem(skull);
		    	} else if (!FileUtils.hasBounty(all.getName())) {
		   			ItemStack skull = new ItemStack(Material.getMaterial(config.getInt("inventory.heads.id")), 1, (byte)config.getInt("inventory.heads.data"));
			   		ItemMeta skull_meta = skull.getItemMeta();
			    				
			   		skull_meta.setDisplayName(config.getString("inventory.heads.displayName").replace("%player", all.getName()).replace("&", "¤"));
		  			skull_meta.setLore(Arrays.asList(config.getString("inventory.heads.withoutBounty.lore").replace("&", "¤")));
		    		skull.setItemMeta(skull_meta);
			    				
		    		bountysGUI.addItem(skull);
		    	}
	   		} else {
	   			
	   		}
	    }
	    		
	   	// Exit Menu Item
	    		
	   	ItemStack exit = new ItemStack(Material.getMaterial(config.getInt("inventory.exit.id")), 1);
 		ItemMeta exit_meta = exit.getItemMeta();
	    		
	   	exit_meta.setDisplayName(config.getString("inventory.exit.displayName").replace("&", "¤"));
	   	exit_meta.setLore(Arrays.asList(config.getString("inventory.exit.lore").replace("&", "¤")));
	   	exit.setItemMeta(exit_meta);
	    		
	   	bountysGUI.setItem((config.getInt("inventory.size") - 1), exit);
	    		
   		// Add Bounty Item
	    		
   		ItemStack addBounty = new ItemStack(Material.getMaterial(config.getInt("inventory.addBounty.id")), 1);
 		ItemMeta addBounty_meta = addBounty.getItemMeta();
	    		
  		addBounty_meta.setDisplayName(config.getString("inventory.addBounty.displayName").replace("&", "¤"));
  		addBounty_meta.setLore(Arrays.asList(config.getString("inventory.addBounty.lore").replace("&", "¤")));
	   	addBounty.setItemMeta(addBounty_meta);
	    		
	   	bountysGUI.setItem((config.getInt("inventory.size") - 9), addBounty);
	   	
	   	// Current Bounty Item
	   	
	   	ItemStack currentBounty = new ItemStack(Material.getMaterial(config.getInt("inventory.currentBounty.id")), 1);
	   	ItemMeta currentBounty_meta = currentBounty.getItemMeta();
	   	
	   	currentBounty_meta.setDisplayName(config.getString("inventory.currentBounty.displayName").replace("%bounty", FileUtils.getBounty(p.getName()).toString()).replace("&", "¤"));
	   	currentBounty.setItemMeta(currentBounty_meta);
	   	
	   	bountysGUI.setItem((config.getInt("inventory.size") - 5), currentBounty);
	    	
	   	if (Bukkit.getOnlinePlayers().size() > (config.getInt("inventory.size") - 9)) {
	   		//Player[] players = Bukkit.getOnlinePlayers();
	   		
			ItemStack nextPage = new ItemStack(Material.getMaterial(config.getInt("inventory.pageArrows.id")));
	   		ItemMeta nextPage_meta = nextPage.getItemMeta();
	    			
	   		Integer next = (current_page + 1);
	   		nextPage_meta.setDisplayName(config.getString("inventory.pageArrows.displayName").replace("%pageNumber", next.toString()).replace("&", "¤"));
			nextPage.setItemMeta(nextPage_meta);
	    			
	   		bountysGUI.setItem((config.getInt("inventory.size") - 4), nextPage);
	    			
	    	ItemStack backPage = new ItemStack(Material.getMaterial(config.getInt("inventory.pageArrows.id")));
	    	ItemMeta backPage_meta = backPage.getItemMeta();
	    			
	   		Integer back = (current_page - 1);
	    	backPage_meta.setDisplayName(config.getString("inventory.pageArrows.displayName").replace("%pageNumber", back.toString()).replace("&", "¤"));
	   		backPage.setItemMeta(backPage_meta);
	    			
			bountysGUI.setItem((config.getInt("inventory.size") - 6), backPage);
	    }
	   		
	   	p.openInventory(bountysGUI);
    }

}