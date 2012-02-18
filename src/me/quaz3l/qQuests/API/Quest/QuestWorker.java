package me.quaz3l.qQuests.API.Quest;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildQuest;
import me.quaz3l.qQuests.API.Build.BuildTask;
import me.quaz3l.qQuests.API.Util.Quest;
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
			quest.multiTaskMode(plugin.Config.getQuestConfig().getBoolean(questName + ".setup.multiTaskMode"));
			quest.repeated(plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
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
			quest.BuildonJoin().message(plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			quest.BuildonJoin().money(plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.money"));
			quest.BuildonJoin().health(plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.health"));
			quest.BuildonJoin().hunger(plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.hunger"));
			
			quest.BuildonDrop().message(plugin.Config.getQuestConfig().getString(questName + ".onDrop.message"));
			quest.BuildonDrop().money(plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.money"));
			quest.BuildonDrop().health(plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.health"));
			quest.BuildonDrop().hunger(plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.hunger"));
			
			quest.BuildonComplete().message(plugin.Config.getQuestConfig().getString(questName + ".onComplete.message"));
			quest.BuildonComplete().money(plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.money"));
			quest.BuildonComplete().health(plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.health"));
			quest.BuildonComplete().hunger(plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.hunger"));
			
			this.rememberQuest(quest.create());
		}
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks().put(taskNo, task);
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