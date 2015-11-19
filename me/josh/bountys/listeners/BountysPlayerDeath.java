package me.josh.bountys.listeners;

import me.josh.bountys.Bountys;
import me.josh.bountys.FileUtils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BountysPlayerDeath implements Listener {
	
	static FileConfiguration config = Bountys.getInstance().getConfig();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player k = p.getKiller();
		
		if (k instanceof Player && p instanceof Player) {
			if (FileUtils.hasBounty(p.getName())) {
				p.sendMessage(config.getString("bountyVictim").replace("%prefix", config.getString("Prefix")).replace("%target", k.getName()).replace("&", "¤"));
				k.sendMessage(config.getString("bountyMurderer").replace("%prefix", config.getString("Prefix")).replace("%target", p.getName()).replace("&", "¤"));
					
				Bountys.economy.depositPlayer(k, FileUtils.getBounty(p.getName()));
					
				FileUtils.removeBounty(p.getName());	
			}
		}
	}

}
