package me.quaz3l.qQuests.API;

import java.util.ArrayList;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildTask;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildonSomething;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;

import org.bukkit.Material;


/**
 * This class is where all the quest manipulating action goes through
 */
public class QuestWorker
{    
    /**
     * This function reloads the quests and is not to be used unless you need to have the quests.yml re-indexed.
     */
    public void buildQuests()
	{
    	Storage.quests.clear();
    	Storage.currentQuests.clear();
    	qQuests.plugin.Config.loadConfigs();
    	
		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			
			// Validate The Quest
			//if(!qQuests.plugin.Config.validate(root)) 
				//continue;
			
			BuildQuest quest = new BuildQuest(root);
			
			// Set Setup Variables
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.repeated") == null)
				quest.repeated(-1);
			else
				quest.repeated(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
			
			// Set Requirements Variables
			quest.levelMin(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".requirements.levelMin"));
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".requirements.levelMax") == null)
				quest.levelMax(-1);
			else
				quest.levelMax(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".requirements.levelMax"));
			
			// Set Tasks Variables
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
							String slug = qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id");
							String[] info = slug.split(":");
							Chat.logger("debug", "Slug: " + slug);
							Chat.logger("debug", "ID: " + info[0]);
							if(info.length == 2) {
								try {
									Chat.logger("debug", "DR: " + info[1]);
									task.id(Integer.parseInt(info[0]));
									task.durability(((Integer)Integer.parseInt(info[1])).shortValue());
								} catch(Exception e) {
									Chat.logger("severe", "The #" + tRoot +" task of '" + root + "' does not have valid material ids! Disabling this quest...");
								}
							} else {
								try {
									task.id(Integer.parseInt(info[0]));
								} catch(Exception e) {
									Chat.logger("severe", "The #" + tRoot +" task of '" + root + "' does not have valid material ids! Disabling this quest...");
								}
							}
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
					continue;
				}
				catch (NumberFormatException e)
				{
					Chat.logger("severe", "The task nodes of quest '" + root + "' are not numbers! Disabling this quest...");
					continue;
				}
				i++;
			}
			
			String[] strs = {""};
			String[] qtrs = {""};
			// Set onJoin Variables
			BuildonSomething BuildonJoin = new BuildonSomething();
			BuildonJoin.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			BuildonJoin.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.delay"));
			BuildonJoin.money(qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".onJoin.market.money"));
			BuildonJoin.health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.health"));
			BuildonJoin.hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.hunger"));
			BuildonJoin.levelAdd(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.levelAdd"));
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.market.levelSet") != null)
				BuildonJoin.levelSet(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.market.levelSet"));
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onJoin.market.items") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onJoin.market.items")) {
					strs = s.split(" ");
					qtrs = strs[0].split(":");
					
					if(Material.matchMaterial(qtrs[0]) != null)
					{
						try
						{
							ArrayList<Integer> itms = new ArrayList<Integer>();
							itms.add(Integer.parseInt(qtrs[0])); // Item Id
							itms.add(Integer.parseInt(strs[1])); // Amount
							if(qtrs.length == 2)
								itms.add(Integer.parseInt(qtrs[1])); // Item Damage
							BuildonJoin.items(i, itms);
						}
						catch(Exception e)
						{
							Chat.logger("severe", "The 'onJoin' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
							continue;
						}
					}
					else
					{
						Chat.logger("severe", "The 'onJoin' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
						continue;
					}
					i++;
				}
			}
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onJoin.market.commands") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onJoin.market.commands")) {
					BuildonJoin.commands(i, s);
					i++;
				}
			}
			quest.onJoin(BuildonJoin);
			
			// Set onDrop Variables
			BuildonSomething BuildonDrop = new BuildonSomething();
			BuildonDrop.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.message"));
			BuildonDrop.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.delay"));
			BuildonDrop.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.nextQuest"));
			BuildonDrop.money(qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".onDrop.market.money"));
			BuildonDrop.health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.health"));
			BuildonDrop.hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.hunger"));
			BuildonDrop.levelAdd(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.levelAdd"));
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.market.levelSet") != null)
				BuildonDrop.levelSet(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.market.levelSet"));
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onDrop.market.items") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onDrop.market.items")) {
					strs = s.split(" ");
					qtrs = strs[0].split(":");
					
					if(Material.matchMaterial(qtrs[0]) != null)
					{
						try
						{
							ArrayList<Integer> itms = new ArrayList<Integer>();
							itms.add(Integer.parseInt(qtrs[0])); // Item Id
							itms.add(Integer.parseInt(strs[1])); // Amount
							if(qtrs.length == 2)
								itms.add(Integer.parseInt(qtrs[1])); // Item Damage
							BuildonDrop.items().put(i, itms);
						}
						catch(Exception e)
						{
							Chat.logger("severe", "The 'onDrop' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
							continue;
						}
					}
					else
					{
						Chat.logger("severe", "The 'onDrop' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
						continue;
					}
					i++;
				}
			}
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onDrop.market.commands") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onDrop.market.commands")) {
					BuildonDrop.commands(i, s);
					i++;
				}
			}
			quest.onDrop(BuildonDrop);
			
			// Set onComplete Variables
			BuildonSomething BuildonComplete = new BuildonSomething();
			BuildonComplete.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.message"));
			BuildonComplete.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.delay"));
			BuildonComplete.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.nextQuest"));
			BuildonComplete.money(qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".onComplete.market.money"));
			BuildonComplete.health(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.health"));
			BuildonComplete.hunger(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.hunger"));
			BuildonComplete.levelAdd(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.levelAdd"));
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.market.levelSet") != null)
				BuildonComplete.levelSet(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.market.levelSet"));
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onComplete.market.items") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onComplete.market.items")) {
					strs = s.split(" ");
					qtrs = strs[0].split(":");
					
					if(Material.matchMaterial(qtrs[0]) != null)
					{
						try
						{
							ArrayList<Integer> itms = new ArrayList<Integer>();
							itms.add(Integer.parseInt(qtrs[0])); // Item Id
							itms.add(Integer.parseInt(strs[1])); // Amount
							if(qtrs.length == 2)
								itms.add(Integer.parseInt(qtrs[1])); // Item Damage
							BuildonComplete.items().put(i, itms);
						}
						catch(Exception e)
						{
							Chat.logger("severe", "The 'onComplete' rewards/fees of '" + root + "' are not correctly formatted! Disabling this quest...");
							continue;
						}
					}
					else
					{
						Chat.logger("severe", "The 'onComplete' rewards/fees of '" + root + "' does not have valid material ids! Disabling this quest...");
						continue;
					}
					i++;
				}
			}
			i=0;
			if(qQuests.plugin.Config.getQuestConfig().getList(questName + ".onComplete.market.commands") != null)
			{
				for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(questName + ".onComplete.market.commands")) {
					BuildonComplete.commands(i, s);
					i++;
				}
			}
			quest.onComplete(BuildonComplete);
			
			// Only if the quest is valid, save the quest.
			this.rememberQuest(quest.create());
		}
		Chat.logger("info", Storage.quests.size() + " Quests Successfully Loaded Into Memory.");
	}
	
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks(taskNo, task);
	}
	private void rememberQuest(Quest quest) 
	{
		Storage.quests.put(quest.name(), quest);
		if(!quest.invisible())
			Storage.visibleQuests.put(quest.name(), quest);
	}
}