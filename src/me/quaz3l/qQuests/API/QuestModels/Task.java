package me.quaz3l.qQuests.API.QuestModels;

import org.bukkit.Location;

import me.quaz3l.qQuests.API.QuestModels.Builders.BuildTask;
import me.quaz3l.qQuests.Util.Storage;

public class Task {	
	private Integer no;
	private String type;
	private Integer idInteger;
	private short durability;
	private String idString;
	private Location idLocation1;
	private Location idLocation2;
	private double radius;
	private String display;
	private Integer amount;

	public Task(BuildTask build) 
	{
		no = build.no();
		type = build.type();
		idInteger = build.idInteger();
		durability = build.durability();
		idString = build.idString();
		idLocation1 = build.idLocation1();
		idLocation2 = build.idLocation2();
		radius = build.radius();
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
		return this.idInteger;
	}
	public final short durability() {
		return this.durability;
	}
	public final String idString() {
		return this.idString;
	}
	public final Location idLocation1() {
		return this.idLocation1;
	}
	public final Location idLocation2() {
		return this.idLocation2;
	}
	public final double radius() {
		return this.radius;
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