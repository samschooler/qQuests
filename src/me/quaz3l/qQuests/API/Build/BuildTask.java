package me.quaz3l.qQuests.API.Build;

import me.quaz3l.qQuests.API.Util.Task;

public class BuildTask {
	public Integer no;
	public String type;
	public Integer id;
	public String name;
	public Integer amount;
	
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
	public BuildTask name(String s) {
		this.name = s;
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
	public String name() {
		return this.name;
	}
	public Integer amount() {
		return this.amount;
	}
}