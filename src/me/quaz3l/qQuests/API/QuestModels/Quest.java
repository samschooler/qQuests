package me.quaz3l.qQuests.API.QuestModels;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;

public class Quest {	
	// Setup
	private String name;
	private int repeated;
	private boolean invisible;
	private boolean forced;
	
	// Better Requirements
	private HashMap<String, Object> requirements = new HashMap<String, Object>();

	// Tasks
	private ArrayList<Task> tasks = new ArrayList<Task>();

	// onWhatever
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;

	public Quest(BuildQuest build) 
	{
		name = build.name();
		repeated = build.repeated();
		invisible = build.invisible();
		forced = build.forced();

		requirements = build.requirements();
		
		tasks = build.tasks();

		onJoin = build.onJoin();
		onDrop = build.onDrop();
		onComplete = build.onComplete();
	}
	public final String name() {
		return this.name;
	}
	public final int repeated() {
		// This is a fix for a miscommunication on my part. repeated: 0 now means you can do a quest once, 1 = twice
		return this.repeated+1;
	}
	public final boolean invisible() {
		return this.invisible;
	}
	public final boolean forced() {
		return this.forced;
	}

	public final HashMap<String, Object> requirements() {
		return this.requirements;
	}

	public final ArrayList<Task> tasks() {
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

	// Utility functions
	public boolean isDone(String player) {
		for(Task t : this.tasks()) {
			if(!t.isDone(player))
				return false;
		}
		return false;
	}
}