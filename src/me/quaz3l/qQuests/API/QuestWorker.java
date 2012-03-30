package me.quaz3l.qQuests.API;

import java.util.ArrayList;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildTask;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;

import org.bukkit.Material;


/**
 * This class is where all the quest manipulating action goes through
 */
public class QuestWorker
{
	private boolean valid = true;
    
    /**
     * This function reloads the quests and is not to be used unless you need to have the quests.yml re-indexed.
     */
    public void buildQuests()
	{
    	this.valid = true;
    	Storage.quests.clear();
    	Storage.currentQuests.clear();
    	qQuests.plugin.Config.reloadQuestConfig();
    	
		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			// Validate The Quest
			this.valid = qQuests.plugin.Config.validate(root, this);
			if(!valid) continue;
			
			BuildQuest quest = new BuildQuest(root);
			quest.repeated(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
			quest.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.nextQuest"));
			quest.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.delay"));
			
			int i=0;
			for (Object taskNo : qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
			{
				try
			    {
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					if(qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks." + i).getKeys(false).size() <= 0)
						throw new NullPointerException();
					else
					{
						BuildTask task = new BuildTask(tRoot);
						task.type(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type"));
						if(task.type().equalsIgnoreCase("collect") ||
								task.type().equalsIgnoreCase("destroy") ||
								task.type().equalsIgnoreCase("damage") ||
								task.type().equalsIgnoreCase("place") ||
								task.type().equalsIgnoreCase("enchant"))
						{
							task.id(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".id"));
						}
						else if(task.type().equalsIgnoreCase("kill") ||
								task.type().equalsIgnoreCase("kill_player") ||
								task.type().equalsIgnoreCase("tame"))
							task.id(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id"));
						
						task.display(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".display"));
						task.amount(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount"));
						this.rememberTask(tRoot, task.create(), quest);
					}
			    }
				catch (NullPointerException e)
				{
					Chat.logger("severe", "The task nodes of quest '" + root + "' do not start with 0 and go in order up! Disabling this quest...");
					this.valid = false;
				}
				catch (NumberFormatException e)
				{
					Chat.logger("severe", "The task nodes of quest '" + root + "' are not numbers! Disabling this quest...");
					this.valid = false;
				}
				i++;
			}
			quest.BuildonJoin().message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			quest.BuildonJoin().money(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.money"));
			quest.BuildonJoin().health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.health"));
			quest.BuildonJoin().hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.hunger"));
			String[] strs = {""};
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onJoin.market.items") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onJoin.market.items")) {
						strs = s.split(" ");
						
						if(Material.matchMaterial(strs[0]) != null)
						{
							try
							{
								ArrayList<Integer> itms = new ArrayList<Integer>();
								itms.add(Integer.parseInt(strs[0])); // Item Id
								itms.add(Integer.parseInt(strs[1])); // Amount
								quest.BuildonJoin().items().put(i, itms);
							}
							catch(Exception e)
							{
								Chat.logger("severe", "The 'onJoin' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
								this.valid = false;
							}
						}
						else
						{
							Chat.logger("severe", "The 'onJoin' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
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
					
					if(Material.matchMaterial(strs[0]) != null)
					{
						try
						{
							ArrayList<Integer> itms = new ArrayList<Integer>();
							itms.add(Integer.parseInt(strs[0])); // Item Id
							itms.add(Integer.parseInt(strs[1])); // Amount
							quest.BuildonDrop().items().put(i, itms);
						}
						catch(Exception e)
						{
							Chat.logger("severe", "The 'onDrop' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
							this.valid = false;
						}
					}
					else
					{
						Chat.logger("severe", "The 'onDrop' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
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
					
					if(Material.matchMaterial(strs[0]) != null)
					{
						try
						{
							ArrayList<Integer> itms = new ArrayList<Integer>();
							itms.add(Integer.parseInt(strs[0])); // Item Id
							itms.add(Integer.parseInt(strs[1])); // Amount
							quest.BuildonComplete().items().put(i, itms);
						}
						catch(Exception e)
						{
							Chat.logger("severe", "The 'onComplete' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
							this.valid = false;
						}
					}
					else
					{
						Chat.logger("severe", "The 'onComplete' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
						this.valid = false;
					}
				i++;
			}
			
			/*
			// Permissions
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onComplete.permissions.give") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onComplete.permissions.give"))
					quest.BuildonComplete().permissionsAdd().put(i, s);
			
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onComplete.permissions.take") != null)
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onComplete.permissions.take"))
					quest.BuildonComplete().permissionsTake().put(i, s);
			*/
			
			if(this.valid)
				this.rememberQuest(quest.create());
			else
				Chat.logger("severe", "Sorry! Quest '" + root + "' is not correctly formatted it has been disabled!");
		}
		Chat.logger("info", Storage.quests.size() + " Quests Successfully Loaded Into Memory.");
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks().put(taskNo, task);
	}
	private void rememberQuest(Quest quest) 
	{
		Storage.quests.put(quest.name(), quest);
		if(!quest.invisible())
			Storage.visibleQuests.put(quest.name(), quest);
	}
}