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
	private QuestWorker QuestWorker = new QuestWorker();
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
		// Get The Configuration Files
	    this.getConfig();
		this.getQuestConfig();
				
		// Saves Configuration Files
		this.saveConfig();
		this.saveQuestConfig();
		
		// Build Quests
		getQuestWorker().buildQuests();
		this.logger.info(this.prefix + getQuestWorker().getQuests().get("d").name());
		
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
	        if(qConfigFile.exists()) {
	        	qConfig.setDefaults(defConfig);
	        	qConfig.options().copyDefaults(true);
	        }
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
