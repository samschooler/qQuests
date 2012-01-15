package me.quaz3l.qQuests.Quests;

import java.util.HashMap;
import java.util.Map;

public class Quest {	
	public String name;
	public String messageStart;
	public String messageEnd;
	public Boolean tasksOrdered;
	public Map<Integer, Task> tasks = new HashMap<Integer, Task>();

	public Quest(BuildQuest build) 
	{
		name = build.name;
		messageStart = build.messageStart;
		messageEnd = build.messageEnd;
		tasksOrdered = build.tasksOrdered;
		tasks = build.tasks;
	}
}