package me.quaz3l.qQuests.API.TaskTypes;

import org.bukkit.Location;

import me.quaz3l.qQuests.qQuests;

public class GoTo {
	public void launchAgent(String player, Location location1, Location location2) {
		Location loc1 = location1.getBlock().getLocation();
		Location loc2 = location2.getBlock().getLocation();
		qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// TODO Cuboid script
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
	}
	public void launchAgent(String player, Location location, double radius) {
		Location loc = location.getBlock().getLocation();
		qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {
			@Override
			public void run() {
				// TODO Radius script
			}
		}, 5 * 20, 5 * 20); // Update every 5 seconds
	}
}
