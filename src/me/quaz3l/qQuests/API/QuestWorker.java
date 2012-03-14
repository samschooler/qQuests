package me.quaz3l.qQuests.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildQuest;
import me.quaz3l.qQuests.API.Build.BuildTask;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.API.Util.Task;
import me.quaz3l.qQuests.Util.Chat;

import org.bukkit.entity.Player;


/**
 * This class is where all the quest manipulating action goes through
 */
public class QuestWorker
{
	private Map<String, Quest> quests = new HashMap<String, Quest>();
	private Map<String, Quest> visibleQuests = new HashMap<String, Quest>();
	private Map<Player, Quest> currentQuests = new HashMap<Player, Quest>();
	private boolean valid = true;
    
    /**
     * This function reloads the quests and is not to be used unless you need to have the quests.yml re-indexed.
     */
    public void buildQuests()
	{
    	this.valid = true;
    	quests.clear();
    	currentQuests.clear();
    	qQuests.plugin.Config.reloadQuestConfig();
    	
		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			// Validate The Quest
			this.valid = qQuests.plugin.Config.validate(root, this);
				
			BuildQuest quest = new BuildQuest(root);
			quest.repeated(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
			quest.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.nextQuest"));
			for (Object taskNo : qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
			{
				try
			    {
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					BuildTask task = new BuildTask(tRoot);
					//String type = qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type");
					/*
					if(!(type.equalsIgnoreCase("collect")) ||
							!(type.equalsIgnoreCase("destroy")) ||
							!(type.equalsIgnoreCase("damage")) ||
							!(type.equalsIgnoreCase("kill")) ||
							!(type.equalsIgnoreCase("kill_player")) //||
							//!(type.equalsIgnoreCase("goto")) ||
							//!(type.equalsIgnoreCase("distance"))
							)
					{
						Chat.logger("severe", "Sorry but the task type '"+ type + "' of quest '" + root + "' is not yet supported! Disabling quest...");
						this.valid = false;
					}
					*/
					task.type(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type"));
					if(task.type().equalsIgnoreCase("collect") ||
							task.type().equalsIgnoreCase("destroy") ||
							task.type().equalsIgnoreCase("damage"))
						task.id(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".id"));
					else if(task.type().equalsIgnoreCase("kill") ||
							task.type().equalsIgnoreCase("kill_player"))
						task.id(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id"));
					
					task.display(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".display"));
					task.amount(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount"));
					this.rememberTask(tRoot, task.create(), quest);
			    }
				catch (Exception e)
				{
					Chat.logger("severe", "The tasks of quest '" + root + "' are not correctly formatted! Disabling this quest...");
					this.valid = false;
				}
			}
			quest.BuildonJoin().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			quest.BuildonJoin().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.money"));
			quest.BuildonJoin().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.health"));
			quest.BuildonJoin().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.hunger"));
			String[] strs = {""};
			int i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onJoin.market.items") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onJoin.market.items")) {
						strs = s.split(" ");

					try
					{
						ArrayList<Integer> itms = new ArrayList<Integer>();
						itms.add(Integer.parseInt(strs[0])); // Item ID
						itms.add(Integer.parseInt(strs[1])); // Amount
						quest.BuildonJoin().items().put(i, itms);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onJoin' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
						this.valid = false;
					}
					i++;
				}

			quest.BuildonDrop().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.message"));
			quest.BuildonDrop().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.money"));
			quest.BuildonDrop().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.health"));
			quest.BuildonDrop().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.hunger"));
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onDrop.market.items") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onDrop.market.items")) {
						strs = s.split(" ");

					try
					{
						ArrayList<Integer> itms = new ArrayList<Integer>();
						itms.add(Integer.parseInt(strs[0])); // Item ID
						itms.add(Integer.parseInt(strs[1])); // Amount
						quest.BuildonDrop().items().put(i, itms);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onDrop' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
						this.valid = false;
					}
					i++;
				}
			
			quest.BuildonComplete().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.message"));
			quest.BuildonComplete().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.money"));
			quest.BuildonComplete().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.health"));
			quest.BuildonComplete().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.hunger"));
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onComplete.market.items") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onComplete.market.items")) {
						strs = s.split(" ");

					try
					{
						ArrayList<Integer> itms = new ArrayList<Integer>();
						itms.add(Integer.parseInt(strs[0])); // Item ID
						itms.add(Integer.parseInt(strs[1])); // Amount
						quest.BuildonComplete().items().put(i, itms);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onComplete' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
						this.valid = false;
					}
					i++;
				}
			
			if(this.valid)
				this.rememberQuest(quest.create());
			else
				Chat.logger("severe", "Sorry! Quest '" + root + "' is not correctly formatted it has been disabled!");
		}
		Chat.logger("info", this.getQuests().size() + " Quests Successfully Loaded Into Memory.");
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks().put(taskNo, task);
	}
	private void rememberQuest(Quest quest) 
	{
		quests.put(quest.name(), quest);
		if(!quest.invisible())
			visibleQuests.put(quest.name(), quest);
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
	public Map<String, Quest> getVisibleQuests()
	{
		return this.visibleQuests;
	}
	/**
	 * <b>Use the same method from the base API</b>
	 */
	public Map<Player, Quest> getActiveQuests()
	{
		return this.currentQuests;
	}
}