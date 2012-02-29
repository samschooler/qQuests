package me.quaz3l.qQuests;

import java.util.logging.Logger;

import me.quaz3l.qQuests.API.QuestAPI;
import me.quaz3l.qQuests.Plugins.cmd;
import me.quaz3l.qQuests.Util.Config;
import me.quaz3l.qQuests.Util.qListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class qQuests extends JavaPlugin 
{
	// General Setup
	public static qQuests plugin;
	public final Logger logger = Logger.getLogger(("Minecraft"));
	public Config Config = new Config(this);
	public Integer qPlugins = 0;
	public QuestAPI qAPI;
	
	// Economy
	public Economy economy = null;
	
	// Prefixes
	public final String chatPrefix = ChatColor.AQUA + "[" + ChatColor.LIGHT_PURPLE + "qQuests" + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE + " ";
	public final String prefix = "[qQuests] ";
	
	
	public qQuests() {
		super();
		qQuests.plugin = this;
	}
	@Override
	public void onDisable() 
	{
		this.logger.info(this.prefix + "v" + this.getDescription().getVersion() + " by Quaz3l: Disabled");
	}

	@Override
	public void onEnable() 
	{
		this.qAPI = new QuestAPI();
		// Initialize The Configuration File
		Config.initialize();
		
		// Find Economy
		this.startEconomy();
		
		// Register Events
		getServer().getPluginManager().registerEvents(new qListener(this), this);
		
		// Build Quests
		qAPI.getQuestWorker().buildQuests();
		
		//Start Stock qPlugins
		stockPlugins();
		
		// Notify Logger
		this.logger.info(qQuests.plugin.prefix + this.qPlugins + " qPlugins Linked.");
		this.logger.info(this.prefix + "v" + this.getDescription().getVersion() + " by Quaz3l: Enabled");
	}
	// Hooks Into The Economy
	private void startEconomy()
	{
		if(getServer().getPluginManager().isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Economy> economyProvider =
					getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			if (economyProvider != null) {
				this.economy = economyProvider.getProvider();
				this.logger.info(this.prefix + "[Economy] Enabled - Vault And Economy Plugin Found.");
			}
			else
			{
				this.logger.warning(this.prefix + "****************************************************************");
				this.logger.warning(this.prefix + "[Economy] Disabled - Economy Plugin Not Found! Go Dowmnload An Economy Plugin From: http://dev.bukkit.org/server-mods/iconomy");
				this.logger.warning(this.prefix + "****************************************************************");
			}
		}
		else
		{
			this.logger.warning(this.prefix + "****************************************************************");
			this.logger.warning(this.prefix + "[Economy] Disabled - Vault Not Found! Go Download Vault From: http://dev.bukkit.org/server-mods/vault");
			this.logger.warning(this.prefix + "****************************************************************");
		}
	}
	// Starts The Stock Plugins
	private void stockPlugins()
	{
		qPluginCommands();
		//qPluginSigns();
		//qPluginNPCs();
	}
	// Stock Plugins
	private void qPluginCommands()
	{
		// Setup Command Executors
		CommandExecutor cmd = new cmd();
			getCommand("Q").setExecutor(cmd);
			getCommand("QUEST").setExecutor(cmd);
			getCommand("QUESTS").setExecutor(cmd);
			getCommand("qQUESTS").setExecutor(cmd);
	}
	
	// Streamline The Permissions
	public boolean checkPerms(Player p, String perm)
	{
		if(p.hasPermission("qquests." + perm))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
