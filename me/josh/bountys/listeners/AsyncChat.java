package me.josh.bountys.listeners;

import java.util.HashMap;

import me.josh.bountys.Bountys;
import me.josh.bountys.FileUtils;
import me.josh.bountys.utils.BountyState;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChat implements Listener {
	
	public static HashMap<Player, Integer> bountyAmount = new HashMap<Player, Integer>();
	public static HashMap<Player, Player> bountyPlayer = new HashMap<Player, Player>();
	static FileConfiguration config = Bountys.getInstance().getConfig();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (BountyState.hasState(p)) {
			if (BountyState.getState(p) == BountyState.CHOOSING_AMOUNT) {
				int i = 0;
				
				String[] msg_split = e.getMessage().split(" ");
				
				if ((msg_split.length > 1) || (msg_split.length < 1)) {
					e.setCancelled(true);
					
					p.sendMessage(config.getString("specifyInteger").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
					return;
				}
				
				try {
					i = Integer.parseInt(e.getMessage());
					
					e.setCancelled(true);
					
					FileUtils.getBountiesConfig().set("NewBountys." + p.getName() + ".Amount", i);
					FileUtils.saveBountiesFile();
					
					BountyState.setState(p, BountyState.CHOOSING_PLAYER);
					
					p.sendMessage(config.getString("bountyTarget").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
				} catch (NumberFormatException e1) {
					e.setCancelled(true);
					
					p.sendMessage(config.getString("specifyInteger").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
					return;
				}
			} else if (BountyState.getState(p) == BountyState.CHOOSING_PLAYER) {
				String[] msg_split = e.getMessage().split(" ");
				
				if ((msg_split.length > 1) || (msg_split.length < 1)) {
					e.setCancelled(true);
					
					p.sendMessage(config.getString("specifyPlayer").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
					return;
				}
				
				e.setCancelled(true);
				
				@SuppressWarnings("deprecation")
				Player target = Bukkit.getServer().getPlayer(e.getMessage());
				
				if ((e.getMessage().contains("exit") || (e.getMessage().contains("EXIT")))) {
					e.setCancelled(true);
					
					BountyState.removeState(p);
					
					FileUtils.getBountiesConfig().set("NewBounty" + p.getName(), null);
					FileUtils.saveBountiesFile();
					
					p.sendMessage(config.getString("successfullyExited").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
					return;
				}
				
				if (target == null) {
					p.sendMessage(config.getString("targetIsNotOnline").replace("%prefix", config.getString("Prefix")).replace("%target", e.getMessage()).replace("&", "¤"));
					return;
				}
				
				FileUtils.getBountiesConfig().set("NewBountys." + p.getName() + ".Target", target.getName());
				FileUtils.saveBountiesFile();
				
				BountyState.setState(p, BountyState.CONFIRMING);
				
				p.sendMessage(config.getString("confirmMessage").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
			} else if (BountyState.getState(p) == BountyState.CONFIRMING) {
				if ((FileUtils.getBountiesConfig().get("NewBountys." + p.getName() + ".Target") != null) && (FileUtils.getBountiesConfig().getInt("NewBountys." + p.getName() + ".Amount") != 0)) {
				    String[] msg_split = e.getMessage().split(" ");
				    
				    if ((msg_split.length > 1) || (msg_split.length < 1)) {
				    	e.setCancelled(true);
				    	
				    	p.sendMessage(config.getString("confirmBounty").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
						return;
					}
				    	
				    if ((e.getMessage().contains("CONFIRM") || (e.getMessage().contains("confirm")))) {
				    	e.setCancelled(true);
				    	
				    	@SuppressWarnings("deprecation")
						Player target = Bukkit.getServer().getPlayer(FileUtils.getBountiesConfig().getString("NewBountys." + p.getName() + ".Target"));
				    	
						FileUtils.addBounty(p, target, FileUtils.getBountiesConfig().getInt("NewBountys." + p.getName() + ".Amount"));
						BountyState.removeState(p);
						
						FileUtils.getBountiesConfig().set("NewBountys." + p.getName(), null);
						FileUtils.saveBountiesFile();
					}
				}
			}
			if (e.getMessage().equals("EXIT") || (e.getMessage().equals("exit"))) {
				e.setCancelled(true);
				
				BountyState.removeState(p);
				
				FileUtils.getBountiesConfig().set("NewBountys." + p.getName(), null);
				FileUtils.saveBountiesFile();
				
				p.sendMessage(config.getString("successfullyExited").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
			}
		} 
	}

}
