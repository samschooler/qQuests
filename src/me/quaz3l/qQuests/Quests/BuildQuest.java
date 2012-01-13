package me.quaz3l.qQuests.Quests;

public class BuildQuest {
	public String name;
	public String messageStart;
	public String messageEnd;
	public Boolean tasksOrdered;
	
	public BuildQuest(String quest) {
		this.name = quest;
	}
	public Quest create() {
		return new Quest(this);
	}
}
