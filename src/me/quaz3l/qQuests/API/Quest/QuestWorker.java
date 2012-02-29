package me.quaz3l.qQuests.API.Quest;

import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildQuest;
import me.quaz3l.qQuests.API.Build.BuildTask;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Task;

import org.bukkit.entity.Player;


/**
 * This class is where all the quest manipulating action goes through
 */
public class QuestWorker
{
	private Map<String, Quest> quests = new HashMap<String, Quest>();
	private Map<Player, Quest> currentQuests = new HashMap<Player, Quest>();
    
    /**
     * This function reloads the quests and is not to be used unless you need to have the quests.yml re-indexed.
     */
    public void buildQuests()
	{
    	boolean valid = true;
    	quests.clear();
    	currentQuests.clear();
    	qQuests.plugin.Config.reloadQuestConfig();
    	
		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			// Validate The Quest
			valid = qQuests.plugin.Config.validate(root, this);
				
			BuildQuest quest = new BuildQuest(root);
			quest.multiTaskMode(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.multiTaskMode"));
			quest.repeated(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
			quest.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.nextQuest"));
			for (Object taskNo : qQuests.plugin.Config.getQuestConfig().createSection(questName + ".tasks").getKeys(false)) 
			{
				try
			    {
					// TODO
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("collect")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("destroy")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("damage")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("place")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("kill")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("kill_player")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("kill")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("goto")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("distance")) 
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					else
					{
						qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! A " + qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") + " quest is not yet supported!");
						valid = false;
					}
					BuildTask task = new BuildTask(tRoot);
					task.type(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type"));
					// TODO
					if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type").equalsIgnoreCase("kill"))
					{
						//task.id(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id"));
						task.name(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".name"));
					}
					else
					{
						task.id(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".id"));
						task.name(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".name"));
					}
					task.amount(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount"));
					this.rememberTask(tRoot, task.create(), quest);
			    }
				catch (Exception e)
				{
					qQuests.plugin.logger.severe(qQuests.plugin.prefix + "The tasks of quest '" + root + "' are not correctly formatted! Disabling this quest");
					valid = false;
				}
			}
			quest.BuildonJoin().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			quest.BuildonJoin().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.money"));
			quest.BuildonJoin().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.health"));
			quest.BuildonJoin().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.hunger"));
			
			quest.BuildonDrop().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.message"));
			quest.BuildonDrop().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.money"));
			quest.BuildonDrop().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.health"));
			quest.BuildonDrop().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.hunger"));
			
			quest.BuildonComplete().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.message"));
			quest.BuildonComplete().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.money"));
			quest.BuildonComplete().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.health"));
			quest.BuildonComplete().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.hunger"));
			
			if(valid)
				this.rememberQuest(quest.create());
			else
				qQuests.plugin.logger.warning(qQuests.plugin.prefix + "Sorry! Quest '" + root + "' is not correctly formatted it has been disabled!");
		}
		qQuests.plugin.logger.info(qQuests.plugin.prefix + this.getQuests().size() + " Quests Successfully Loaded Into Memory.");
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks().put(taskNo, task);
	}
	private void rememberQuest(Quest quest) 
	{
		quests.put(quest.name().toLowerCase(), quest);
	}
	
	/**
	 * <b>Use the same method from the base API</b>
	 */
	public Map<String, Quest> getQuests()
	{
		return this.quests;
	}
	/**
	 * <b>Use the same method from the base API</b>
	 */
	public Map<Player, Quest> getActiveQuests()
	{
		return this.currentQuests;
	}
}