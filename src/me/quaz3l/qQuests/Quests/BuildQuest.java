package me.quaz3l.qQuests.Quests;

import java.util.HashMap;
import java.util.Map;

public class BuildQuest {
	public String name;
	public String messageStart;
	public String messageEnd;
	public Boolean tasksOrdered;
	public Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	
	public BuildQuest(String quest) {
		this.name = quest;
	}
	public Quest create() {
		return new Quest(this);
	}
}