package me.quaz3l.qQuests.API.TaskTypes;

import org.bukkit.Location;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

public class GoTo {
	public static void launchAgent(String player, Location location1, Location location2) {
		//Location loc1 = location1.getBlock().getLocation();
		//Location loc2 = location2.getBlock().getLocation();
		qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// Cuboid script
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
	}
	public static void launchAgent(final String player, final Location location, final double radius) {
		final Location loc = location.getBlock().getLocation();
		final double radiusSqrt = radius*radius;
		qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// Radius script
				if(qQuests.plugin.getServer().getPlayer(player) == null)
					return;
				
				if(qQuests.plugin.getServer().getPlayer(player).getLocation().distanceSquared(loc) <= radiusSqrt){
	                Chat.logger("debug", "Yay! " + player + " is in my radius!");
	            }
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
	}
}
