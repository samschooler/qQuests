package me.quaz3l.qQuests.API.TaskTypes;

import java.util.HashMap;

import org.bukkit.Location;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

public class GoTo {
	private HashMap<String, org.bukkit.scheduler.BukkitTask> tasks = new HashMap<String, org.bukkit.scheduler.BukkitTask>();
	private HashMap<String, Boolean> inRadius = new HashMap<String, Boolean>();
	public void launchAgent(String player, Location location1, Location location2) {
		//Location loc1 = location1.getBlock().getLocation();
		//Location loc2 = location2.getBlock().getLocation();
		org.bukkit.scheduler.BukkitTask task = qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// Cuboid script
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
		tasks.put(player, task);
	}
	public void launchAgent(final String player, final Location location, final double radius) {
		final Location loc = location.getBlock().getLocation();
		final double radiusSqrt = radius*radius;
		org.bukkit.scheduler.BukkitTask task = qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// Radius script
				if(qQuests.plugin.getServer().getPlayer(player) == null)
					return;
				
				Chat.logger("debug", radiusSqrt+"");
				
				if(qQuests.plugin.getServer().getPlayer(player).getLocation().distanceSquared(loc) <= radiusSqrt){
	                Chat.logger("debug", "Yay! " + player + " is in my radius!");
	                inRadius.put(player, true);
	            } else {
	            	inRadius.put(player, false);
	            }
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
		if(tasks.get(player) != null) {
			shutdownAgent(player);
		}
		tasks.put(player, task);
	}
	public void shutdownAgent(String player) {
		if(tasks.get(player) != null) {
			tasks.get(player).cancel();
			inRadius.remove(player);
		}
	}
	public boolean isInRadius(String player) {
		if(inRadius.containsKey(player))
			return inRadius.get(player);
		else return false;
	}
}
