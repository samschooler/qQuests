package me.quaz3l.qQuests.API.QuestModels.Builders;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.onSomething;

public class BuildonSomething {
	private String message = "";
	private double money = 0;
	private int health = 0;
	private int hunger = 0;
	
	private int levelAdd = 0;
	private int levelSet = 0;
	
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
	public BuildonSomething money(double i) {
		this.money = i;
		return this;
	}
	public BuildonSomething health(int i) {
		this.health = i;
		return this;
	}
	public BuildonSomething hunger(int i) {
		this.hunger = i;
		return this;
	}
	
	public BuildonSomething levelAdd(int i) {
		this.levelAdd = i;
		return this;
	}
	public BuildonSomething levelSet(int i) {
		this.levelSet = i;
		return this;
	}
	
	public String message() {
		return this.message;
	}
	public double money() {
		return this.money;
	}
	public int health() {
		return this.health;
	}
	public int hunger() {
		return this.hunger;
	}
	
	public int levelAdd() {
		return this.levelAdd;
	}
	public int levelSet() {
		return this.levelSet;
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
