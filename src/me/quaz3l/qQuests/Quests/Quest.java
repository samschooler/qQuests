package me.quaz3l.qQuests.Quests;

import java.util.HashMap;
import java.util.Map;

public class Quest {	
	public String name;
	public String messageStart;
	public String messageEnd;
	public Boolean tasksOrdered;
	public Integer repeated;
	public String nextQuest;
	public Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	public Map<String, Integer> toJoin = new HashMap<String, Integer>();
	public Map<String, Integer> toDrop = new HashMap<String, Integer>();
	public Map<Integer, Reward> toComplete = new HashMap<Integer, Reward>();
	
	public Quest(BuildQuest build) 
	{
		name = build.name;
		messageStart = build.messageStart;
		messageEnd = build.messageEnd;
		tasksOrdered = build.tasksOrdered;
		tasks = build.tasks;
		toJoin = build.toJoin;
		toDrop = build.toDrop;
		toComplete = build.toComplete;
	}
}