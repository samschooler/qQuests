package me.quaz3l.qQuests.Plugins.Requirements;

import org.bukkit.ChatColor;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.PluginModels.qRequirement;
import me.quaz3l.qQuests.Util.Chat;

public class LevelMinRequirement extends qRequirement {

	@Override
	public int passedRequirement(String player, Object value) {
		try {
			if(Integer.parseInt(value.toString()) > qQuests.plugin.qAPI.getProfiles().getInt(player, "Level"))
				return 1;
		} catch(NumberFormatException e) {
			Chat.logger("severe", this.parseError(player, value, -1));
		}
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
	public String getName() {
		return "levelMin";
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -1: return "The requirement " + this.getName() + ", is NOT a number, it MUST be a number!";
		case  1: return "You need to be on a level above " + ChatColor.GOLD + value + ChatColor.RED + " to get this quest, you are on level " + ChatColor.GOLD + qQuests.plugin.qAPI.getProfiles().getInt(player, "Level") + ChatColor.RED + ".";
		case  2: return "You level is not high enough to get this quest!";
		default: return "Unknown Error! LULZ! :p";
		}
	}
}