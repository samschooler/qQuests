package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.quaz3l.qQuests.qQuests;

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
	    	plugin.logger.severe(plugin.prefix + "Could not save config to " + cConfigFile);
	    }
	}
	public void initialize() {
		if(this.getQuestConfig().getKeys(false).size() < 1) {
			this.getQuestConfig().options().copyDefaults(true);
			
			// Set Setup Nodes
			if(this.getQuestConfig().getString("Diamonds!.setup.messageStart") == null) 
				this.getQuestConfig().set("Diamonds!.setup.messageStart", "Hey! Can you go get my 5 diamonds! I'll pay you $500");
			if(this.getQuestConfig().getString("Diamonds!.setup.messageEnd") == null) 
				this.getQuestConfig().set("Diamonds!.setup.messageEnd", "Thanks! Now I can feed my lava dragon! ;)");
			if(this.getQuestConfig().getBoolean("Diamonds!.setup.tasksOrdered") == false) 
				this.getQuestConfig().set("Diamonds!.setup.tasksOrdered", false);
			if(this.getQuestConfig().getInt("Diamonds!.setup.repeated") == 0) 
				this.getQuestConfig().set("Diamonds!.setup.repeated", 0);
			if(this.getQuestConfig().getBoolean("Diamonds!.setup.invisible") == false) 
				this.getQuestConfig().set("Diamonds!.setup.invisible", false);
			if(this.getQuestConfig().getString("Diamonds!.setup.nextQuest") == null) 
				this.getQuestConfig().set("Diamonds!.setup.nextQuest", "");
			
			// Set Task Nodes
			if(this.getQuestConfig().getString("Diamonds!.tasks.0.type") == null) 
				this.getQuestConfig().set("Diamonds!.tasks.0.type", "collect");
			if(this.getQuestConfig().getInt("Diamonds!.tasks.0.id") == 0) 
				this.getQuestConfig().set("Diamonds!.tasks.0.id", 264);
			if(this.getQuestConfig().getString("Diamonds!.tasks.0.name") == null) 
				this.getQuestConfig().set("Diamonds!.tasks.0.name", "Diamonds");
			if(this.getQuestConfig().getInt("Diamonds!.tasks.0.amount") == 0) 
				this.getQuestConfig().set("Diamonds!.tasks.0.amount", 5);
			if(this.getQuestConfig().getString("Diamonds!.tasks.0.nextTask") == null) 
				this.getQuestConfig().set("Diamonds!.tasks.0.nextTask", "");
			
			// Set toJoin Nodes
			if(this.getQuestConfig().getInt("Diamonds!.market.toJoin.money") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toJoin.money", 0);
			if(this.getQuestConfig().getInt("Diamonds!.market.toJoin.health") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toJoin.health", 0);
			if(this.getQuestConfig().getInt("Diamonds!.market.toJoin.hunger") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toJoin.hunger", 0);
			
			// Set toDrop Nodes
			if(this.getQuestConfig().getInt("Diamonds!.market.toDrop.money") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toDrop.money", -50);
			if(this.getQuestConfig().getInt("Diamonds!.market.toDrop.health") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toDrop.health", 0);
			if(this.getQuestConfig().getInt("Diamonds!.market.toDrop.hunger") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toDrop.hunger", 0);
			
			// Set toComplete Nodes
			if(this.getQuestConfig().getInt("Diamonds!.market.toComplete.0.money") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toComplete.0.money", 500);
			if(this.getQuestConfig().getInt("Diamonds!.market.toComplete.0.health") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toComplete.0.health", 0);
			if(this.getQuestConfig().getInt("Diamonds!.market.toComplete.0.hunger") == 0) 
				this.getQuestConfig().set("Diamonds!.market.toComplete.0.hunger", 0);
			
        	this.saveQuestConfig();
		}
	}
	public void validate(String questName) {
		if(this.getQuestConfig().getString(questName + ".setup.messageStart") == null) 
			this.getQuestConfig().set(questName + ".setup.messageStart", "Hey! Can you go get my 5 diamonds! I'll pay you $500");
		
		if(this.getQuestConfig().getString(questName + ".setup.messageEnd") == null)
			this.getQuestConfig().set(questName + ".setup.messageEnd", "End Message");
		
		if(this.getQuestConfig().getBoolean(questName + ".setup.tasksOrdered") == false)
			this.getQuestConfig().set(questName + ".setup.tasksOrdered", false);
		
		if(this.getQuestConfig().getInt(questName + ".setup.repeated") == 0)
			this.getQuestConfig().set(questName + ".setup.repeated", 0);
		
		if(this.getQuestConfig().getBoolean(questName + ".setup.invisible") == false)
			this.getQuestConfig().set(questName + ".setup.invisible", false);
		
		if(this.getQuestConfig().getString(questName + ".setup.nextQuest") == null)
			this.getQuestConfig().set(questName + ".setup.nextQuest", "");
		
		this.saveQuestConfig();
	}
}