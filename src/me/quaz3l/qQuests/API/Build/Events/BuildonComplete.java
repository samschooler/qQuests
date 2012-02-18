package me.quaz3l.qQuests.API.Build.Events;

import me.quaz3l.qQuests.API.Util.Events.onComplete;

public class BuildonComplete {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	
	public onComplete create() {
		return new onComplete(this);
	}
	
	public BuildonComplete message(String s) {
		this.message = s;
		return this;
	}
	public BuildonComplete money(Integer i) {
		this.money = i;
		return this;
	}
	public BuildonComplete health(Integer i) {
		this.health = i;
		return this;
	}
	public BuildonComplete hunger(Integer i) {
		this.hunger = i;
		return this;
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
