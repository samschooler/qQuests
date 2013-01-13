package me.quaz3l.qQuests.API.QuestModels.Builders;

import java.util.ArrayList;

import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.API.QuestModels.onSomething;

public class BuildQuest {
	// Setup
	private String name = "Quest";
	private int repeated = -1;
	private boolean invisible = false;
	private boolean forced = false;
	
	// Requirements
	private int levelMin = 0;
	private int levelMax = -1;
	
	// Tasks
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	// onWhatever
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;
	
	public BuildQuest(String quest) {
		this.name = quest;
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
	public BuildQuest invisible(boolean b) {
		this.invisible = b;
		return this;
	}
	public BuildQuest forced(boolean b) {
		this.forced = b;
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
	public BuildQuest tasks(int i, Task t) {
		this.tasks.add(i, t);
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
	public final String name() {
		return this.name;
	}
	public final int repeated() {
		return this.repeated;
	}
	public final boolean invisible() {
		return this.invisible;
	}
	public final boolean forced() {
		return this.forced;
	}
	
	// Requirements
	public final int levelMin() {
		return this.levelMin;
	}
	public final int levelMax() {
		return this.levelMax;
	}
	
	// Tasks
	public final ArrayList<Task> tasks() {
		return this.tasks;
	}
	
	// onWhatever
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