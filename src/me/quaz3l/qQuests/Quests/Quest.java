package me.quaz3l.qQuests.Quests;

public class Quest {	
	public String name;
	public String messageStart;
	public String messageEnd;
	public Boolean tasksOrdered;

	public Quest(BuildQuest build) 
	{
		name = build.name;
	}
}