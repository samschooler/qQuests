package me.quaz3l.qQuests.Quests;

import java.util.Random;
import me.quaz3l.qQuests.qQuests;

import org.bukkit.entity.Player;

public class QuestActions {	
	
	// Gives A Random Quest
	public Quest give(Player player)
	{
		Random rand = new Random(); 
		Integer selectedQuest = rand.nextInt(qQuests.plugin.getQuestWorker().quests.size()); 
		Object[] qSet = qQuests.plugin.getQuestWorker().quests.keySet().toArray();
		Quest quest = qQuests.plugin.getQuestWorker().quests.get(qSet[selectedQuest]);
		qQuests.plugin.getQuestWorker().currentQuests.put(player, quest);
		return quest;
	}
	
	// TODO Gives A Quest Based On Type
	/*private Quest giveType(Player player, String type)
	{
		
		return quest;
	}*/
	
	// Gives A Specific Quest
	public Quest give(Player player, String quest)
	{
		player.sendMessage(quest + "---" + player + "---" + qQuests.plugin.currentQuests.get(player));
		if(!qQuests.plugin
				//.getQuestWorker()
				.currentQuests
				.containsKey(player)) 
		{
			Quest qClass = qQuests.plugin.getQuestWorker().quests.get(quest.toLowerCase());
			qQuests.plugin.getQuestWorker().currentQuests.put(player, qClass);
			return qClass;
		}
		return null;
	}
	
	// TODO Gets The Quest Info
	public QuestInfo infoFormat(Quest quest)
	{
		QuestInfo QuestInfo = new QuestInfo(quest);
		return QuestInfo;
	}
	
	// Drops The Current Quest With market.onDrop Configuration Unless Forced
	public boolean drop(Player player, boolean force)
	{
		if(qQuests.plugin.currentQuests.get(player) != null) 
		{
			qQuests.plugin.getQuestWorker().currentQuests.put(player, null);
			return true;
		}
		return false;
	}
	
	// TODO Checks To See Is Completed, If Not Returns False, Unless Forced, Then It Completes The Quest 
	public boolean done(Player player, boolean force)
	{
		if(qQuests.plugin.getQuestWorker().currentQuests.get(player) != null) 
		{
			qQuests.plugin.getQuestWorker().currentQuests.put(player, null);
			return true;
		}
		return false;
	}
	
	
}
