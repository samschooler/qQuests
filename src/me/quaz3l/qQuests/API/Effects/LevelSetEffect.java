package me.quaz3l.qQuests.API.Effects;

import me.quaz3l.qQuests.qQuests;

public class LevelSetEffect extends qEffect {

	@Override
	public void executeEffect(String player, Object value) {
		if(Integer.parseInt(value.toString()) > -1)
			qQuests.plugin.qAPI.getProfiles().set(player, "Level", Integer.parseInt(value.toString()));
	}

	@Override
	public int passedRequirement(String player, Object value) {
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
		return "levelSet";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -1: return "The value is NOT a number, it MUST be a number!";
		default: return "Unknown Error! LULZ! :p";
		}
	}
}
