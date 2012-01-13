package me.quaz3l.qQuests.Quests;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.entity.Player;

public class QuestActions {
	private qQuests plugin;
	// Gives A Random Quest
	/*
	public Quest give(Player player)
	{
		Set<String> questNo = plugin.getQuestConfig().getKeys(false);
		Random rand = new Random(); 
		Integer SelectedQuest = rand.nextInt(questNo.size()); 
		
		return quest;
	}
	// Gives A Quest Based On Type
	public Quest give(Player player, String type)
	{
		
		return quest;
	}
	*/
	// Gives A Specific Quest
	public Quest give(Player player, Quest quest)
	{
		if(plugin.currentQuests.get(player) == null) 
		{
			plugin.currentQuests.put(player, quest);
			return quest;
		}
		return null;
	}
	// Gets The Quest Info
	public QuestInfo infoFormat(Quest quest)
	{
		QuestInfo QuestInfo = new QuestInfo(quest);
		return QuestInfo;
	}
	// Drops The Current Quest With market.onDrop Configuration Unless Forced
	public boolean drop(Player player, boolean force)
	{
		if(plugin.currentQuests.get(player) != null) 
		{
			plugin.currentQuests.put(player, null);
			return true;
		}
		return false;
	}
	// Checks To See Is Completed, If Not Returns False, Unless Forced, Then It Completes The Quest 
	public boolean done(Player player, boolean force)
	{
		if(plugin.currentQuests.get(player) != null) 
		{
			plugin.currentQuests.put(player, null);
			return true;
		}
		return false;
	}
}
