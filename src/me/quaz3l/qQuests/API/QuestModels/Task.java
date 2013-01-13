package me.quaz3l.qQuests.API.QuestModels;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildTask;
import me.quaz3l.qQuests.Util.Storage;

public class Task {	
	private Integer no;
	private String type;
	private Integer id;
	private short durability;
	private String ids;
	private String display;
	private Integer amount;

	public Task(BuildTask build) 
	{
		no = build.no();
		type = build.type();
		id = build.id();
		durability = build.durability();
		ids = build.ids();
		display = build.display();
		amount = build.amount();
	}
	
	// Getting Variables
	public final Integer no() {
		return this.no;
	}
	public final String type() {
		return this.type;
	}
	public final Integer idInt() {
		return this.id;
	}
	public final short durability() {
		return this.durability;
	}
	public final String idString() {
		return this.ids;
	}
	public final String display() {
		return this.display;
	}
	public final Integer amount() {
		return this.amount;
	}
	
	// Utility functions
	public boolean isDone(String player) {
		return Storage.currentTaskProgress.get(player).get(no) >= this.amount();
	}
}