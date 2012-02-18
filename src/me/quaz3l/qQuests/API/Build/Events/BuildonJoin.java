package me.quaz3l.qQuests.API.Build.Events;

import me.quaz3l.qQuests.API.Util.Events.onJoin;

public class BuildonJoin {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	
	public onJoin create() {
		return new onJoin(this);
	}
	
	public BuildonJoin message(String s) {
		this.message = s;
		return this;
	}
	public BuildonJoin money(Integer i) {
		this.money = i;
		return this;
	}
	public BuildonJoin health(Integer i) {
		this.health = i;
		return this;
	}
	public BuildonJoin hunger(Integer i) {
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
