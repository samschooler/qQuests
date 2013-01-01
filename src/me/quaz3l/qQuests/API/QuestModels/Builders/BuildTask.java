package me.quaz3l.qQuests.API.QuestModels.Builders;

import me.quaz3l.qQuests.API.QuestModels.Task;

public class BuildTask {
	private int no;
	private String type;
	private int id;
		private short durability;
	private String ids;
	private String display;
	private int amount;
	
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
	public BuildTask id(Integer i) {
		this.id = i;
		return this;
	}
	public BuildTask durability(short i) {
		this.durability = i;
		return this;
	}
	public BuildTask id(String s) {
		this.ids = s;
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
	public final int id() {
		return this.id;
	}
	public final short durability() {
		return this.durability;
	}
	public final String ids() {
		return this.ids;
	}
	public final String display() {
		return this.display;
	}
	public final int amount() {
		return this.amount;
	}
}