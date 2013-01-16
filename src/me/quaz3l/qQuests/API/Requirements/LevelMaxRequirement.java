package me.quaz3l.qQuests.API.Requirements;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;

public class LevelMaxRequirement extends qRequirement {

	@Override
	public boolean passedRequirement(String player, String quest) {
		Quest q = qQuests.plugin.qAPI.getQuest(quest);
		try {
			if(Integer.parseInt(q.requirements().get(this.getName()).toString()) < qQuests.plugin.qAPI.getProfiles().getInt(player, "Level"))
				return false;
		} catch(NumberFormatException e) {
			Chat.logger("severe", "The requirement " + this.getName() + " of quest " + q.name() + ", is NOT a number, it MUST be a number!");
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
		return "levelMax";
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}
}