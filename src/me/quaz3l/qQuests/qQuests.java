package me.quaz3l.qQuests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.quaz3l.qQuests.API.Quest.QuestWorker;
import me.quaz3l.qQuests.Plugins.cmd;
import me.quaz3l.qQuests.Util.Econ;
import me.quaz3l.qQuests.Util.qListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class qQuests extends JavaPlugin 
{
	// General Setup
	public static qQuests plugin;
	public final Logger logger = Logger.getLogger(("Minecraft"));
	public final String chatPrefix = ChatColor.AQUA + "[" + ChatColor.LIGHT_PURPLE + "qQuests" + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE + " ";
	public final String prefix = "[qQuests] ";
	private QuestWorker QuestWorker = new QuestWorker(this);
	private Econ Econ = new Econ();
	
	// If the economy is enabled
	public boolean econEnabled = false;
	
	// Configuration Files Variables
	private FileConfiguration qConfig = null;
	private File qConfigFile = null;
	private FileConfiguration cConfig = null;
	private File cConfigFile = null;
	
	@Override
	public void onDisable() 
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(this.prefix + " Version " + pdfFile.getVersion() + " by Quaz3l: Disabled");
	}

	@Override
	public void onEnable() 
	{
		// API
			// Get The Configuration File
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
			
			// Build Quests
			getQuestWorker().buildQuests();
			//this.logger.info(this.prefix + this.getQuestConfig().getConfigurationSection("d.market.toJoin"));
			
			// Find Economy
			if(getServer().getPluginManager().isPluginEnabled("Vault")) {
				RegisteredServiceProvider<Economy> economyProvider =
						getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
				if (economyProvider != null) {
					Econ.economy = economyProvider.getProvider();
					this.logger.info(this.prefix + "Vault And Economy Plugin Found, All Economic Interactions Have Been Enabled!");
					econEnabled = true;
				}
				else
				{
					this.logger.warning(this.prefix + "****************************************************************");
					this.logger.warning(this.prefix + "Economy Not Found! All Economic Interactions Have Been Disabled!");
					this.logger.warning(this.prefix + "****************************************************************");
					econEnabled = false;
				}
			}
			else
			{
				this.logger.warning(this.prefix + "****************************************************************");
				this.logger.warning(this.prefix + "Vault Not Found! All Economic Interactions Have Been Disabled!");
				this.logger.warning(this.prefix + "****************************************************************");
		    	econEnabled = false;
			}
			
			// Events
			getServer().getPluginManager().registerEvents(new qListener(this), this);
		// End API
			
			
		// Plugin: Commands 
			// Setup Command Executors
			CommandExecutor cmd = new cmd(this);
				getCommand("Q").setExecutor(cmd);
				getCommand("QUEST").setExecutor(cmd);
				getCommand("QUESTS").setExecutor(cmd);
				getCommand("qQUESTS").setExecutor(cmd);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(this.prefix + " Version " + pdfFile.getVersion() + " by Quaz3l: Enabled");
	}
	
	// Returns The QuestWorker
	public QuestWorker getQuestWorker() 
	{
		return QuestWorker;
	}
	public static qQuests getPlugin()
	{
		return plugin;
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
		    qConfigFile = new File(getDataFolder(), "quests.yml");
		}
	    qConfig = YamlConfiguration.loadConfiguration(qConfigFile);
	 
	    InputStream defConfigStream = getResource("quests.yml");
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
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, this.prefix + "Could not save config to " + qConfigFile, ex);
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
		    cConfigFile = new File(getDataFolder(), "config.yml");
		}
	    cConfig = YamlConfiguration.loadConfiguration(cConfigFile);
	    
	    InputStream defConfigStream = getResource("config.yml");
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
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, this.prefix + "Could not save config to " + cConfigFile, ex);
	    }
	}
}
