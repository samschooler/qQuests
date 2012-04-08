package me.quaz3l.qQuests.Util;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.entity.Player;

public class Storage {
	// Config Values
	public static boolean autoUpdate = true;
	public static boolean tellMeYourUsingMyPlugin = true;
	public static String primaryCommand = "quest";
	
	// 
	public static boolean showMoney = true;
	public static String moneyName = "";
	public static boolean showHealth = true;
	public static boolean showFood = true;
	public static boolean showItems = true;
	public static boolean showItemIds = true;
	public static boolean showCommands = true;
	public static boolean showLevelsAdded = true;
	public static boolean showSetLevel = true;
		
	// Quests
	public static HashMap<String, Quest> quests = new HashMap<String, Quest>();
	public static HashMap<String, Quest> visibleQuests = new HashMap<String, Quest>();
	public static HashMap<Player, Quest> currentQuests = new HashMap<Player, Quest>();
	
	// Task Progress
	public static HashMap<Player, HashMap<Integer, Integer>> currentTaskProgress = new HashMap<Player, HashMap<Integer, Integer>>();
	public static HashMap<Player, Integer> tasksLeftInQuest = new HashMap<Player, Integer>();
	
	// Previous Quests
	public static HashMap<Player, Quest> previousQuest = new HashMap<Player, Quest>();
	
	// Way Quests Were Given
	public static HashMap<Player, String> wayCurrentQuestsWereGiven = new HashMap<Player, String>();
	public static HashMap<Player, String> wayPreviousQuestWereGiven = new HashMap<Player, String>();
	
	// Players That Can't Get Quests
	public static ArrayList<Player> cannotGetQuests = new ArrayList<Player>();
	
	public static void persist()
	{
		
	}
}
