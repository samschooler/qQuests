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
	private Map<String, Quest> quests = new HashMap<String, Quest>();
	private Map<Player, Quest> currentQuests = new HashMap<Player, Quest>();
	private qQuests plugin;
    public QuestWorker(qQuests plugin) 
    {
	this.plugin = plugin;
    }
	
	public void buildQuests()
	{
		quests.clear();
		currentQuests.clear();
		
		for (Object questName :
			plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			
			// Validate The Quest
			plugin.Config.validate(root);
				
			BuildQuest quest = new BuildQuest(root);
			quest.messageStart(plugin.Config.getQuestConfig().getString(questName + ".setup.messageStart"));
			quest.messageEnd(plugin.Config.getQuestConfig().getString(questName + ".setup.messageEnd"));
			quest.tasksOrdered(plugin.Config.getQuestConfig().getBoolean(questName + ".setup.tasksOrdered"));
			quest.repeated(plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.nextQuest(plugin.Config.getQuestConfig().getString(questName + ".setup.nextQuest"));
			for (Object taskNo : plugin.Config.getQuestConfig().createSection(questName + ".tasks").getKeys(false)) 
			{
				try
			    {
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					BuildTask task = new BuildTask(tRoot);
					task.type(plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type"));
					// TODO
					if(plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("kill"))
					{
						//task.id(plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id"));
						task.name(plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".name"));
					}
					else
					{
						task.id(plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".id"));
						task.name(plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".name"));
					}
					task.amount(plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount"));
					this.rememberTask(tRoot, task.create(), quest);
			    }
				catch (NumberFormatException nfe)
				{
					qQuests.plugin.logger.severe(qQuests.plugin.prefix + "The tasks of quest '" + root + "' are not correctly formatted!");
				}
			}
			quest.toJoin().put("money", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.money"));
			quest.toJoin().put("health", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.health"));
			quest.toJoin().put("hunger", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.hunger"));
			
			quest.toDrop().put("money", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.money"));
			quest.toDrop().put("health", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.health"));
			quest.toDrop().put("hunger", plugin.Config.getQuestConfig().getInt(questName + ".market.toJoin.hunger"));
			
			for (Object rewardNo : plugin.Config.getQuestConfig().createSection(questName + ".market.toComplete").getKeys(false)) 
			{
				try
			    {
					Integer rRoot = Integer.parseInt(rewardNo.toString().trim());
					BuildReward reward = new BuildReward(rRoot);
					reward.money = plugin.Config.getQuestConfig().getInt(questName + ".market.toComplete." + rewardNo + ".money");
					reward.health = plugin.Config.getQuestConfig().getInt(questName + ".market.toComplete." + rewardNo + ".health");
					reward.hunger = plugin.Config.getQuestConfig().getInt(questName + ".market.toComplete." + rewardNo + ".hunger");
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
		quest.tasks().put(taskNo, task);
	}
	private void rememberReward(Integer rewardNo, Reward reward, BuildQuest quest) 
	{
		quest.toComplete().put(rewardNo, reward);
	}
	private void rememberQuest(Quest quest) 
	{
		quests.put(quest.name().toLowerCase(), quest);
	}
	
	// API Functions
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