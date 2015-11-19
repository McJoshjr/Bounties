package me.josh.bountys;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FileUtils {
	
	public static HashMap<String, Integer> current_page = new HashMap<String, Integer>();
	static FileConfiguration config = Bountys.getInstance().getConfig();
	public static Bountys plugin = Bountys.getInstance();
	public static File bountiesFile;
	public static FileConfiguration bountiesConfig;
	
	public static FileConfiguration getBountiesConfig() {
		return bountiesConfig;
	}
	
	public static void loadBountiesFile() {
		bountiesFile = new File(plugin.getDataFolder(), "bounties.yml");
		
		if (!bountiesFile.exists()) {
			try {
				bountiesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		bountiesConfig = YamlConfiguration.loadConfiguration(bountiesFile);
	}
	
	public static void reloadBountiesFile() {
		bountiesFile = new File(plugin.getDataFolder(), "bounties.yml");
		
		if (!bountiesFile.exists()) {
			try {
				bountiesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		bountiesConfig = YamlConfiguration.loadConfiguration(bountiesFile);
		
		try {
			bountiesConfig.load(bountiesFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static FileConfiguration getBountiesFile() {
		return bountiesConfig;
	}
	
	public static void saveBountiesFile() {
		try {
			getBountiesConfig().save(bountiesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasBounty(String p) {
		if (getBountiesConfig().get("Players." + p) != null) {
			return true;
		} else if (getBountiesConfig().get("Players." + p) == null) {
			return false;
		}
		return false;
	}
	
	public static Integer getBounty(String p) {
		if (getBountiesConfig().get("Players." + p) != null) {
			return getBountiesConfig().getInt("Players." + p + ".Bounty");
		} else if (getBountiesConfig().get("Players." + p) == null) {
			return 0;
		}
		return 0;
	}
	
	public static void addBounty(Player sender, Player p, Integer i) {
		if (Bountys.economy.getBalance(sender) < i) {
			sender.sendMessage(config.getString("notEnoughMoney").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
			return;
		} else if (Bountys.economy.getBalance(sender) >= i) {
			if (getBountiesConfig().get("Players." + p.getName()) != null) {
				int newBounty = (getBountiesConfig().getInt("Players." + p.getName() + ".Bounty") + i);
					
				getBountiesConfig().set("Players." + p.getName() + ".Bounty", newBounty);
				Bountys.economy.withdrawPlayer(sender, i);
					
				sender.sendMessage(config.getString("addBountySender").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", p.getName()).replace("&", "¤"));
   				p.sendMessage(config.getString("addBountyReceiver").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", sender.getName()).replace("&", "¤"));
    				
    			if (Bountys.broadcastBounty) {
    				Bukkit.broadcastMessage(config.getString("addBroadcastBounty").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", p.getName()).replace("&", "¤"));
   				}
    				
				saveBountiesFile();
				reloadBountiesFile();
				return;
			} else if (getBountiesConfig().get("Players." + p.getName()) == null) {
				getBountiesConfig().set("Players." + p.getName() + ".Bounty", i);	
				Bountys.economy.withdrawPlayer(sender, i);
					
				sender.sendMessage(config.getString("addBountySender").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", p.getName()).replace("&", "¤"));
    			p.sendMessage(config.getString("addBountyReceiver").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", sender.getName()).replace("&", "¤"));
    				
    			if (Bountys.broadcastBounty) {
   					Bukkit.broadcastMessage(config.getString("addBroadcastBounty").replace("%prefix", config.getString("Prefix")).replace("%bounty", i.toString()).replace("%target", p.getName()).replace("&", "¤"));
   				}
    				
				saveBountiesFile();
				reloadBountiesFile();
				return;
			}
		}
	}
	
	public static void removeBounty(String p) {
		if (getBountiesConfig().get("Players." + p) != null) {
			getBountiesConfig().set("Players." + p, null);
			
			saveBountiesFile();
			reloadBountiesFile();
			return;
		}
	}
	
    public static void removeBountyWithSender(Player sender, String p) {
		if (getBountiesConfig().get("Players." + p) != null) {
    		getBountiesConfig().set("Players." + p, null);
				
			saveBountiesFile();
			reloadBountiesFile();
			return;
		} else if (p == null) {
			sender.sendMessage(config.getString("targetNotFound").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
			return;
		}
	}
    
    @SuppressWarnings("deprecation")
	public static void openGUI(Player p) {
    	final Inventory bountysGUI = Bukkit.createInventory(p, config.getInt("inventory.size"), config.getString("inventory.name").replace("&", "¤"));
    	
    	for (Player all : Bukkit.getOnlinePlayers()) {
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
    	}
    		
    	// Exit Menu Item
    		
    	ItemStack exit = new ItemStack(Material.getMaterial(config.getInt("inventory.exit.id")), 1, (byte)config.getInt("inventory.exit.byte"));
   		ItemMeta exit_meta = exit.getItemMeta();
    		
   		exit_meta.setDisplayName(config.getString("inventory.exit.displayName").replace("&", "¤"));
    	exit_meta.setLore(Arrays.asList(config.getString("inventory.exit.lore").replace("&", "¤")));
    	exit.setItemMeta(exit_meta);
    		
    	bountysGUI.setItem((config.getInt("inventory.size") - 1), exit);
    		
   		// Add Bounty Item
    		
   		ItemStack addBounty = new ItemStack(Material.getMaterial(config.getInt("inventory.addBounty.id")), 1, (byte)config.getInt("inventory.addBounty.byte"));
   		ItemMeta addBounty_meta = addBounty.getItemMeta();
    		
   		addBounty_meta.setDisplayName(config.getString("inventory.addBounty.displayName").replace("&", "¤"));
   		addBounty_meta.setLore(Arrays.asList(config.getString("inventory.addBounty.lore").replace("&", "¤")));
   		addBounty.setItemMeta(addBounty_meta);
    		
   		bountysGUI.setItem((config.getInt("inventory.size") - 9), addBounty);
    	
   		if (Bukkit.getOnlinePlayers().size() < 45) {
   			ItemStack nextPage = new ItemStack(Material.getMaterial(config.getInt("inventory.pageArrows.id")));
   			ItemMeta nextPage_meta = nextPage.getItemMeta();
    			
   			Integer next = (current_page.get(p.getName()) + 1);
   			nextPage_meta.setDisplayName(config.getString("inventory.pageArrows.displayName").replace("%pageNumber", next.toString()).replace("&", "¤"));
   			nextPage.setItemMeta(nextPage_meta);
    			
   			bountysGUI.setItem((config.getInt("inventory.size") - 4), nextPage);
    			
    		ItemStack backPage = new ItemStack(Material.getMaterial(config.getInt("inventory.pageArrows.id")));
    		ItemMeta backPage_meta = backPage.getItemMeta();
    			
    		Integer back = (current_page.get(p.getName()) - 1);
    		backPage_meta.setDisplayName(config.getString("inventory.pageArrows.displayName").replace("%pageNumber", back.toString()).replace("&", "¤"));
   			backPage.setItemMeta(backPage_meta);
    			
   			bountysGUI.setItem((config.getInt("inventory.size") - 6), backPage);
        }
   		
   		p.openInventory(bountysGUI);
    }
    
}
