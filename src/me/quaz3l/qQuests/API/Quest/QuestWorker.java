package me.quaz3l.qQuests.API.Quest;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildQuest;
import me.quaz3l.qQuests.API.Build.BuildReward;
import me.quaz3l.qQuests.API.Build.BuildTask;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Reward;
import me.quaz3l.qQuests.API.Util.Task;

import org.bukkit.entity.Player;

public class QuestWorker
{
	private final Map<String, Quest> quests = new HashMap<String, Quest>();
	private Map<Player, Quest> currentQuests = new HashMap<Player, Quest>();
	
	public void buildQuests()
	{
		for (Object questName :
			qQuests.plugin
			.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			BuildQuest quest = new BuildQuest(root);
			quest.messageStart = qQuests.plugin.getQuestConfig().getString(questName + ".info.messageStart");
			quest.messageEnd = qQuests.plugin.getQuestConfig().getString(questName + ".info.messageEnd");
			quest.tasksOrdered = qQuests.plugin.getQuestConfig().getBoolean(questName + ".info.tasksOrdered");
			quest.repeated = qQuests.plugin.getQuestConfig().getInt(questName + ".info.repeated");
			quest.nextQuest = qQuests.plugin.getQuestConfig().getString(questName + ".info.nextQuest");
			for (Object taskNo : qQuests.plugin.getQuestConfig().createSection(questName + ".tasks").getKeys(false)) 
			{
				try
			    {
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					BuildTask task = new BuildTask(tRoot);
					task.type = qQuests.plugin.getQuestConfig().getString(questName + ".tasks." + taskNo + ".type");
					task.id = qQuests.plugin.getQuestConfig().getInt(questName + ".tasks." + taskNo + ".id");
					task.name = qQuests.plugin.getQuestConfig().getString(questName + ".tasks." + taskNo + ".name");
					task.amount = qQuests.plugin.getQuestConfig().getInt(questName + ".tasks." + taskNo + ".amount");
					this.rememberTask(tRoot, task.create(), quest);
			    }
				catch (NumberFormatException nfe)
				{
					qQuests.plugin.logger.severe(qQuests.plugin.prefix + "The tasks of quest '" + root + "' are not correctly formatted!");
				}
			}
			quest.toJoin.put("money", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.money"));
			quest.toJoin.put("health", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.health"));
			quest.toJoin.put("hunger", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.hunger"));
			
			quest.toDrop.put("money", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.money"));
			quest.toDrop.put("health", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.health"));
			quest.toDrop.put("hunger", qQuests.plugin.getQuestConfig().getInt(questName + ".market.toJoin.hunger"));
			
			for (Object rewardNo : qQuests.plugin.getQuestConfig().createSection(questName + ".market.toComplete").getKeys(false)) 
			{
				try
			    {
					Integer rRoot = Integer.parseInt(rewardNo.toString().trim());
					BuildReward reward = new BuildReward(rRoot);
					reward.money = qQuests.plugin.getQuestConfig().getString(questName + ".market.toComplete." + rewardNo + ".money");
					reward.health = qQuests.plugin.getQuestConfig().getInt(questName + ".market.toComplete." + rewardNo + ".health");
					reward.hunger = qQuests.plugin.getQuestConfig().getString(questName + ".market.toComplete." + rewardNo + ".hunger");
					this.rememberReward(rRoot, reward.create(), quest);
			    }
				catch (NumberFormatException nfe)
				{
					qQuests.plugin.logger.severe(qQuests.plugin.prefix + "The rewards in onComplete of quest '" + root + "' are not correctly formatted!");
				}
			}
			this.rememberQuest(quest.create());
		}
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks.put(taskNo, task);
	}
	private void rememberReward(Integer rewardNo, Reward reward, BuildQuest quest) 
	{
		quest.toComplete.put(rewardNo, reward);
	}
	private void rememberQuest(Quest quest) 
	{
		quests.put(quest.name.toLowerCase(), quest);
	}
	
	public Map<String, Quest> getQuests()
	{
		return this.quests;
	}
	
	// Gets The Current Quest Of The Player
	public Quest getPlayersQuest(Player p)
	{
		return this.currentQuests.get(p);
	}
}