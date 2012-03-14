package me.quaz3l.qQuests.API.Build;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.Util.onSomething;

public class BuildonSomething {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();
	
	public onSomething create() {
		return new onSomething(this);
	}
	
	public BuildonSomething message(String s) {
		this.message = s;
		return this;
	}
	public BuildonSomething money(Integer i) {
		this.money = i;
		return this;
	}
	public BuildonSomething health(Integer i) {
		this.health = i;
		return this;
	}
	public BuildonSomething hunger(Integer i) {
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
	public HashMap<Integer, ArrayList<Integer>> items() {
		return this.items;
	}
}