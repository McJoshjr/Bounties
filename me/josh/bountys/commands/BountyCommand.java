package me.josh.bountys.commands;

import me.josh.bountys.Bountys;
import me.josh.bountys.FileUtils;
import me.josh.bountys.utils.BountysGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BountyCommand implements CommandExecutor {
	
	static Bountys plugin = Bountys.getInstance();
	static FileConfiguration config = Bountys.getInstance().getConfig();
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			System.out.println("[Bountys] Console support is currently in the works :D");
			return true;
	    } else if (sender instanceof Player) {
	    	Player p = (Player)sender;
		    	if (cmd.getName().equalsIgnoreCase("bounty")) {
			    	if (args.length < 1) {
			    		if (p.hasPermission("bounty.get")) {
			    			p.sendMessage(config.getString("getBounty").replace("%prefix", config.getString("Prefix")).replace("%bounty", FileUtils.getBounty(p.getName()).toString()).replace("&", "¤"));
			    		} else {
			    			p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
			    			return true;
			    		}
			    	}
			    }
			    if (args.length == 1) {
			    	if (p.hasPermission("bounty.help")) {
			    		if (args[0].equalsIgnoreCase("help")) {
		    				if (!p.isOp()) {
		    					p.sendMessage(ChatColor.GRAY + "- = - = - = - = - = - = - " + ChatColor.RED + "Bountys Help" + ChatColor.GRAY + " - = - = - = - = - = - = -");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty" + ChatColor.GRAY + " - " + ChatColor.GREEN + "View your current bounty");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty gui" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Opens up the bountys GUI");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Show the bountys help menu");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty add (player) (amount)" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Put a bounty on a player");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty get (player)" + ChatColor.GRAY + " - " + ChatColor.GREEN + "View a bounty of another player");
		    				    return true;
		    				} else {
		    					p.sendMessage(ChatColor.GRAY + "- = - = - = - = - = - " + ChatColor.RED + "Bountys Help (Admin)" + ChatColor.GRAY + " - = - = - = - = - = -");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty" + ChatColor.GRAY + " - " + ChatColor.GREEN + "View your current bounty");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty gui" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Opens up the bountys GUI");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Show the bountys help menu");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty add (player) (amount)" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Put a bounty on a player");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty get (player)" + ChatColor.GRAY + " - " + ChatColor.GREEN + "View a bounty of another player");
				    			p.sendMessage(ChatColor.GRAY + " - " + ChatColor.RED + "/bounty remove (player)" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Remove a bounty of a player");
		    				}
		    			}
			    	} else {
			    		p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    			return true;
		    		}
			    }
			    if (args.length == 1) {
			    	if (p.hasPermission("bounty.gui")) {
			    		if (args[0].equalsIgnoreCase("gui")) {
			    			BountysGUI.openGUI(p);
			    		}
			    	} else {
			    		p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    			return true;
			    	}
			    }
			    if (args.length == 2) {
			    	if (p.hasPermission("bounty.remove")) {
			    		if (args[0].equalsIgnoreCase("remove")) {
		    				Player target = Bukkit.getServer().getPlayer(args[1]);
		    				
		    				if (target == null) {
		    					p.sendMessage(config.getString("targetIsNotOnline").replace("%prefix", config.getString("Prefix")).replace("%target", args[1]).replace("&", "¤"));
		    					return true;
		    				}
		    				
		    				FileUtils.removeBountyWithSender(p, target.getName());
			    			p.sendMessage(config.getString("removeBountySender").replace("%prefix", config.getString("Prefix")).replace("%target", target.getName()).replace("&", "¤"));
		    			}
			    	} else {
			    		p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    			return true;
		    		}
			    }
			    if (args.length == 2) {
			    	if (p.hasPermission("bounty.get")) {
			    		if (args[0].equalsIgnoreCase("get")) {
		    				Player target = Bukkit.getServer().getPlayer(args[1]);
		    				
		    				if (target == null) {
		    					p.sendMessage(config.getString("targetIsNotOnline").replace("%prefix", config.getString("Prefix")).replace("%target", args[1]).replace("&", "¤"));
		    					return true;
		    				}
		    				
		    				p.sendMessage(config.getString("getSomeonesBounty").replace("%prefix", config.getString("Prefix")).replace("%target", target.getName()).replace("%bounty", FileUtils.getBounty(target.getName()).toString()).replace("&", "¤"));
		    			}
			    	} else {
			    		p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    		    return true;
		    		}
			    }
			    if (args.length == 3) {
			    	if (p.hasPermission("bounty.add")) {
			    		if (args[0].equalsIgnoreCase("add")) {
		    				Player target = Bukkit.getServer().getPlayer(args[1]);
		    				
		    				if (target == null) {
		    					p.sendMessage(config.getString("targetIsNotOnline").replace("%prefix", config.getString("Prefix")).replace("%target", args[1]).replace("&", "¤"));
		    					return true;
		    				}
		    				
		    				if (args[2].startsWith("-")) {
		    					p.sendMessage(config.getString("specifyInteger").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    					return true;
		    				}
		    				
		    				try {
		    					if (Integer.parseInt(args[2]) <= 0) {
		    						p.sendMessage(config.getString("notAInteger").replace("%prefix", config.getString("Prefix")).replace("%args", args[2]).replace("&", "¤"));
		    					}
		    					int i = Integer.parseInt(args[2]);
		    					
		    					FileUtils.addBounty(p, target, i);
		    					return true;
		    				} catch (Exception e) {
		    					p.sendMessage(config.getString("notAInteger").replace("%prefix", config.getString("Prefix")).replace("%args", args[2]).replace("&", "¤"));
		    					return true;
		    				}
		    			}
			    	} else {
			    		p.sendMessage(config.getString("noPermission").replace("%prefix", config.getString("Prefix")).replace("&", "¤"));
		    			return true;
		    		}
			    }
		    }
		return false;
	}

}