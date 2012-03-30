package me.quaz3l.qQuests.API.QuestModels;

import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;

public class Quest {	
	private String name;
	private Integer repeated;
	private Boolean invisible;
	private String nextQuest;
	private Integer delay;
	private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;
	
	public Quest(BuildQuest build) 
	{
		name = build.name();
		repeated = build.repeated();
		invisible = build.invisible();
		nextQuest = build.nextQuest();
		delay = build.delay();
		tasks = build.tasks();
		onJoin = build.onJoin();
		onDrop = build.onDrop();
		onComplete = build.onComplete();
	}
	public final String name() {
		return this.name;
	}
	public final Integer repeated() {
		return this.repeated;
	}
	public final Boolean invisible() {
		return this.invisible;
	}
	public final String nextQuest() {
		return this.nextQuest;
	}
	public final Integer delay() {
		return this.delay;
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