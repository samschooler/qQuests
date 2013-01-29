package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.API.QuestModels.Quest;

public class DamageTest extends Task {

	public DamageTest(Quest quest) {
		super(quest);
	}
	
	@Override
	public String getName() {
		return "damage";
	}

}
