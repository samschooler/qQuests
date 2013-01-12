package me.quaz3l.qQuests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import me.quaz3l.qQuests.API.QuestAPI;
import me.quaz3l.qQuests.API.TaskTypes.Damage;
import me.quaz3l.qQuests.API.TaskTypes.Destroy;
import me.quaz3l.qQuests.API.TaskTypes.Distance;
import me.quaz3l.qQuests.API.TaskTypes.Enchant;
import me.quaz3l.qQuests.API.TaskTypes.GoTo;
import me.quaz3l.qQuests.API.TaskTypes.Kill;
import me.quaz3l.qQuests.API.TaskTypes.Kill_Player;
import me.quaz3l.qQuests.API.TaskTypes.Place;
import me.quaz3l.qQuests.API.TaskTypes.Tame;
import me.quaz3l.qQuests.Plugins.Commands;
import me.quaz3l.qQuests.Plugins.Signs;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Metrics;
import me.quaz3l.qQuests.Util.Updater;
import me.quaz3l.qQuests.Util.Store.Config;
import me.quaz3l.qQuests.Util.Store.PersistenceBean;
import me.quaz3l.qQuests.Util.Store.PersistenceDatabase;
import me.quaz3l.qQuests.Util.Store.Storage;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class qQuests extends JavaPlugin
{
	// General Setup
	public static qQuests plugin;
	public final Logger logger = Logger.getLogger(("Minecraft"));
	public Config Config;
	public QuestAPI qAPI;
	public PersistenceDatabase persistence;

	// SHOULD BE FALSE
	public boolean debug = true;

	// Services
	public Economy economy = null;
	public Permission permission = null;

	// Prefixes
	public String chatPrefix = ChatColor.AQUA + "[" + ChatColor.LIGHT_PURPLE + Storage.prefix + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE + " ";
	public String prefix = "[qQuests] ";	

	// Super Variable
	public qQuests() {
		super();
		qQuests.plugin = this;
	}

	@Override
	public void onDisable() 
	{
		Chat.logger("debug", this.persistence.get("test"));
		// To fix delays
		getServer().getScheduler().cancelTasks(plugin);
		
		// Save data, and shutdown the database
		this.persistence.shutdown();
		
		// Free the memory
		this.qAPI = null;
		this.Config = null;
		this.persistence = null;

		Chat.logger("info", "v" + this.getDescription().getVersion() + " by Quaz3l: Disabled");
	}

	@Override
	public void onEnable() 
	{
		// Setup Configuration
		this.Config = new Config();
		
		// Setup persistence database
		this.persistence = new PersistenceDatabase();

		// Setup Economy
		this.setupEconomy();

		// Setup Permissions
		this.setupPermissions();

		// Setup Quest Types
		this.setupTaskTypes();

		// Get The API
		this.qAPI = new QuestAPI();

		// Setup Player Profiles
		this.qAPI.getProfiles().initializePlayerProfiles();

		// Build Quests
		this.qAPI.getQuestWorker().buildQuests();

		//Setup Stock qPlugins
		this.setupStockPlugins();

		// Check for updates
		Updater.start(this, "qquests", this.getFile());

		// http://mcstats.org/qQuests
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

		// Load the current quest data
		//Storage.loadPersisted();
		
		this.persistence.set("test", "hello world");
		

		// Start persister of data
		//Storage.rePersist();

		// Notify Logger
		Chat.logger("info", "by Quaz3l: Enabled");
	}

	// Hooks Into The Economy Plugin
	private void setupEconomy()
	{
		if(getServer().getPluginManager().isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Economy> economyProvider =
					getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			if (economyProvider != null) {
				this.economy = economyProvider.getProvider();
				Chat.logger("info", "[Economy] Enabled - Vault And Economy Plugin Found.");
			}
			else
			{
				Chat.logger("warning", "[Economy] Disabled - Economy Plugin Not Found! Go Dowmnload An Economy Plugin From: http://plugins.bukkit.org/curseforge");
			}
		}
		else
		{
			Chat.logger("warning", "[Economy] Disabled - Vault Not Found! Go Download Vault From: http://dev.bukkit.org/server-mods/vault");
		}
	}
	// Hook Into The Permissions Plugin
	private void setupPermissions()
	{
		if(getServer().getPluginManager().isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Permission> permissionProvider =
					getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if (permissionProvider != null) {
				this.permission = permissionProvider.getProvider();
				if(permission.getName().equalsIgnoreCase("SuperPerms")) {
					Chat.logger("info", "[Permissions] Enabled - Vault Found, But No Perms Plugin Found, Using Op/Non-Op.");
				} else {
					Chat.logger("info", "[Permissions] Enabled - Vault And Permissions Plugin Found.");
				}
			}
			else
			{
				Chat.logger("warning", "[Permissions] Disabled - Permissions Plugin Not Found! Go Dowmnload A Permissions Plugin From: http://dev.bukkit.org/server-mods/bpermissions");
			}
		}
		else
		{
			Chat.logger("warning", "[Permissions] Disabled - Vault Not Found! Go Download Vault From: http://dev.bukkit.org/server-mods/vault");
		}
	}
	// Setup Task Types
	private void setupTaskTypes()
	{
		// Collect Is Handled By The Command
		getServer().getPluginManager().registerEvents(new Damage(), this);
		getServer().getPluginManager().registerEvents(new Destroy(), this);
		getServer().getPluginManager().registerEvents(new Place(), this);

		getServer().getPluginManager().registerEvents(new Distance(), this);
		getServer().getPluginManager().registerEvents(new GoTo(), this);

		getServer().getPluginManager().registerEvents(new Kill_Player(), this);
		getServer().getPluginManager().registerEvents(new Kill(), this);
		getServer().getPluginManager().registerEvents(new Tame(), this);

		getServer().getPluginManager().registerEvents(new Enchant(), this);

		Chat.logger("debug", "Listeners Registered.");
	}

	// Starts The Stock Plugins
	private void setupStockPlugins()
	{
		qPluginCommands();
		qPluginSigns();
		//qPluginNPCs();
	}
	// Stock Plugins
	private void qPluginCommands()
	{
		// Setup Command Executors
		CommandExecutor cmd = new Commands();
		getCommand("qQUESTS").setExecutor(cmd);
	}
	private void qPluginSigns()
	{
		// Setup Signs
		getServer().getPluginManager().registerEvents(new Signs(), this);
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses() {
		// register bean
		List<Class<?>> classes = new LinkedList<Class<?>>();
		
		// add beans
		classes.add(PersistenceBean.class);
		
		return classes;
	}
	
	// To connect to qQuests put this function in your plugin;
	// And "depend: [qQuests]" in your plugin.yml
	/*
	public qQuests qQuests = null;
	public QuestAPI qAPI = null;

	public void setupQQuests()
	  {
	    this.qQuests = (qQuests) getServer().getPluginManager().getPlugin("qQuests");

	    if (this.qAPI == null)
	      if (this.qQuests != null) {
	        this.qAPI = qQuests.qAPI;
	        System.out.println("qQuests found!");
	      } else {
	        System.out.println("qQuests not found. Disabling plugin.");
	        getServer().getPluginManager().disablePlugin(this);
	      }
	  }
	 */
}
