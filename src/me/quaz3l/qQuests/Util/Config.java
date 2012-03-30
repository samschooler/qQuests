package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestWorker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	private qQuests plugin;
	
	// Configuration Files Variables
	private FileConfiguration qConfig = null;
	private File qConfigFile = null;
	private FileConfiguration cConfig = null;
	private File cConfigFile = null;
	
	public Config(qQuests plugin) {
		this.plugin = plugin;
	}
			
	// Configuration Functions
	public FileConfiguration getQuestConfig() {
		if (qConfig == null) {
			reloadQuestConfig();
		}
		return qConfig;
	}
	public void reloadQuestConfig() {
		if (qConfigFile == null) 
		{
			qConfigFile = new File(plugin.getDataFolder(), "quests.yml");
		}
		qConfig = YamlConfiguration.loadConfiguration(qConfigFile);
		
		InputStream defConfigStream = plugin.getResource("quests.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			qConfig.setDefaults(defConfig);
		}
	}
	public void saveQuestConfig() {
		if (qConfig == null || qConfigFile == null) {
			return;
		}
		try {
			qConfig.save(qConfigFile);
		} catch (IOException ex) {
			plugin.logger.severe(plugin.prefix + "Could not save config to " + qConfigFile);
		}
	}
	public FileConfiguration getConfig() {
		if (cConfig == null) {
			reloadConfig();
		}
		return cConfig;
	}
	public void reloadConfig() {
		if (cConfigFile == null) 
		{
			cConfigFile = new File(plugin.getDataFolder(), "config.yml");
		}
		cConfig = YamlConfiguration.loadConfiguration(cConfigFile);
		
		InputStream defConfigStream = plugin.getResource("config.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			cConfig.setDefaults(defConfig);
			cConfig.options().copyDefaults(true);
		}
	}
	public void saveConfig() {
		if (cConfig == null || cConfigFile == null) {
	    return;
	    }
		try {
	        cConfig.save(cConfigFile);
	    } catch (IOException ex) {
	    	Chat.logger("severe", "Could not save config to " + cConfigFile);
	    }
	}
	public void initializeConfig() {
		this.getConfig();
		this.getConfig().options().copyDefaults(true);
		
		// Set General Nodes
		if(this.getConfig().getKeys(false).size() <= 0)
		{
			this.getConfig().set("autoUpdate", true);
			this.getConfig().set("tellMeYourUsingMyPlugin", true);
			this.saveConfig();
		}
	}
	public void initializeQuestConfig() {
		this.getQuestConfig();
		if(this.getQuestConfig().getKeys(false).size() < 1) {
			this.getQuestConfig().options().copyDefaults(true);
			
			// Set Setup Nodes
				this.getQuestConfig().set("Diamonds!.setup.repeated", -1);
				this.getQuestConfig().set("Diamonds!.setup.invisible", false);
				this.getQuestConfig().set("Diamonds!.setup.delay", 1);
				this.getQuestConfig().set("Diamonds!.setup.nextQuest", "");
			
			// Set Task Nodes
				this.getQuestConfig().set("Diamonds!.tasks.0.type", "collect");
				this.getQuestConfig().set("Diamonds!.tasks.0.id", 264);
				this.getQuestConfig().set("Diamonds!.tasks.0.display", "Diamond");
				this.getQuestConfig().set("Diamonds!.tasks.0.amount", 5);
			
			// Set onJoin Nodes
				this.getQuestConfig().set("Diamonds!.onJoin.message", "Hey! Can you go get my 5 diamonds! I'll pay you $500");
				this.getQuestConfig().set("Diamonds!.onJoin.market.money", 0);
				this.getQuestConfig().set("Diamonds!.onJoin.market.health", 0);
				this.getQuestConfig().set("Diamonds!.onJoin.market.hunger", 0);
			
			// Set onDrop Nodes
				this.getQuestConfig().set("Diamonds!.onDrop.message", "AwwÉ fineÉ I'll go find someone else :(");
				this.getQuestConfig().set("Diamonds!.onDrop.market.money", -50);
				this.getQuestConfig().set("Diamonds!.onDrop.market.health", 0);
				this.getQuestConfig().set("Diamonds!.onDrop.market.hunger", 0);
			
			// Set onComplete Nodes
				this.getQuestConfig().set("Diamonds!.onComplete.message", "Thanks! Now I can feed my lava dragon! ;)");
				this.getQuestConfig().set("Diamonds!.onComplete.market.money", 500);
				this.getQuestConfig().set("Diamonds!.onComplete.market.health", 0);
				this.getQuestConfig().set("Diamonds!.onComplete.market.hunger", 0);
				this.getQuestConfig().set("Diamonds!.onComplete.market.items", Arrays.asList("3 1", "4 5"));
		}
		this.saveQuestConfig();
	}
	public boolean validate(String questName, QuestWorker q) {
		Integer tRoot = 0;
		boolean rturn = true;
		
		if(qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks") == null)
		{
			Chat.logger("severe", "Quest " + questName + " disabled because the 'tasks' node does not exist!");
			return false;
		}
		if(qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false).size() < 1)
		{
			Chat.logger("severe", "Quest " + questName + " disabled because the 'tasks' node has no tasks!");
			return false;
		}
		
		for (Object taskNo : qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
		{
			try
		    {
				tRoot = Integer.parseInt(taskNo.toString().trim());
		    }
			catch(Exception e)
			{
				Chat.logger("severe", "Quest " + questName + " disabled because node 'tasks." + tRoot + "' is not a number!");
				return false;
			}
			if(this.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type") == null) 
			{
				this.getQuestConfig().set(questName + ".tasks." + tRoot + ".type", "UNDEFINED");
				rturn = false;
			}
			if(this.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id") == null) 
			{
				this.getQuestConfig().set(questName + ".tasks." + tRoot + ".id", "UNDEFINED");
				rturn = false;
			}
			if(this.getQuestConfig().getString(questName + ".tasks." + tRoot + ".display") == null) 
			{
				this.getQuestConfig().set(questName + ".tasks." + tRoot + ".display", "UNDEFINED");
				rturn = false;
			}
			if(this.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount") == 0) 
			{
				this.getQuestConfig().set(questName + ".tasks." + tRoot + ".amount", -1);
				Chat.logger("severe", "Quest " + questName + " disabled because node 'tasks." + tRoot + ".amount' is not set!");
				rturn = false;
			}
		}
		
		// Check Required onJoin Nodes
		if(this.getQuestConfig().getString(questName + ".onJoin.message") == null) 
		{
			Chat.logger("severe", "Quest " + questName + " disabled because node 'onJoin.message' is not set!");
			rturn = false;
		}
		
		// Check onDrop Nodes
		if(this.getQuestConfig().getString(questName + ".onDrop.message") == null) 
		{
			Chat.logger("severe", "Quest " + questName + " disabled because node 'onDrop.message' is not set!");
			rturn = false;
		}
		
		// Check onComplete Nodes
		if(this.getQuestConfig().getString(questName + ".onComplete.message") == null) 
		{
			Chat.logger("severe", "Quest " + questName + " disabled because node 'onComplete.message' is not set!");
			rturn = false;
		}
		
		// Check Proper Values For Nodes
		/*
		if(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated") == 0 && qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.repeated") != null)
		{
			Chat.logger("severe", "The 'setup.repeated' should not be a string! Disabling this quest...");
			rturn = false;
		}
		if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.invisible") != "true" && qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.invisible") != "false" && qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.invisible") != null)
		{
			Chat.logger("severe", "The 'setup.invisible' should not be a string, choose true or false! Disabling this quest...");
			rturn = false;
		}
		if(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.delay") == 0 && qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.delay") != null)
		{
			Chat.logger("severe", "The 'setup.delay' should not be a string! Disabling this quest...");
			rturn = false;
		}
		*/
		
		for (Object taskNo : qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
		{
			try
		    {
				tRoot = Integer.parseInt(taskNo.toString().trim());
		    }
			catch(Exception e)
			{
				Chat.logger("severe", "Quest " + questName + " disabled because node 'tasks." + tRoot + "' is not a number!");
				return false;
			}
			String type = this.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type");
			if(type != null)
				if(!type.equalsIgnoreCase("collect") &&
						!type.equalsIgnoreCase("destroy") &&
						!type.equalsIgnoreCase("damage") &&
						!type.equalsIgnoreCase("place") &&
						!type.equalsIgnoreCase("kill") &&
						!type.equalsIgnoreCase("kill_player") &&
						!type.equalsIgnoreCase("enchant") &&
						!type.equalsIgnoreCase("tame"))
				{
					Chat.logger("severe", "Sorry but the task type '"+ type + "' of quest '" + questName + "' is not yet supported! Disabling quest...");
					rturn = false;
				}
		}
		this.saveQuestConfig();
		if(!rturn)
			return false;
		else
			return true;
	}
	public void dumpQuestConfig()
	{
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "##################### Starting Config Dump #####################");
		Chat.logger("warning", "################################################################");
		
		for (String o : plugin.Config.getQuestConfig().getKeys(true))
			Chat.logger("info", o + ": " + plugin.Config.getQuestConfig().get(o));
		
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "###################### Ending Config Dump ######################");
		Chat.logger("warning", "################################################################");
	}
}