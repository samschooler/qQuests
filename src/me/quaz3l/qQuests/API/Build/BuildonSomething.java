package me.quaz3l.qQuests.API.Build;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.Util.onSomething;

public class BuildonSomething {
	private String message = "";
	private Integer money = 0;
	private Integer health = 0;
	private Integer hunger = 0;
	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();
	private HashMap<Integer, String> permissionsAdd = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsTake = new HashMap<Integer, String>();
	
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
	public HashMap<Integer, String> permissionsAdd() {
		return this.permissionsAdd;
	}
	public HashMap<Integer, String> permissionsTake() {
		return this.permissionsTake;
	}
}
