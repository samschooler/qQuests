package me.quaz3l.qQuests.API.QuestModels;

import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;

public class Quest {	
	// Setup
	private String name;
	private int repeated;
	private Boolean invisible;
	
	// Requirements
	private int levelMin;
	private int levelMax;
	
	// Tasks
	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
	
	// onWhatever
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;
	
	public Quest(BuildQuest build) 
	{
		name = build.name();
		repeated = build.repeated();
		invisible = build.invisible();
		
		levelMin = build.levelMin();
		levelMax = build.levelMax();
		
		tasks = build.tasks();
		
		onJoin = build.onJoin();
		onDrop = build.onDrop();
		onComplete = build.onComplete();
	}
	public final String name() {
		return this.name;
	}
	public final int repeated() {
		return this.repeated;
	}
	public final Boolean invisible() {
		return this.invisible;
	}
	
	public final int levelMin() {
		return this.levelMin;
	}
	public final int levelMax() {
		return this.levelMax;
	}
	
	public final HashMap<Integer, Task> tasks() {
		return this.tasks;
	}
	
	public final onSomething onJoin() {
		return this.onJoin;
	}
	public final onSomething onDrop() {
		return this.onDrop;
	}
	public final onSomething onComplete() {
		return this.onComplete;
	}
}