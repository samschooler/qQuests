package me.quaz3l.qQuests.API.Build;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Reward;
import me.quaz3l.qQuests.API.Util.Task;

public class BuildQuest {
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
	
	public BuildQuest(String quest) {
		this.name = quest.toLowerCase();
	}
	public Quest create() {
		return new Quest(this);
	}
	
	// Setting Variables
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
	
	// Getting Variables
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