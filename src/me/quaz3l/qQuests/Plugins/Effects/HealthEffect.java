package me.quaz3l.qQuests.Plugins.Effects;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.PluginModels.qEffect;
import me.quaz3l.qQuests.Util.Chat;

public class HealthEffect extends qEffect {

	@Override
	public void executeEffect(String player, Object value) {
		Chat.logger("debug", "HealthEffect.executeEffect(): start");
		Player p = qQuests.plugin.getServer().getPlayer(player);
		if(p == null) {
			return;
		}

		int given = Integer.parseInt(value.toString());
		int has = qQuests.plugin.getServer().getPlayer(player).getHealth();

		int healAmount = has + given;
		if(healAmount >= 20)
			p.setHealth(20);
		else
			p.setHealth(healAmount);
		Chat.logger("debug", "HealthEffect.executeEffect(): end");
	}

	@Override
	public int passedRequirement(String player, Object value) {
		try {
			Player p = qQuests.plugin.getServer().getPlayer(player);
			if(p == null) {
				return -1;
			}

			int needed = Integer.parseInt(value.toString());
			int has = qQuests.plugin.getServer().getPlayer(player).getHealth();

			if(needed*-1 > has) {
				return 1;
			}
		} catch(NumberFormatException e) {
			return -2;
		}
		return 0;
	}

	@Override
	public int validate(Object value) {
		if(value == null) {
			return -2;
		}
		try {
			Integer.parseInt(value.toString());
		} catch(NumberFormatException e) {
			return -2;
		}
		return 0;
	}

	@Override
	public String getName() {
		return "health";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -2: return "The value is NOT a number, it MUST be a number!";
		case -1: return "The player is not online.";
		case  1: return "You don't have enough health to complete this quest!";
		default: return "Unknown Error! LULZ! :p";
		}
	}
}
