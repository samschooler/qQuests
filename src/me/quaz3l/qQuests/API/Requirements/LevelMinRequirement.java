package me.quaz3l.qQuests.API.Requirements;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

public class LevelMinRequirement extends qRequirement {

	@Override
	public boolean passedRequirement(String player, Object value) {
		try {
			if(Integer.parseInt(value.toString()) > qQuests.plugin.qAPI.getProfiles().getInt(player, "Level"))
				return false;
		} catch(NumberFormatException e) {
			Chat.logger("severe", "The requirement " + this.getName() + ", is NOT a number, it MUST be a number!");
		}
		return true;
	}

	@Override
	public boolean validate(Object value) {
		if(value == null) {
			return false;
		}
		try {
			Integer.parseInt(value.toString());
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
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
}