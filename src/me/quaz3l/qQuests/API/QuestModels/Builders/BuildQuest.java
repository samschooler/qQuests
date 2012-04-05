package me.quaz3l.qQuests.API.QuestModels.Builders;

import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.API.QuestModels.onSomething;

public class BuildQuest {
	// Setup
	private String name = "Quest";
	private int repeated = -1;
	private Boolean invisible = false;
	private String nextQuest = "";
	private int delay = 0;
	
	// Requirements
	private int levelMin = 0;
	private int levelMax = -1;
	
	// Tasks
	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
	
	// onWhatever
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;
	
	public BuildQuest(String quest) {
		this.name = quest.toLowerCase();
	}
	public Quest create() {
		return new Quest(this);
	}
	
	// Setting Variables
	
	// Setup
	public BuildQuest repeated(int i) {
		this.repeated = i;
		return this;
	}
	public BuildQuest invisible(Boolean b) {
		this.invisible = b;
		return this;
	}
	public BuildQuest nextQuest(String s) {
		this.nextQuest = s;
		return this;
	}
	public BuildQuest delay(int i) {
		this.delay = i;
		return this;
	}
	
	// Requirements
	public BuildQuest levelMin(int i) {
		this.levelMin = i;
		return this;
	}
	public BuildQuest levelMax(int i) {
		this.levelMax = i;
		return this;
	}
	
	// Tasks
	public BuildQuest tasks(HashMap<Integer, Task> m) {
		this.tasks = m;
		return this;
	}
	
	// onWhatever
	public BuildQuest onJoin(BuildonSomething b) {
		this.onJoin = b.create();
		return this;
	}
	public BuildQuest onDrop(BuildonSomething b) {
		this.onDrop = b.create();
		return this;
	}
	public BuildQuest onComplete(BuildonSomething b) {
		this.onComplete = b.create();
		return this;
	}
	
	// Getting Variables
	
	// Setup
	public String name() {
		return this.name;
	}
	public int repeated() {
		return this.repeated;
	}
	public Boolean invisible() {
		return this.invisible;
	}
	public String nextQuest() {
		return this.nextQuest;
	}
	public int delay() {
		return this.delay;
	}
	
	// Requirements
	public final int levelMin() {
		return this.levelMin;
	}
	public final int levelMax() {
		return this.levelMax;
	}
	
	// Tasks
	public HashMap<Integer, Task> tasks() {
		return this.tasks;
	}
	
	// onWhatever
	public onSomething onJoin() {
		return this.onJoin;
	}
	public onSomething onDrop() {
		return this.onDrop;
	}
	public onSomething onComplete() {
		return this.onComplete;
	}
}