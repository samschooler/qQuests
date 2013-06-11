package me.quaz3l.qQuests.API.Effects;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

public class DelayEffect extends qEffect {

	@Override
	public void executeEffect(final String player, Object value) {
		if(value == null) {
			return;
		}
		try {
			int delay = Integer.parseInt(value.toString());
			
			Storage.delayLeft.put(player, delay);
			qQuests.plugin.qAPI.setDisabled(player, "delay", true);
			
			final BukkitTask timer = qQuests.plugin.qAPI.runPersistentTaskTimer( new Runnable() {
				public void run() {
					Chat.logger("debug", "subtract 3 seconds");
					if(Storage.delayLeft.get(player) != null)
						Storage.delayLeft.put(player, Storage.delayLeft.get(player)-3);
				}
			}, 0, 60);
			qQuests.plugin.qAPI.runPersistentTaskLater(new Runnable() {
				public void run() {
					qQuests.plugin.qAPI.setDisabled(player, "delay", false);
					timer.cancel();
					Storage.delayLeft.remove(player);
				}
			}, (delay * 20));

		} catch(NumberFormatException e) {
			return;
		}
	}

	@Override
	public int passedRequirement(String player, Object value) {
		Chat.logger("debug", "delay.passedRequirement()");
		if(Storage.delayLeft.get(player) != null) {
			Chat.logger("debug", "delay.passedRequirement(): 0");
			if(Storage.delayLeft.get(player) > 0) {
				Chat.logger("debug", "delay.passedRequirement(): 1");
				return 1;
			}
		}
		Chat.logger("debug", "delay.passedRequirement(): 2");
		return 0;
	}

	@Override
	public int validate(Object value) {
		if(value == null) {
			return -1;
		}
		try {
			Integer.parseInt(value.toString());
		} catch(NumberFormatException e) {
			return -1;
		}
		return 0;
	}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -1: return "The effect " + this.getName() + ", is NOT a number, it MUST be a number!";
		case  1: return "You need to wait " + ChatColor.GOLD + (Storage.delayLeft.get(player)) + " seconds" + ChatColor.RED + " before you can get a quest.";
		default: return "Unknown Error! LULZ! :p";
		}
	}

	@Override
	public String getName() {
		return "delay";
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

}
