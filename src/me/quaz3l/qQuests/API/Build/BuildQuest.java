package me.quaz3l.qQuests.API.Build;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Build.Events.BuildonComplete;
import me.quaz3l.qQuests.API.Build.Events.BuildonDrop;
import me.quaz3l.qQuests.API.Build.Events.BuildonJoin;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Task;
import me.quaz3l.qQuests.API.Util.Events.onJoin;
import me.quaz3l.qQuests.API.Util.Events.onDrop;
import me.quaz3l.qQuests.API.Util.Events.onComplete;

public class BuildQuest {
	private String name = "Quest";
	private Boolean multiTaskMode = false;
	private Integer repeated  = -1;
	private Boolean invisible = false;
	private String nextQuest  = "";
	private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	private BuildonJoin BuildonJoin = new BuildonJoin();
	private BuildonDrop BuildonDrop = new BuildonDrop();
	private BuildonComplete BuildonComplete = new BuildonComplete();
	private onJoin onJoin;
	private onDrop onDrop;
	private onComplete onComplete;
	
	public BuildQuest(String quest) {
		this.name = quest.toLowerCase();
	}
	public Quest create() {
		this.onJoin = new onJoin(BuildonJoin);
		this.onDrop = new onDrop(BuildonDrop);
		this.onComplete = new onComplete(BuildonComplete);
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
	public onJoin onJoin() {
		return this.onJoin;
	}
	public onDrop onDrop() {
		return this.onDrop;
	}
	public onComplete onComplete() {
		return this.onComplete;
	}
	public BuildonJoin BuildonJoin() {
		return this.BuildonJoin;
	}
	public BuildonDrop BuildonDrop() {
		return this.BuildonDrop;
	}
	public BuildonComplete BuildonComplete() {
		return this.BuildonComplete;
	}
}