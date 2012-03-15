package me.quaz3l.qQuests.Util;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Storage {
	// Task Progress
	public static HashMap<Player, HashMap<Integer, Integer>> currentTaskProgress = new HashMap<Player, HashMap<Integer, Integer>>();
	public static HashMap<Player, String> wayCurrentQuestsWereGiven = new HashMap<Player, String>();
}
