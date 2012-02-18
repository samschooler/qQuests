package me.quaz3l.qQuests.API.Util;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.API.Build.BuildQuest;
import me.quaz3l.qQuests.API.Util.Events.onComplete;
import me.quaz3l.qQuests.API.Util.Events.onDrop;
import me.quaz3l.qQuests.API.Util.Events.onJoin;

public class Quest {	
	private String name = "Quest";
	private Boolean multiTaskMode = false;
	private Integer repeated  = -1;
	private Boolean invisible = false;
	private String nextQuest  = "";
	private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	private onJoin onJoin;
	private onDrop onDrop;
	private onComplete onComplete;
	
	public Quest(BuildQuest build) 
	{
		name = build.name();
		multiTaskMode = build.multiTaskMode();
		repeated = build.repeated();
		invisible = build.invisible();
		tasks = build.tasks();
		onJoin = build.onJoin();
		onDrop = build.onDrop();
		onComplete = build.onComplete();
	}
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
}