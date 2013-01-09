package me.quaz3l.qQuests.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.entity.Player;

public class Storage {
	// Quests
	public static HashMap<String, Quest> quests = new HashMap<String, Quest>();
	public static HashMap<String, Quest> visibleQuests = new HashMap<String, Quest>();
	public static HashMap<Player, Quest> currentQuests = new HashMap<Player, Quest>(); // Persist

	// Task Progress
	public static HashMap<Player, HashMap<Integer, Integer>> currentTaskProgress = new HashMap<Player, HashMap<Integer, Integer>>(); //Persist
	public static HashMap<Player, Integer> tasksLeftInQuest = new HashMap<Player, Integer>(); //Persist

	// Previous Quests
	public static HashMap<Player, Quest> previousQuest = new HashMap<Player, Quest>(); //Persist

	// Way Quests Were Given
	public static HashMap<Player, String> wayCurrentQuestsWereGiven = new HashMap<Player, String>(); //Persist
	public static HashMap<Player, String> wayPreviousQuestWereGiven = new HashMap<Player, String>(); //Persist

	// Keep track of delays for persistence
	public static HashMap<Player, Integer> delayLeft = new HashMap<Player, Integer>(); //Persist

	// Players That Can't Get Quests
	public static ArrayList<Player> cannotGetQuests = new ArrayList<Player>();


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

		if(!access.containsKey(has))
			return false;
		if(!access.get(has).containsKey(trying))
			return false;
		if(!access.get(has).get(trying).contains(cmd))
			return false;

		return true;
	}
	public static void rePersist() {
		qQuests.plugin.getServer().getScheduler().runTaskTimerAsynchronously(qQuests.plugin, new Runnable() {

			public void run() {
				Storage.persist();
			}
		}, 0, 1 *60 * 20); // Persist data every minute
	}
	public static void persist()
	{
		File dir = new File(qQuests.plugin.getDataFolder().getPath() + "/savedState/");
		try {
			// Create /storage/ if it doesn't exist
			if(!dir.exists()) dir.mkdir();

			// currentQuests
			HashMap<String, String> cQuests = new HashMap<String, String>();
			for(Entry<Player, Quest> entry : currentQuests.entrySet())
				cQuests.put(entry.getKey().getName(), entry.getValue().name());
			SLAPI.save(cQuests, qQuests.plugin.getDataFolder().getPath() + "/savedState/currentQuests.dat");

			// currentTaskProgress
			HashMap<String, HashMap<Integer, Integer>> cTaskProgress = new HashMap<String, HashMap<Integer, Integer>>();
			for(Entry<Player, HashMap<Integer, Integer>> entry : currentTaskProgress.entrySet())
				cTaskProgress.put(entry.getKey().getName(), entry.getValue());
			SLAPI.save(cTaskProgress, qQuests.plugin.getDataFolder().getPath() + "/savedState/currentTaskProgress.dat");

			// tasksLeftInQuest
			HashMap<String, Integer> tLeftInQuest = new HashMap<String, Integer>();
			for(Entry<Player, Integer> entry : tasksLeftInQuest.entrySet())
				tLeftInQuest.put(entry.getKey().getName(), entry.getValue());
			SLAPI.save(tLeftInQuest, qQuests.plugin.getDataFolder().getPath() + "/savedState/tasksLeftInQuest.dat");

			// previousQuests
			HashMap<String, String> pQuest = new HashMap<String, String>();
			for(Entry<Player, Quest> entry : previousQuest.entrySet())
				pQuest.put(entry.getKey().getName(), entry.getValue().name());
			SLAPI.save(pQuest, qQuests.plugin.getDataFolder().getPath() + "/savedState/previousQuest.dat");
			
			Chat.logger("debug", pQuest.toString());

			// wayCurrentQuestsWereGiven
			HashMap<String, String> wcQuestsWereGiven = new HashMap<String, String>();
			for(Entry<Player, String> entry : wayCurrentQuestsWereGiven.entrySet())
				wcQuestsWereGiven.put(entry.getKey().getName(), entry.getValue());
			SLAPI.save(wcQuestsWereGiven, qQuests.plugin.getDataFolder().getPath() + "/savedState/wayCurrentQuestsWereGiven.dat");

			// wayPreviousQuestWereGiven
			HashMap<String, String> wpQuestWereGiven = new HashMap<String, String>();
			for(Entry<Player, String> entry : wayPreviousQuestWereGiven.entrySet())
				wpQuestWereGiven.put(entry.getKey().getName(), entry.getValue());
			SLAPI.save(wpQuestWereGiven, qQuests.plugin.getDataFolder().getPath() + "/savedState/wayPreviousQuestWereGiven.dat");

			// delayLeft
			HashMap<String, Integer> dLeft = new HashMap<String, Integer>();
			for(Entry<Player, Integer> entry : delayLeft.entrySet())
				dLeft.put(entry.getKey().getName(), entry.getValue());
			SLAPI.save(dLeft, qQuests.plugin.getDataFolder().getPath() + "/savedState/delayLeft.dat");

			Chat.logger("debug", "Quests persisted.");
		} catch (Exception e) {
			Chat.logger("severe", "Quests are not able to save to file! The quests will not persist!");
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public static boolean loadPersisted()
	{
		File dir = new File(qQuests.plugin.getDataFolder().getPath() + "/savedState/");
		try {
			if(!dir.exists()) return false;

			// currentQuests
			for(Entry<String, String> entry : 
				((HashMap<String, String>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/currentQuests.dat")).entrySet())
				currentQuests.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), qQuests.plugin.qAPI.getQuest(entry.getValue()));

			// currentTaskProgress
			for(Entry<String, HashMap<Integer, Integer>> entry : 
				((HashMap<String, HashMap<Integer, Integer>>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/currentTaskProgress.dat")).entrySet())
				currentTaskProgress.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), entry.getValue());

			// tasksLeftInQuest
			for(Entry<String, Integer> entry : 
				((HashMap<String, Integer>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/tasksLeftInQuest.dat")).entrySet())
				tasksLeftInQuest.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), entry.getValue());

			// previousQuests <
			for(Entry<String, String> entry : 
				((HashMap<String, String>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/previousQuest.dat")).entrySet())
				previousQuest.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), qQuests.plugin.qAPI.getQuest(entry.getValue()));

			// wayCurrentQuestsWereGiven
			for(Entry<String, String> entry : 
				((HashMap<String, String>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/wayCurrentQuestsWereGiven.dat")).entrySet())
				wayCurrentQuestsWereGiven.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), entry.getValue());

			// wayPreviousQuestWereGiven <
			for(Entry<String, String> entry : 
				((HashMap<String, String>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/wayPreviousQuestWereGiven.dat")).entrySet())
				wayPreviousQuestWereGiven.put((Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey()), entry.getValue());

			// delayLeft <
			for(Entry<String, Integer> entry : 
				((HashMap<String, Integer>) SLAPI.load(qQuests.plugin.getDataFolder().getPath() + 
						"/savedState/delayLeft.dat")).entrySet()) {
				final Player player = (Player)qQuests.plugin.getServer().getOfflinePlayer(entry.getKey());
				delayLeft.put(player, entry.getValue());
				
				Storage.cannotGetQuests.add(player);
				qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {

					public void run() {
						Storage.cannotGetQuests.remove(player);
						if(Storage.previousQuest.get(player).onComplete().nextQuest() != null)
						{
							int result = qQuests.plugin.qAPI.giveQuest(player, Storage.previousQuest.get(player).onComplete().nextQuest(), false, Storage.wayPreviousQuestWereGiven.get(player));
							if(result == 0)
							{
								Chat.message(player, qQuests.plugin.qAPI.getActiveQuest(player).onJoin().message());
							}
							else
								Chat.errorCode(result, Storage.wayPreviousQuestWereGiven.get(player), player);
						}
						Storage.previousQuest.remove(player);
						Storage.wayPreviousQuestWereGiven.remove(player);
						Storage.delayLeft.remove(player);
					}
				}, (entry.getValue() * 20)+10);
			}

			Chat.logger("debug", "Persisted: " + currentQuests.toString());

			return true;
		} catch (Exception e) {
			Chat.logger("severe", "Quests are not able to be loaded from file! The quests will not persist!");
			return false;
		}
	}
}
