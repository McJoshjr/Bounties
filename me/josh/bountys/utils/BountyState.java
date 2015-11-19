package me.josh.bountys.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

public enum BountyState {
	
	CHOOSING_AMOUNT, CHOOSING_PLAYER, CONFIRMING;
	
	public static HashMap<String, BountyState> bountyState = new HashMap<String, BountyState>();
	
	public static BountyState getState(Player p) {
		if (bountyState.containsKey(p.getName())) {
			return bountyState.get(p.getName());
		}
		return null;
	}
	
	public static void setState(Player p, BountyState bs) {
		if (bountyState.containsKey(p.getName())) {
			bountyState.remove(p.getName());
			bountyState.put(p.getName(), bs);
		} else {
			bountyState.put(p.getName(), bs);
		}
	}
	
	public static boolean hasState(Player p) {
		if (bountyState.containsKey(p.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void removeState(Player p) {
		if (bountyState.containsKey(p.getName())) {
			bountyState.remove(p.getName());
		}
	}

}
