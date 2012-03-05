package me.quaz3l.qQuests.API.Util;

import me.quaz3l.qQuests.API.Build.BuildonSomething;;

public class onSomething {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.money = build.money();
		this.health = build.health();
		this.hunger = build.hunger();
	}
	public String message() {
		return this.message;
	}
	public Integer money() {
		return this.money;
	}
	public Integer health() {
		return this.health;
	}
	public Integer hunger() {
		return this.hunger;
	}
}
