package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class Config {	
	// Configuration Files Variables
	private YamlConfiguration qConfig = null;
	private File qFile = null;
	private boolean qConfigIsNew = false;
	private YamlConfiguration cConfig = null;
	private File cFile = null;
	
	public Config()
	{
		this.loadConfigs();
		
		// Converts Old quests.yml
		if(this.getQuestConfig().getString("0.info.name") != null)
			LegacyConverter.convert();
		
		// Initialize The Configuration Files
		this.initializeConfig();
		this.initializeQuestConfig();
		
		// Debugging
		//this.dumpQuestConfig();
	}
	
	public void loadConfigs()
	{
		this.cFile = new File(qQuests.plugin.getDataFolder(), "config.yml");
		this.cConfig = loadConfig(this.cFile);
		
		this.qFile = new File(qQuests.plugin.getDataFolder(), "quests.yml");
		if(!this.qFile.exists())
			this.qConfigIsNew = true;
		this.qConfig = loadConfig(this.qFile);
	}
	
	// Getters
	public YamlConfiguration getConfig()
	{
		if (cConfig == null) {
			loadConfig(this.cFile);
		}
		return cConfig;
	}
	public YamlConfiguration getQuestConfig()
	{
		if (qConfig == null) {
			loadConfig(this.qFile);
		}
		return qConfig;
	}
	
	// Setters
	public void saveConfig()
	{
		this.saveConfig(this.cFile, this.cConfig);
	}
	public void saveQuestConfig()
	{
		this.saveConfig(this.qFile, this.qConfig);
	}
	
	// Starters
	public void initializeConfig() {
		this.getConfig();
		this.getConfig().options().copyDefaults(true);
		
		if(this.getConfig().getString("autoUpdate") != "true" &&
				this.getConfig().getString("autoUpdate") != "false")
			this.getConfig().set("autoUpdate", true);
		
		if(this.getConfig().getString("tellMeYourUsingMyPlugin") != "true" &&
				this.getConfig().getString("tellMeYourUsingMyPlugin") != "false")
			this.getConfig().set("tellMeYourUsingMyPlugin", true);
		
		if(this.getConfig().getString("primaryCommand") != null)
		{
			if(!this.getConfig().getString("primaryCommand").equalsIgnoreCase("q") &&
					!this.getConfig().getString("primaryCommand").equalsIgnoreCase("qu") &&
					!this.getConfig().getString("primaryCommand").equalsIgnoreCase("quest") &&
					!this.getConfig().getString("primaryCommand").equalsIgnoreCase("quests") &&
					!this.getConfig().getString("primaryCommand").equalsIgnoreCase("qquests"))
			{
				Chat.logger("warning", "Your primary command must be q, qu, quest, quests, or qquests, resetting to quest...");
				this.getConfig().set("primaryCommand", "quest");
			}
			else if(this.getConfig().getString("primaryCommand") == null)
				this.getConfig().set("primaryCommand", "quest");
		}
		else
			this.getConfig().set("primaryCommand", "quest");
		
		if(this.getConfig().getString("showItemIds") != "true" &&
				this.getConfig().getString("showItemIds") != "false")
			this.getConfig().set("showItemIds", true);
		
		if(this.getConfig().getString("info.showMoney") != "true" &&
				this.getConfig().getString("info.showMoney") != "false")
			this.getConfig().set("info.showMoney", true);
		
		if(this.getConfig().getString("info.moneyName") == null)
			this.getConfig().set("info.moneyName", "coins");
		
		if(this.getConfig().getString("info.showHealth") != "true" &&
				this.getConfig().getString("info.showHealth") != "false")
			this.getConfig().set("info.showHealth", true);
		
		if(this.getConfig().getString("info.showFood") != "true" &&
				this.getConfig().getString("info.showFood") != "false")
			this.getConfig().set("info.showFood", true);
		
		if(this.getConfig().getString("info.showCommands") != "true" &&
				this.getConfig().getString("info.showCommands") != "false")
			this.getConfig().set("info.showCommands", true);
		
		if(this.getConfig().getString("info.showItems") != "true" &&
				this.getConfig().getString("info.showItems") != "false")
			this.getConfig().set("info.showItems", true);
		
		if(this.getConfig().getString("info.showLevelsAdded") != "true" &&
				this.getConfig().getString("info.showLevelsAdded") != "false")
			this.getConfig().set("info.showLevelsAdded", true);
		
		if(this.getConfig().getString("info.showSetLevel") != "true" &&
				this.getConfig().getString("info.showSetLevel") != "false")
			this.getConfig().set("info.showSetLevel", true);
		
		Storage.autoUpdate = this.getConfig().getBoolean("autoUpdate");
		Storage.tellMeYourUsingMyPlugin = this.getConfig().getBoolean("tellMeYourUsingMyPlugin");
		Storage.primaryCommand = this.getConfig().getString("primaryCommand");
		Storage.showItemIds = this.getConfig().getBoolean("showItemIds");
		Storage.showMoney = this.getConfig().getBoolean("showMoney");
		Storage.moneyName = this.getConfig().getString("info.moneyName");
		Storage.showHealth = this.getConfig().getBoolean("info.showHealth");
		Storage.showFood = this.getConfig().getBoolean("info.showFood");
		Storage.showCommands = this.getConfig().getBoolean("info.showCommands");
		Storage.showItems = this.getConfig().getBoolean("info.showItems");
		Storage.showLevelsAdded = this.getConfig().getBoolean("info.showLevelsAdded");
		Storage.showSetLevel = this.getConfig().getBoolean("info.showSetLevel");
		
		this.saveConfig();
	}
	public void initializeQuestConfig() {
		this.getQuestConfig();
		if(this.qConfigIsNew) 
		{
			this.qConfigIsNew = false;
			this.getQuestConfig().options().copyDefaults(true);
			// Set Setup Nodes
				this.getQuestConfig().set("Diamonds!.setup.repeated", -1);
				this.getQuestConfig().set("Diamonds!.setup.invisible", false);
				this.getQuestConfig().set("Diamonds!.setup.delay", 1);
				this.getQuestConfig().set("Diamonds!.setup.nextQuest", "");
			
			// Set Requirements Nodes
				this.getQuestConfig().set("Diamonds!.requirements.levelMin", 0);
			
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
				this.getQuestConfig().set("Diamonds!.onDrop.message", "Aww… fine… I'll go find someone else :(");
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
	
	
	// Validator
	public boolean validate(String quest)
	{
		boolean rturn = true;
		
		// Setup
		if(this.getQuestConfig().getString(quest + ".setup.repeated") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".setup.repeated"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'setup.repeated' node of quest '" + quest + "' is not number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".setup.invisible") != null && this.getQuestConfig().getString(quest + ".setup.invisible") != "true" && this.getQuestConfig().getString(quest + ".setup.invisible") != "false")
		{
			Chat.attention(2);
			Chat.logger("severe", "The 'setup.invisible' node of quest '" + quest + "' is not a boolean statement [true/false]!");
			rturn = false;
		}
		if(this.getQuestConfig().getString(quest + ".setup.delay") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".setup.delay"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'setup.delay' node of quest '" + quest + "' is not a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".setup.nextQuest") != null)
		{
			
		}
		
		// Requirements
		if(this.getQuestConfig().getString(quest + ".requirements.levelMin") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".requirements.levelMin"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'requirements.levelMin' node of quest '" + quest + "' is not number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".requirements.levelMax") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".requirements.levelMax"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'requirements.levelMax' node of quest '" + quest + "' is not number!");
				rturn = false;
			}
		}
		
		// Tasks
		for(Object o : this.getQuestConfig().getConfigurationSection(quest + ".tasks").getKeys(false))
		{
			if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type") == null)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'tasks." + o + ".type' node of quest '" + quest + "' is not defined, adding default now!");
				this.getQuestConfig().set(quest + ".tasks." + o.toString() + ".type", "UNDEFINED");
				rturn = false;
			}
			else
				if(!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("collect") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("destroy") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("damage") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("place") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("kill") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("kill_player") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("enchant") &&
						!this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("tame"))
				{
					Chat.attention(2);
					Chat.logger("severe", "The 'tasks." + o + ".type' node of quest '" + quest + "' is not a valid task type!");
					rturn = false;
				}
			if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".id") != null)
			{
				if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("collect") ||
						this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("destroy") ||
						this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("damage") ||
						this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("place") ||
						this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("enchant"))
				{
					try
					{
						Integer.parseInt(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".id"));
					}
					catch(NumberFormatException e)
					{
						Chat.attention(2);
						Chat.logger("severe", "The 'tasks." + o.toString() + ".id' node of quest '" + quest + "', it needs to be a number for this task type, it is not number!");
						rturn = false;
					}
				}
				else if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("kill") ||
						this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".type").equalsIgnoreCase("tame"))
				{
					if(EntityType.fromName(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".id")) == null)
					{
						Chat.attention(2);
						Chat.logger("severe", "The 'tasks." + o.toString() + ".id' node of quest '" + quest + "', it needs to be a valid entity name for this task type, it is not a valid entity!");
						rturn = false;
					}
				}
			}
			else
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'tasks." + o.toString() + ".id' node of quest '" + quest + "' is not defined!");
				this.getQuestConfig().set(quest + ".tasks." + o.toString() + ".id", "UNDEFINED");
				rturn = false;
			}
			if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".display") == null)
			{
				Chat.logger("severe", "The 'tasks." + o.toString() + ".display' node of quest '" + quest + "', it needs to be defined!");
				this.getQuestConfig().set(quest + ".tasks." + o.toString() + ".display", "UNDEFINED");
				rturn = false;
			}
			if(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".amount") != null)
			{
				try
				{
					Integer.parseInt(this.getQuestConfig().getString(quest + ".tasks." + o.toString() + ".amount"));
				}
				catch(NumberFormatException e)
				{
					Chat.attention(2);
					Chat.logger("severe", "The 'tasks." + o.toString() + ".amount' node of quest '" + quest + "' is not number!");
					rturn = false;
				}
			}
			else
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'tasks." + o.toString() + ".amount' node of quest '" + quest + "' is not defined!");
				this.getQuestConfig().set(quest + ".tasks." + o.toString() + ".amount", "UNDEFINED");
				rturn = false;
			}
		}
		
		// onJoin
		if(this.getQuestConfig().getString(quest + ".onJoin.message") == null)
		{
			Chat.attention(2);
			Chat.logger("severe", "The 'onJoin.message' node of quest '" + quest + "' is not defined!");
			this.getQuestConfig().set(quest + ".onJoin.message", "UNDEFINED");
			rturn = false;
		}
		if(this.getQuestConfig().getString(quest + ".onJoin.market.money") != null)
		{
			try
			{
				Double.parseDouble(this.getQuestConfig().getString(quest + ".onJoin.market.money"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onJoin.market.money' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onJoin.market.health") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onJoin.market.health"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onJoin.market.health' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onJoin.market.hunger") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onJoin.market.hunger"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onJoin.market.hunger' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getStringList(quest + ".onJoin.market.items").isEmpty())
		{
			String[] strs = {""};
			for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(quest + ".onJoin.market.items")) {
				strs = s.split(" ");
				
				if(Material.matchMaterial(strs[0]) != null)
				{
					try
					{
						Integer.parseInt(strs[0]);
						Integer.parseInt(strs[1]);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onJoin' rewards/fees of '" + quest + "' are not correctly formatted! Disabling this quest...");
						continue;
					}
				}
				else
				{
					Chat.logger("severe", "The 'onJoin' rewards/fees of '" + quest + "' does not have valid material ids! Disabling this quest...");
					continue;
				}
			}
		}
		if(this.getQuestConfig().getString(quest + ".onJoin.market.levelAdd") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onJoin.market.levelAdd"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onJoin.market.levelAdd' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onJoin.market.levelSet") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onJoin.market.levelSet"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onJoin.market.levelSet' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		
		// onDrop
		if(this.getQuestConfig().getString(quest + ".onDrop.message") == null)
		{
			Chat.attention(2);
			Chat.logger("severe", "The 'onDrop.message' node of quest '" + quest + "' is not defined!");
			this.getQuestConfig().set(quest + ".onDrop.message", "UNDEFINED");
			rturn = false;
		}
		if(this.getQuestConfig().getString(quest + ".onDrop.market.money") != null)
		{
			try
			{
				Double.parseDouble(this.getQuestConfig().getString(quest + ".onDrop.market.money"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onDrop.market.money' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onDrop.market.health") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onDrop.market.health"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onDrop.market.health' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onDrop.market.hunger") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onDrop.market.hunger"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onDrop.market.hunger' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(!this.getQuestConfig().getStringList(quest + ".onDrop.market.items").isEmpty())
		{
			String[] strs = {""};
			for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(quest + ".onDrop.market.items")) {
				strs = s.split(" ");
				
				if(Material.matchMaterial(strs[0]) != null)
				{
					try
					{
						Integer.parseInt(strs[0]);
						Integer.parseInt(strs[1]);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onDrop' rewards/fees of '" + quest + "' are not correctly formatted! Disabling this quest...");
						continue;
					}
				}
				else
				{
					Chat.logger("severe", "The 'onDrop' rewards/fees of '" + quest + "' does not have valid material ids! Disabling this quest...");
					continue;
				}
			}
		}
		if(this.getQuestConfig().getString(quest + ".onDrop.market.levelAdd") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onDrop.market.levelAdd"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onDrop.market.levelAdd' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onDrop.market.levelSet") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onDrop.market.levelSet"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onDrop.market.levelSet' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		
		// onComplete
		if(this.getQuestConfig().getString(quest + ".onComplete.message") == null)
		{
			Chat.attention(2);
			Chat.logger("severe", "The 'onComplete.message' node of quest '" + quest + "' is not defined!");
			this.getQuestConfig().set(quest + ".onComplete.message", "UNDEFINED");
			rturn = false;
		}
		if(this.getQuestConfig().getString(quest + ".onComplete.market.money") != null)
		{
			try
			{
				Double.parseDouble(this.getQuestConfig().getString(quest + ".onComplete.market.money"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onComplete.market.money' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onComplete.market.health") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onComplete.market.health"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onComplete.market.health' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onComplete.market.hunger") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onComplete.market.hunger"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onComplete.market.hunger' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getStringList(quest + ".onComplete.market.items").isEmpty())
		{
			String[] strs = {""};
			for (String s : qQuests.plugin.Config.getQuestConfig().getStringList(quest + ".onComplete.market.items")) {
				strs = s.split(" ");
				
				if(Material.matchMaterial(strs[0]) != null)
				{
					try
					{
						Integer.parseInt(strs[0]);
						Integer.parseInt(strs[1]);
					}
					catch(Exception e)
					{
						Chat.logger("severe", "The 'onComplete' rewards/fees of '" + quest + "' are not correctly formatted! Disabling this quest...");
						continue;
					}
				}
				else
				{
					Chat.logger("severe", "The 'onComplete' rewards/fees of '" + quest + "' does not have valid material ids! Disabling this quest...");
					continue;
				}
			}
		}
		if(this.getQuestConfig().getString(quest + ".onComplete.market.levelAdd") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onComplete.market.levelAdd"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onComplete.market.levelAdd' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		if(this.getQuestConfig().getString(quest + ".onComplete.market.levelSet") != null)
		{
			try
			{
				Integer.parseInt(this.getQuestConfig().getString(quest + ".onComplete.market.levelSet"));
			}
			catch(NumberFormatException e)
			{
				Chat.attention(2);
				Chat.logger("severe", "The 'onComplete.market.levelSet' node of quest '" + quest + "', it needs to be a number!");
				rturn = false;
			}
		}
		
		// Return
		this.saveQuestConfig();
		return rturn;
	}
	
	// Base Functions
	private YamlConfiguration loadConfig(File file) 
	{
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
	      } catch (IOException ex) {
	    	  Chat.logger("severe", "Can't Create " + file.getName() + " File!");
	      }
		}
		
		return YamlConfiguration.loadConfiguration(file);
	}
	private void saveConfig(File file, YamlConfiguration config) 
	{
		try {
			config.save(file);
		} catch (IOException ex) {
			Chat.logger("severe", "Can't Write To File '" + file.getName() + "'!");
	    }
	}
	
	// Debugging Function
	public void dumpQuestConfig()
	{
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "##################### Starting Config Dump #####################");
		Chat.logger("warning", "################################################################");
		
		for (String o : this.getQuestConfig().getKeys(true))
			Chat.logger("info", o + ": " + this.getQuestConfig().get(o));
		
		Chat.logger("warning", "################################################################");
		Chat.logger("warning", "###################### Ending Config Dump ######################");
		Chat.logger("warning", "################################################################");
	}
}