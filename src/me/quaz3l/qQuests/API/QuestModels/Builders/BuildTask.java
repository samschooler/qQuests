package me.quaz3l.qQuests.API.QuestModels.Builders;

import me.quaz3l.qQuests.API.QuestModels.Task;

public class BuildTask {
	private Integer no;
	private String type;
	private Integer id;
	private String ids;
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
	public BuildTask id(Integer i) {
		this.id = i;
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
	public Integer no() {
		return this.no;
	}
	public String type() {
		return this.type;
	}
	public Integer id() {
		return this.id;
	}
	public String ids() {
		return this.ids;
	}
	public String display() {
		return this.display;
	}
	public Integer amount() {
		return this.amount;
	}
}