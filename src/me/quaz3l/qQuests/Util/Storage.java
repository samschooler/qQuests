package me.quaz3l.qQuests.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

public class Storage {
	// Quests
	public static HashMap<String, Quest> quests = new HashMap<String, Quest>();
	public static HashMap<String, Quest> visibleQuests = new HashMap<String, Quest>();
	public static HashMap<String, Quest> currentQuests = new HashMap<String, Quest>(); // Persist

	// Task Progress
	public static HashMap<String, ArrayList<Integer>> currentTaskProgress = new HashMap<String, ArrayList<Integer>>(); //Persist

	// Previous Quests
	public static HashMap<String, Quest> previousQuest = new HashMap<String, Quest>(); //Persist

	// Way Quests Were Given
	public static HashMap<String, String> wayCurrentQuestsWereGiven = new HashMap<String, String>(); //Persist
	public static HashMap<String, String> wayPreviousQuestWereGiven = new HashMap<String, String>(); //Persist

	// Keep track of delays for persistence
	public static HashMap<String, Integer> delayLeft = new HashMap<String, Integer>(); //Persist

	// Strings That Can't Get Quests
	public static ArrayList<String> cannotGetQuests = new ArrayList<String>();


	// Config Values
	public static String primaryCommand = "quest";
	public static String prefix = "qQuests";
	public static int persistDelay = 5;

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
		if(cmd.equalsIgnoreCase("list"))
			cmd="info";
		else if(cmd.equalsIgnoreCase("progress"))
			cmd="tasks";
		else if(cmd.equalsIgnoreCase("finish")||cmd.equalsIgnoreCase("end"))
			cmd="done";
		else if(cmd.equalsIgnoreCase("help")||cmd.equalsIgnoreCase("stats"))
			return true;
		else if(!cmd.equalsIgnoreCase("tasks") &&
				!cmd.equalsIgnoreCase("progress") &&
				!cmd.equalsIgnoreCase("drop") &&
				!cmd.equalsIgnoreCase("done"))
			return true;
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
				persist();
				qQuests.plugin.qAPI.persist().save();
			}
		}, persistDelay *60 * 20, persistDelay *60 * 20); // Persist data every minute
	}
	public static void persist()
	{
		ArrayList<String> curPlayers = new ArrayList<String>();
		// currentQuests
		HashMap<String, String> cQuests = new HashMap<String, String>();
		for(Entry<String, Quest> entry : Storage.currentQuests.entrySet()) {
			if(entry.getValue() != null) {
				cQuests.put(entry.getKey(), entry.getValue().name());
				curPlayers.add(entry.getKey());
			} else {
				cQuests.put(entry.getKey(), null);
			}
		}
		qQuests.plugin.qAPI.persist().set("currentQuests", cQuests);

		// currentTaskProgress
		HashMap<String, Object[]> cTaskProgress = new HashMap<String, Object[]>();
		for(Entry<String, ArrayList<Integer>> entry : Storage.currentTaskProgress.entrySet()) {
			if(!curPlayers.contains(entry.getKey()))
				cTaskProgress.put(entry.getKey(), null);
			else
				cTaskProgress.put(entry.getKey(), entry.getValue().toArray());
		}
		qQuests.plugin.qAPI.persist().setArrayHashMap("currentTaskProgress", cTaskProgress);

		// previousQuests
		HashMap<String, String> pQuest = new HashMap<String, String>();
		for(Entry<String, Quest> entry : Storage.previousQuest.entrySet()) {
			if(!curPlayers.contains(entry.getKey()))
				pQuest.put(entry.getKey(), null);
			else
				pQuest.put(entry.getKey(), entry.getValue().name());
		}
		qQuests.plugin.qAPI.persist().set("previousQuests", pQuest);

		Chat.logger("debug", cQuests.toString());
		Chat.logger("debug", qQuests.plugin.qAPI.persist().getStringHashMap("currentQuests").toString());

		// wayCurrentQuestsWereGiven
		HashMap<String, String> wcQuestsWereGiven = new HashMap<String, String>();
		for(Entry<String, String> entry : Storage.wayCurrentQuestsWereGiven.entrySet()) {
			if(!curPlayers.contains(entry.getKey()))
				wcQuestsWereGiven.put(entry.getKey(), null);
			else
				wcQuestsWereGiven.put(entry.getKey(), entry.getValue());
		}
		qQuests.plugin.qAPI.persist().set("wayCurrentQuestsWereGiven", wcQuestsWereGiven);

		// wayPreviousQuestWereGiven
		HashMap<String, String> wpQuestWereGiven = new HashMap<String, String>();
		for(Entry<String, String> entry : Storage.wayPreviousQuestWereGiven.entrySet()) {
				wpQuestWereGiven.put(entry.getKey(), entry.getValue());
		}
		qQuests.plugin.qAPI.persist().set("wayPreviousQuestWereGiven", wpQuestWereGiven);

		// delayLeft
		HashMap<String, String> dLeft = new HashMap<String, String>();
		for(Entry<String, Integer> entry : Storage.delayLeft.entrySet()) {
				dLeft.put(entry.getKey(), entry.getValue().toString());
		}
		qQuests.plugin.qAPI.persist().set("delayLeft", dLeft);

		qQuests.plugin.qAPI.persist().save();

		Chat.logger("info", "Quests persisted to disk...");
	}
	public static void loadPersisted()
	{
		qQuests.plugin.getServer().getScheduler().runTaskAsynchronously(qQuests.plugin, new Runnable() {
			public void run() {
				// currentQuests
				for(Entry<String, String> entry : 
					((HashMap<String, String>) qQuests.plugin.qAPI.persist().getStringHashMap("currentQuests")).entrySet())
					Storage.currentQuests.put(entry.getKey(), qQuests.plugin.qAPI.getQuest(entry.getValue()));

				// currentTaskProgress
				for(Entry<String, ArrayList<Integer>> entry : 
					((HashMap<String, ArrayList<Integer>>) qQuests.plugin.qAPI.persist().getIntegerArrayHashMap("currentTaskProgress")).entrySet())
					Storage.currentTaskProgress.put(entry.getKey(), entry.getValue());

				// previousQuests
				for(Entry<String, String> entry : 
					((HashMap<String, String>) qQuests.plugin.qAPI.persist().getStringHashMap("previousQuests")).entrySet())
					Storage.previousQuest.put(entry.getKey(), qQuests.plugin.qAPI.getQuest(entry.getValue()));

				// wayCurrentQuestsWereGiven
				for(Entry<String, String> entry : 
					((HashMap<String, String>) qQuests.plugin.qAPI.persist().getStringHashMap("wayCurrentQuestsWereGiven")).entrySet())
					Storage.wayCurrentQuestsWereGiven.put(entry.getKey(), entry.getValue());

				// wayPreviousQuestWereGiven
				for(Entry<String, String> entry : 
					((HashMap<String, String>) qQuests.plugin.qAPI.persist().getStringHashMap("wayPreviousQuestWereGiven")).entrySet())
					Storage.wayPreviousQuestWereGiven.put(entry.getKey(), entry.getValue());

				// delayLeft
				for(Entry<String, Integer> entry : 
					((HashMap<String, Integer>) qQuests.plugin.qAPI.persist().getIntegerHashMap("delayLeft")).entrySet()) {
					Storage.delayLeft.put(entry.getKey(), entry.getValue());

					final String player = entry.getKey();

					Storage.cannotGetQuests.add(player);
					qQuests.plugin.getServer().getScheduler().runTaskLaterAsynchronously(qQuests.plugin, new Runnable() {

						public void run() {
							Storage.cannotGetQuests.remove(player);
							if(Storage.previousQuest.get(player).onComplete().nextQuest() != null)
							{
								int result = qQuests.plugin.qAPI.giveQuest(player, Storage.previousQuest.get(player).onComplete().nextQuest(), false, false, Storage.wayPreviousQuestWereGiven.get(player));
								if(result == 0)
								{
									Chat.message(player, qQuests.plugin.qAPI.getActiveQuest(player).onJoin().message(player));
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
				Storage.rePersist();
				Chat.logger("info", "Current quests loaded from disk.");
				return;
			}
		});
	}
}
