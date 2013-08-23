package me.quaz3l.qQuests.API.QuestModels.Builders;

import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.onSomething;
import me.quaz3l.qQuests.Util.Chat;

public class BuildonSomething {
	private String message = "Quest Message!";
	private int delay = 0;
	private String nextQuest ="";

	// Better Effects
	private HashMap<String, Object> effects = new HashMap<String, Object>();

	public onSomething create() {
		return new onSomething(this);
	}

	public BuildonSomething message(String s) {
		if(s != null)
			this.message = s;
		return this;
	}
	public BuildonSomething nextQuest(String s) {
		this.nextQuest = s;
		return this;
	}
	public BuildonSomething delay(int i) {
		this.delay = i;
		return this;
	}

	public final BuildonSomething addEffect(String key, Object value) {
		this.effects.put(key, value);
		Chat.logger("debug", key+" "+value);
		return this;
	}

	public final String message() {
		return this.message;
	}
	public final String nextQuest() {
		return this.nextQuest;
	}
	public final int delay() {
		return this.delay;
	}
	
	// Effects
	public final HashMap<String, Object> effects() {
		return this.effects;
	}
}
