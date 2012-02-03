package me.quaz3l.qQuests.API.Build;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Reward;
import me.quaz3l.qQuests.API.Util.Task;

public class BuildQuest {
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
	
	public BuildQuest(String quest) {
		this.name = quest.toLowerCase();
	}
	public Quest create() {
		return new Quest(this);
	}
	public BuildQuest messageStart(String s) {
		this.messageStart = s;
		return this;
	}
	public BuildQuest messageEnd(String s) {
		this.messageEnd = s;
		return this;
	}
	public BuildQuest tasksOrdered(Boolean b) {
		this.tasksOrdered = b;
		return this;
	}
	public BuildQuest repeated(Integer i) {
		this.repeated = i;
		return this;
	}
	public BuildQuest nextQuest(String s) {
		this.nextQuest = s;
		return this;
	}
	public BuildQuest tasks(Map<Integer, Task> m) {
		this.tasks = m;
		return this;
	}
	public BuildQuest toJoin(Map<String, Integer> m) {
		this.toJoin = m;
		return this;
	}
	public BuildQuest toDrop(Map<String, Integer> m) {
		this.toDrop = m;
		return this;
	}
	public BuildQuest toComplete(Map<Integer, Reward> m) {
		this.toComplete = m;
		return this;
	}
	
}