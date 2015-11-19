package me.josh.bountys.listeners;

import me.josh.bountys.Bountys;
import me.josh.bountys.utils.BountyState;
import me.josh.bountys.utils.BountysGUI;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
	
	static FileConfiguration config = Bountys.getInstance().getConfig();
	BountysGUI gui = new BountysGUI();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getName().equals(e.getInventory().getName())) {
		    if (e.getCurrentItem().getType() == Material.getMaterial(config.getInt("inventory.exit.id"))) {
		    	e.setCancelled(true);
				p.closeInventory();
		    } else if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
		    	e.setCancelled(true);
		    } else if (e.getCurrentItem().getType() == Material.getMaterial(config.getInt("inventory.addBounty.id"))) {
		    	e.setCancelled(true);
		    	
		    	if (!BountyState.hasState(p)) {
		    		BountyState.setState(p, BountyState.CHOOSING_AMOUNT);
		    		
		    		p.sendMessage(config.getString("amountOfBounty").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    	}
		    	
		    	p.closeInventory();
		    }
		}
	}

}
