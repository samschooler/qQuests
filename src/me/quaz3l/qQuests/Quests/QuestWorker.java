package me.quaz3l.qQuests.Quests;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class QuestWorker
{
	
	public static final Map<String, Quest> quests = new HashMap<String, Quest>();
	public Map<Player, Quest> currentQuests = new HashMap<Player, Quest>();
	
	public void buildQuests(FileConfiguration questConfig)
	{
		for (Object questName : questConfig.getKeys(false)) 
		{
			String root = questName.toString();
			BuildQuest quest = new BuildQuest(root);
			quest.messageStart = questConfig.getString(questName + ".info.messageStart");
			quest.messageEnd = questConfig.getString(questName + ".info.messageEnd");
			quest.tasksOrdered = questConfig.getBoolean(questName + ".info.tasksOrdered");
			for (Object taskNo : questConfig.createSection(questName + ".tasks").getKeys(false)) 
			{
				Integer tRoot = (Integer) taskNo;
				BuildTask task = new BuildTask(tRoot);
				task.type = questConfig.getString(questName + ".tasks." + taskNo + ".type");
				task.id = questConfig.getInt(questName + ".tasks." + taskNo + ".id");
				task.name = questConfig.getString(questName + ".tasks." + taskNo + ".name");
				task.amount = questConfig.getInt(questName + ".tasks." + taskNo + ".amount");
				this.rememberTask(tRoot, task.create(), quest);
			}
			quest.toJoin.put("money", questConfig.getInt(questName + ".market.toJoin.money"));
			quest.toJoin.put("health", questConfig.getInt(questName + ".market.toJoin.health"));
			quest.toJoin.put("hunger", questConfig.getInt(questName + ".market.toJoin.hunger"));
			
			quest.toDrop.put("money", questConfig.getInt(questName + ".market.toJoin.money"));
			quest.toDrop.put("health", questConfig.getInt(questName + ".market.toJoin.health"));
			quest.toDrop.put("hunger", questConfig.getInt(questName + ".market.toJoin.hunger"));
			
			for (Object rewardNo : questConfig.createSection(questName + ".market.toComplete").getKeys(false)) 
			{
				Integer rRoot = (Integer) rewardNo;
				BuildReward reward = new BuildReward(rRoot);
				reward.money = questConfig.getString(questName + ".market.toComplete." + rewardNo + ".money");
				reward.health = questConfig.getInt(questName + ".market.toComplete." + rewardNo + ".health");
				reward.hunger = questConfig.getString(questName + ".market.toComplete." + rewardNo + ".hunger");
				this.rememberReward(rRoot, reward.create(), quest);
			}
			this.rememberQuest(quest.create());
		}
	}
	public void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks.put(taskNo, task);
	}
	public void rememberReward(Integer rewardNo, Reward reward, BuildQuest quest) 
	{
		quest.toComplete.put(rewardNo, reward);
	}
	public void rememberQuest(Quest quest) 
	{
		quests.put(quest.name.toLowerCase(), quest);
	}
	public Quest currentQuests(Player player)
	{
		return this.currentQuests.get(player);
	}
}