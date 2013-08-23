package me.quaz3l.qQuests.Plugins.Effects;

import org.bukkit.ChatColor;
import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.PluginModels.qEffect;

public class MoneyEffect extends qEffect {

	@Override
	public void executeEffect(String player, Object value) {
		if(qQuests.plugin.economy != null) 
		{
			double money = Double.parseDouble(value.toString());
			
			if (qQuests.plugin.economy.bankBalance(player) != null) 
				qQuests.plugin.economy.createPlayerAccount(player);
			if(money < 0) 
				qQuests.plugin.economy.withdrawPlayer(player, money * -1);
			else
				qQuests.plugin.economy.depositPlayer(player, money);
		}
	}

	@Override
	public int passedRequirement(String player, Object value) {
		try {
			if(qQuests.plugin.economy != null) {
				double money = Double.parseDouble(value.toString());
				if(money < 0 && qQuests.plugin.economy.getBalance(player) < (money * -1)) 
					return 1;
			}
		} catch(NumberFormatException e) {
			return -1;
		}
		return 0;
	}

	@Override
	public int validate(Object value) {
		if(value == null) {
			return -1;
		}
		try {
			Double.parseDouble(value.toString());
		} catch(NumberFormatException e) {
			return -1;
		}
		return 0;
	}

	@Override
	public String getName() {
		return "money";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -1: return "The value is NOT a number, it MUST be a number!";
		case  1: return "You need " + ChatColor.GOLD + (Double.parseDouble(value.toString())*-1) + ChatColor.RED + " money to complete this quest! You have " + ChatColor.GOLD + qQuests.plugin.economy.getBalance(player) + ChatColor.RED + ".";
		default: return "Unknown Error! LULZ! :p";
		}
	}
}
