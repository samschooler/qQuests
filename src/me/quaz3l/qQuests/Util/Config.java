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
	// Configuration Files Variables
	private YamlConfiguration quests = null;
	private File qFile = null;
	private YamlConfiguration profiles = null;
	private File pFile = null;
	private YamlConfiguration config = null;
	private File cFile = null;
	
	public Config()
	{
		loadConfigs();
	}
	
	public void loadConfigs()
	{
		this.cFile = new File(qQuests.plugin.getDataFolder(), "config.yml");
	    this.config = loadConfig(this.cFile);
	    this.qFile = new File(qQuests.plugin.getDataFolder(), "quests.yml");
	    this.quests = loadConfig(this.qFile);
	    this.pFile = new File(qQuests.plugin.getDataFolder(), "profiles.yml");
	    this.quests = loadConfig(this.pFile);
	}
	private YamlConfiguration loadConfig(File file) {
		if (!file.exists()) {
			InputStream defConfigStream = qQuests.plugin.getResource(file.getName());
			if (defConfigStream != null) {
				YamlConfiguration customConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				try {
					customConfig.save(file);
				} catch (IOException ex) {
					Chat.logger("severe", "Can't Save " + file.getName() + " File!");
				}
				return customConfig;
			}
			try
			{
				file.createNewFile();
				Chat.logger("info", "New " + file.getName() + " File Created");
			} catch (IOException ex) {
				Chat.logger("severe", "Can't Create " + file.getName() + " File!");
			}
		}
		
		return YamlConfiguration.loadConfiguration(file);
	}
	private void saveConfig(File file, YamlConfiguration config) {
		try {
			config.save(file);
		} catch (IOException ex) {
			Chat.logger("severe", "Can't Write To File '" + file.getName() + "'!");
		}
	}
	
	// Configuration Functions
	public YamlConfiguration getConfig() {
		return config;
	}
	// Quest Configuration Functions
	public FileConfiguration getQuestConfig() {
		return quests;
	}
	public FileConfiguration getProfilesConfig() {
		return profiles;
	}
	
	
	public void initializeConfig() {
		this.getConfig();
		this.getConfig().options().copyDefaults(true);
		
		if(this.getConfig().getString("autoUpdate") != "true" &&
				this.getConfig().getString("autoUpdate") != "false")
			this.getConfig().set("autoUpdate", true);
		
		if(this.getConfig().getString("tellMeYourUsingMyPlugin") != "true" &&
				this.getConfig().getString("tellMeYourUsingMyPlugin") != "false")
			this.getConfig().set("tellMeYourUsingMyPlugin", true);
		
		if(!this.getConfig().getString("primaryCommand").equalsIgnoreCase("q") &&
				!this.getConfig().getString("primaryCommand").equalsIgnoreCase("qu") &&
				!this.getConfig().getString("primaryCommand").equalsIgnoreCase("quest") &&
				!this.getConfig().getString("primaryCommand").equalsIgnoreCase("quests") &&
				!this.getConfig().getString("primaryCommand").equalsIgnoreCase("qquests") &&
				this.getConfig().getString("primaryCommand") != null)
		{
			Chat.logger("warning", "Your primary command must be q, qu, quest, quests, or qquests, resetting to quest...");
			this.getConfig().set("primaryCommand", "quest");
		}
		else if(this.getConfig().getString("primaryCommand") == null)
				this.getConfig().set("primaryCommand", "quest");
		
		if(this.getConfig().getString("showMoreInfo") != "true" &&
				this.getConfig().getString("showMoreInfo") != "false")
			this.getConfig().set("showMoreInfo", true);
		
		Storage.autoUpdate = this.getConfig().getBoolean("autoUpdate");
		Storage.tellMeYourUsingMyPlugin = this.getConfig().getBoolean("tellMeYourUsingMyPlugin");
		Storage.primaryCommand = this.getConfig().getString("primaryCommand");
		Storage.showMoreInfo = this.getConfig().getBoolean("showMoreInfo");
		
		this.saveConfig(this.cFile, this.config);
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
			
			// Set Requirements Nodes
				this.getQuestConfig().set("Diamonds!.requirements.level", 0);
			
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
				this.getQuestConfig().set("Diamonds!.onDrop.market.levelSet", 0);
			
			// Set onComplete Nodes
				this.getQuestConfig().set("Diamonds!.onComplete.message", "Thanks! Now I can feed my lava dragon! ;)");
				this.getQuestConfig().set("Diamonds!.onComplete.market.money", 500);
				this.getQuestConfig().set("Diamonds!.onComplete.market.health", 0);
				this.getQuestConfig().set("Diamonds!.onComplete.market.hunger", 0);
				this.getQuestConfig().set("Diamonds!.onComplete.market.levelAdd", 1);
				this.getQuestConfig().set("Diamonds!.onComplete.market.items", Arrays.asList("3 1", "4 5"));
		}
		this.saveConfig(this.qFile, this.quests);
	}
	
	// Validate The Quest Configuration
	public boolean validate(String questName, QuestWorker q) {
		Integer tRoot = 0;
		boolean rturn = true;
		
		if(this.getQuestConfig().getConfigurationSection(questName + ".tasks") == null)
		{
			Chat.logger("severe", "Quest " + questName + " disabled because the 'tasks' node does not exist!");
			return false;
		}
		if(this.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false).size() < 1)
		{
			Chat.logger("severe", "Quest " + questName + " disabled because the 'tasks' node has no tasks!");
			return false;
		}
		
		for (Object taskNo : this.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
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
			if(this.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount") <= 0) 
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
		if(this.getQuestConfig().getInt(questName + ".setup.repeated") == 0 && this.getQuestConfig().getString(questName + ".setup.repeated") != null)
		{
			Chat.logger("severe", "The 'setup.repeated' should not be a string! Disabling this quest...");
			rturn = false;
		}
		if(this.getQuestConfig().getString(questName + ".setup.invisible") != "true" && this.getQuestConfig().getString(questName + ".setup.invisible") != "false" && this.getQuestConfig().getString(questName + ".setup.invisible") != null)
		{
			Chat.logger("severe", "The 'setup.invisible' should not be a string, choose true or false! Disabling this quest...");
			rturn = false;
		}
		if(this.getQuestConfig().getInt(questName + ".setup.delay") == 0 && this.getQuestConfig().getString(questName + ".setup.delay") != null)
		{
			Chat.logger("severe", "The 'setup.delay' should not be a string! Disabling this quest...");
			rturn = false;
		}
		*/
		
		for (Object taskNo : this.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
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
		this.saveConfig(this.qFile, this.quests);
		if(!rturn)
			return false;
		else
			return true;
	}
	
	// For Debugging
	public void dumpQuestConfig()
	{
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "##################### Starting Config Dump #####################");
		Chat.logger("warning", "################################################################");
		
		for (String o :this.getQuestConfig().getKeys(true))
			Chat.logger("info", o + ": " + this.getQuestConfig().get(o));
		
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "###################### Ending Config Dump ######################");
		Chat.logger("warning", "################################################################");
	}
}