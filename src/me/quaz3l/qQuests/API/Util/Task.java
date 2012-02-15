package me.quaz3l.qQuests.API.Util;

import me.quaz3l.qQuests.API.Build.BuildTask;

public class Task {	
	public Integer no;
	public String type;
	public Integer id;
	public String name;
	public Integer amount;

	public Task(BuildTask build) 
	{
		no = build.no();
		type = build.type();
		id = build.id();
		name = build.name();
		amount = build.amount();
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