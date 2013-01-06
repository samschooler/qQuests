package me.quaz3l.qQuests.Util;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.entity.Player;

public class Storage {
	// Config Values
	public static String primaryCommand = "quest";
	public static String prefix = "qQuests";
	
	// Access            <Plugin,         Plugin,           Command>
	public static HashMap<String, HashMap<String, ArrayList<String>>> access = null;
	
	public static class give {
		public static boolean commands = true;
		public static boolean signs = true;
	}
	public static class info {
		public static boolean commands = true;
		public static boolean signs = true;
		
		public static boolean showMoney = true;
		public static String  moneyName = "";
		public static boolean showHealth = true;
		public static boolean showFood = true;
		public static boolean showItems = true;
		public static boolean showItemIds = true;
		public static boolean showCommands = true;
		public static boolean showLevelsAdded = true;
		public static boolean showSetLevel = true;
	}
	public static class tasks {
		public static boolean commands = true;
		public static boolean signs = true;
	}
	public static class drop {
		public static boolean commands = true;
		public static boolean signs = true;
	}
	public static class done {
		public static boolean commands = true;
		public static boolean signs = true;
	}
	
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
	
	public static boolean access(String trying, String has, String cmd) {
		if(has == null || trying == null || cmd == null)
			return false;
		has=has.toLowerCase();
		trying=trying.toLowerCase();
		cmd=cmd.toLowerCase();
		if(cmd.equalsIgnoreCase("start"))
			cmd="give";
		if(cmd.equalsIgnoreCase("list"))
			cmd="info";
		else if(cmd.equalsIgnoreCase("progress"))
			cmd="tasks";
		else if(cmd.equalsIgnoreCase("finish")||cmd.equalsIgnoreCase("end"))
			cmd="done";
		Chat.logger("debug", cmd);
		
		Chat.logger("debug", access.toString());
		Chat.logger("debug", access.containsKey(has)+"");
		Chat.logger("debug", access.get(has).get(trying).contains(cmd)+"");
		
		
		if(!access.containsKey(has))
			return false;
		if(!access.get(has).containsKey(trying))
			return false;
		if(!access.get(has).get(trying).contains(cmd))
			return false;
		
		return true;
	}
	
	public static void persist()
	{
		
	}
}
