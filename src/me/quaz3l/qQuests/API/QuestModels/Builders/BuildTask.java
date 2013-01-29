package me.quaz3l.qQuests.API.QuestModels.Builders;

import org.bukkit.Location;

import me.quaz3l.qQuests.API.QuestModels.Task;

public class BuildTask {
	private Integer no;
	private String type;
	private int idInteger;
	private short durability;
	private String idString;
	private Location idLocation1;
	private Location idLocation2;
	private double radius;
	private String display;
	private Integer amount;
	
	public BuildTask(Integer task) {
		this.no = task;
	}
	public Task create() {
		return new Task(this);
	}
	
	// Setting Variables
	public BuildTask type(String s) {
		this.type = s;
		return this;
	}
	public BuildTask id(int i) {
		this.idInteger = i;
		return this;
	}
	public BuildTask durability(short i) {
		this.durability = i;
		return this;
	}
	public BuildTask id(String s) {
		this.idString = s;
		return this;
	}
	public BuildTask id(Location l1, Location l2, double radius) {
		this.idLocation1 = l1;
		this.idLocation2 = l2;
		this.radius = radius;
		return this;
	}
	public BuildTask display(String s) {
		this.display = s;
		return this;
	}
	public BuildTask amount(Integer i) {
		this.amount = i;
		return this;
	}
	
	// Getting Variables
	public final int no() {
		return this.no;
	}
	public final  String type() {
		return this.type;
	}
	public final int idInteger() {
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
	public final int amount() {
		return this.amount;
	}
}