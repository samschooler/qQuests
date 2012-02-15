package me.quaz3l.qQuests.API.Util;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Build.BuildQuest;

public class Quest {	
	private String name = "Quest";
	private String messageStart = "You Have Recived A Quest!";
	private String messageEnd = "You Have Finished A Quest!";
	private Boolean tasksOrdered = false;
	private Integer repeated  = -1;
	private String nextQuest  = "";
	private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	private Map<String, Integer> toJoin = new HashMap<String, Integer>();
	private Map<String, Integer> toDrop = new HashMap<String, Integer>();
	private Map<Integer, Reward> toComplete = new HashMap<Integer, Reward>();
	
	public Quest(BuildQuest build) 
	{
		name = build.name();
		messageStart = build.messageStart();
		messageEnd = build.messageEnd();
		tasksOrdered = build.tasksOrdered();
		tasks = build.tasks();
		toJoin = build.toJoin();
		toDrop = build.toDrop();
		toComplete = build.toComplete();
	}
	public String name() {
		return this.name;
	}
	public String messageStart() {
		return this.messageStart;
	}
	public String messageEnd() {
		return this.messageEnd;
	}
	public Boolean tasksOrdered() {
		return this.tasksOrdered;
	}
	public Integer repeated() {
		return this.repeated;
	}
	public String nextQuest() {
		return this.nextQuest;
	}
	public Map<Integer, Task> tasks() {
		return this.tasks;
	}
	public Map<String, Integer> toJoin() {
		return this.toJoin;
	}
	public Map<String, Integer> toDrop() {
		return this.toDrop;
	}
	public Map<Integer, Reward> toComplete() {
		return this.toComplete;
	}
}