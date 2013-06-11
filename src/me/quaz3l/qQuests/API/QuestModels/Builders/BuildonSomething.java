package me.quaz3l.qQuests.API.QuestModels.Builders;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.onSomething;
import me.quaz3l.qQuests.Util.Chat;

public class BuildonSomething {
	private String message = "Quest Message!";
	private int delay = 0;
	private String nextQuest ="";

	private double money = 0;
	private int health = 0;
	private int hunger = 0;

	private int levelAdd = 0;
	private int levelSet = -1;

	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();
	private HashMap<Integer, String> commands = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsAdd = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsTake = new HashMap<Integer, String>();

	// Better Effects
	private HashMap<String, Object> effects = new HashMap<String, Object>();

	public onSomething create() {
		return new onSomething(this);
	}

	public BuildonSomething message(String s) {
		if(s != null)
			this.message = s;
		return this;
	}
	public BuildonSomething nextQuest(String s) {
		this.nextQuest = s;
		return this;
	}
	public BuildonSomething delay(int i) {
		this.delay = i;
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

	public BuildonSomething items(int i, ArrayList<Integer> itms) {
		this.items.put(i, itms);
		return this;
	}
	public BuildonSomething commands(int i, String s) {
		this.commands.put(i, s);
		return this;
	}
	public final BuildonSomething addEffect(String key, Object value) {
		this.effects.put(key, value);
		Chat.logger("debug", key+" "+value);
		return this;
	}

	public final String message() {
		return this.message;
	}
	public final String nextQuest() {
		return this.nextQuest;
	}
	public final int delay() {
		return this.delay;
	}

	public final double money() {
		return this.money;
	}
	public final int health() {
		return this.health;
	}
	public final int hunger() {
		return this.hunger;
	}

	public final int levelAdd() {
		return this.levelAdd;
	}
	public final int levelSet() {
		return this.levelSet;
	}

	public final HashMap<Integer, ArrayList<Integer>> items() {
		return this.items;
	}
	public final HashMap<Integer, String> commands() {
		return this.commands;
	}
	public final HashMap<Integer, String> permissionsAdd() {
		return this.permissionsAdd;
	}
	public final HashMap<Integer, String> permissionsTake() {
		return this.permissionsTake;
	}

	// Effects
	public final HashMap<String, Object> effects() {
		return this.effects;
	}
}
