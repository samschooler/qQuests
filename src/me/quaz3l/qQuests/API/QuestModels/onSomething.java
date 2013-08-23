package me.quaz3l.qQuests.API.QuestModels;

import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildonSomething;

public class onSomething {
	private String message;
	private int delay;
	private String nextQuest;
	
	// Better Effects
	private HashMap<String, Object> effects = new HashMap<String, Object>();

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.delay = build.delay();
		this.nextQuest = build.nextQuest();
		
		this.effects = build.effects();
	}
	public String message(String player) {
		return this.message.replaceAll("`player", player);
	}
	public int delay() {
		return this.delay;
	}
	public final String nextQuest() {
		return this.nextQuest;
	}
	// Effects
	public final HashMap<String, Object> effects() {
		return this.effects;
	}
}
