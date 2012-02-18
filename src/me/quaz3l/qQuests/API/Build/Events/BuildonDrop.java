package me.quaz3l.qQuests.API.Build.Events;

import me.quaz3l.qQuests.API.Util.Events.onDrop;

public class BuildonDrop {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	
	public onDrop create() {
		return new onDrop(this);
	}
	
	public BuildonDrop message(String s) {
		this.message = s;
		return this;
	}
	public BuildonDrop money(Integer i) {
		this.money = i;
		return this;
	}
	public BuildonDrop health(Integer i) {
		this.health = i;
		return this;
	}
	public BuildonDrop hunger(Integer i) {
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
