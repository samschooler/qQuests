package me.quaz3l.qQuests.API.Build;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Task;
import me.quaz3l.qQuests.API.Util.onSomething;
import me.quaz3l.qQuests.Util.Chat;

public class BuildQuest {
	private String name = "Quest";
	private Boolean multiTaskMode = false;
	private Integer repeated  = -1;
	private Boolean invisible = false;
	private String nextQuest  = "";
	private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	private BuildonSomething BuildonJoin = new BuildonSomething();
	private BuildonSomething BuildonDrop = new BuildonSomething();
	private BuildonSomething BuildonComplete = new BuildonSomething();
	private onSomething onJoin;
	private onSomething onDrop;
	private onSomething onComplete;
	
	public BuildQuest(String quest) {
		this.name = quest.toLowerCase();
	}
	public Quest create() {
		this.onJoin = new onSomething(BuildonJoin);
		Chat.logger("info", this.onJoin().message());
		this.onDrop = new onSomething(BuildonDrop);
		this.onComplete = new onSomething(BuildonComplete);
		return new Quest(this);
	}
	
	// Setting Variables
	public BuildQuest multiTaskMode(Boolean b) {
		this.multiTaskMode = b;
		return this;
	}
	public BuildQuest repeated(Integer i) {
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
	public BuildQuest tasks(Map<Integer, Task> m) {
		this.tasks = m;
		return this;
	}
	
	// Getting Variables
	public String name() {
		return this.name;
	}
	public Boolean multiTaskMode() {
		return this.multiTaskMode;
	}
	public Integer repeated() {
		return this.repeated;
	}
	public Boolean invisible() {
		return this.invisible;
	}
	public String nextQuest() {
		return this.nextQuest;
	}
	public Map<Integer, Task> tasks() {
		return this.tasks;
	}
	public onSomething onJoin() {
		return this.onJoin;
	}
	public onSomething onDrop() {
		return this.onDrop;
	}
	public onSomething onComplete() {
		return this.onComplete;
	}
	public BuildonSomething BuildonJoin() {
		return this.BuildonJoin;
	}
	public BuildonSomething BuildonDrop() {
		return this.BuildonDrop;
	}
	public BuildonSomething BuildonComplete() {
		return this.BuildonComplete;
	}
}