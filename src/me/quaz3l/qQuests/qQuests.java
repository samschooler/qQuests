package me.quaz3l.qQuests;

import java.util.logging.Logger;

import me.quaz3l.qQuests.API.Quest.QuestWorker;
import me.quaz3l.qQuests.Plugins.cmd;
import me.quaz3l.qQuests.Util.Config;
import me.quaz3l.qQuests.Util.qListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class qQuests extends JavaPlugin 
{
	// General Setup
	public static qQuests plugin;
	public final Logger logger = Logger.getLogger(("Minecraft"));
	public Config Config = new Config(this);
	private QuestWorker QuestWorker = new QuestWorker(this);
	
	// Economy
	public Economy economy = null;
	
	// Prefixes
	public final String chatPrefix = ChatColor.AQUA + "[" + ChatColor.LIGHT_PURPLE + "qQuests" + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE + " ";
	public final String prefix = "[qQuests] ";
	
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
			// Initialize The Configuration File
			Config.initialize();
			
			// Build Quests
			getQuestWorker().buildQuests();
			//this.logger.info(this.prefix + Config.getQuestConfig().getConfigurationSection("d.market.toJoin"));
			
			// Find Economy
			if(getServer().getPluginManager().isPluginEnabled("Vault")) {
				RegisteredServiceProvider<Economy> economyProvider =
						getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
				if (economyProvider != null) {
					this.economy = economyProvider.getProvider();
					this.logger.info(this.prefix + "Vault And Economy Plugin Found, All Economic Interactions Have Been Enabled!");
				}
				else
				{
					this.logger.warning(this.prefix + "****************************************************************");
					this.logger.warning(this.prefix + "Economy Not Found! All Economic Interactions Have Been Disabled!");
					this.logger.warning(this.prefix + "****************************************************************");
				}
			}
			else
			{
				this.logger.warning(this.prefix + "****************************************************************");
				this.logger.warning(this.prefix + "Vault Not Found! All Economic Interactions Have Been Disabled!");
				this.logger.warning(this.prefix + "****************************************************************");
			}
			
			// Events
			getServer().getPluginManager().registerEvents(new qListener(this), this);
		// End API
			
			
		// Stock Plugins TODO
			pluginCommands();
			//pluginSigns();
			//pluginNPCs();
		
		// Notify Logger
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(this.prefix + " Version " + pdfFile.getVersion() + " by Quaz3l: Enabled");
	}
	// The Plugin
	public static qQuests getPlugin()
	{
		return plugin;
	}
	// QuestWorker
	public QuestWorker getQuestWorker() 
	{
		return QuestWorker;
	}
	
	
	// Stock Plugins
	private void pluginCommands()
	{
		// Setup Command Executors
		CommandExecutor cmd = new cmd(this);
			getCommand("Q").setExecutor(cmd);
			getCommand("QUEST").setExecutor(cmd);
			getCommand("QUESTS").setExecutor(cmd);
			getCommand("qQUESTS").setExecutor(cmd);
	}
}
