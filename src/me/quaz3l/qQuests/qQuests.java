package me.quaz3l.qQuests;

import me.quaz3l.qQuests.Util.cmd_qQuests;
import me.quaz3l.qQuests.listeners.bListener;
import me.quaz3l.qQuests.listeners.eListener;
import net.milkbowl.vault.economy.Economy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class qQuests extends JavaPlugin 
{
	public static qQuests plugin;
	private cmd_qQuests cmdExe;
	
	public Map<Player, Object> currentQuests = new HashMap<Player, Object>();
	public Map<Player, Integer> doneItems = new HashMap<Player, Integer>();
		
	public final Logger logger = Logger.getLogger(("Minecraft"));
	
	public static Economy economy = null;
	
	private FileConfiguration qConfig = null;
	private File qConfigFile = null;
	
	private final bListener blockListener = new bListener(this);
	private final eListener entityListener = new eListener(this);

	@Override
	public void onDisable() 
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info( "[" + getDescription().getName() + "] Version " + pdfFile.getVersion() + " by Quaz3l: Disabled");
	}

	@Override
	public void onEnable() 
	{
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info( "[" + pdfFile.getName() + "] Version " + pdfFile.getVersion() + " by Quaz3l: Enabling");
		
		// Checks for a quests.yml with contents if none exists creates one with default quests
		if(this.getQuestConfig().getString("version") == null) {
			this.getConfig().options().copyDefaults(true);
		}
		if(this.getQuestConfig().getString("0.info.name") == null) {
			this.getQuestConfig().options().copyDefaults(true);
		}
		
		// Saves Configuration Files
		this.saveConfig();
		this.saveQuestConfig();
		
		// Register Events
		PluginManager pm = getServer().getPluginManager();
			// Block Events
			pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
			pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
		
			// Entity Events
			pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
		
		// Setup Command Executors
		cmdExe = new cmd_qQuests(this);
			getCommand("Q").setExecutor(cmdExe);
			getCommand("QUEST").setExecutor(cmdExe);
			getCommand("QUESTS").setExecutor(cmdExe);
			getCommand("qQUESTS").setExecutor(cmdExe);
		
		// ***TODO*** Setup Economy
		//Setup Economy
		
		// Notify The Console
		this.logger.info( "[" + pdfFile.getName() + "] Version " + pdfFile.getVersion() + " by Quaz3l: Enabled");
	}
	 
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
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + qConfigFile, ex);
	    }
	}
	
	public void endQuest(Player player, String type) {
		int healAmount;
		int aHealth;
		int cHealth;
		int feedAmount;
		int aHunger;
		int cHunger;
		
		if(type == "join")
		{
			//***TODO*** Add Items
			//Fee: Items
			
			//***TODO*** Reward money
			// Fee: Money

			
			// Reward: Health
			cHealth = player.getHealth();
			aHealth = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.fee.toJoin.health");
			healAmount = (cHealth + aHealth);
			if(healAmount > 20) {
				player.setHealth(20);
			}
			else if(healAmount < 0)
			{
				player.setHealth(0);
			}
			else
			{
				player.setHealth(healAmount);
			}
			
			// Fee: Hunger
			cHunger = player.getFoodLevel();
			aHunger = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.fee.toJoin.hunger");
			feedAmount = (cHunger + aHunger);
			if(feedAmount > 20) {
				player.setFoodLevel(20);
			}
			else if(feedAmount < 0)
			{
				player.setFoodLevel(0);
			}
			else
			{
				player.setFoodLevel(feedAmount);
			}
		}
		else if(type == "drop") {
			//***TODO*** Add Items
			//Fee: Items
			
			//***TODO*** Reward money
			// Fee: Money

			
			// Fee: Health
			cHealth = player.getHealth();
			aHealth = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.fee.toDrop.health");
			healAmount = (cHealth + aHealth);
			if(healAmount > 20) {
				player.setHealth(20);
			}
			else if(healAmount < 0)
			{
				player.setHealth(0);
			}
			else
			{
				player.setHealth(healAmount);
			}
			
			// Fee: Hunger
			cHunger = player.getFoodLevel();
			aHunger = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.fee.toDrop.hunger");
			feedAmount = (cHunger + aHunger);
			if(feedAmount > 20) {
				player.setFoodLevel(20);
			}
			else if(feedAmount < 0)
			{
				player.setFoodLevel(0);
			}
			else
			{
				player.setFoodLevel(feedAmount);
			}
						
			
			// Reset Players Quest Data
			this.doneItems.put(player, null);
			this.currentQuests.put(player, null);
			// Notify Player
			player.sendMessage(ChatColor.LIGHT_PURPLE + "Quest Dropped!");
		}
		else if(type == "done")
		{
			//***TODO*** Add Items
			//***FIX*** List<String> Throws a null error
			// Reward: Items
			/*
			List<String> items = plugin.getQuestConfig().getStringList((String) plugin.currentQuests.get("0.market.reward.items"));

			if(items != null && items.size() > 0) // always check against null if it's even the slightest possibility of beeing null
			{
			    for(String itemStr : items) // iterate, don't use indexes and .get() for loops !
			    {
			    	PlayerInventory inventory = player.getInventory();
					String[] cItem = itemStr.split(":");
					Integer howMuch = Integer.parseInt(cItem[1].trim());
					Integer whatToGive = Integer.parseInt(cItem[0].trim());
					ItemStack istack = new ItemStack(whatToGive, howMuch);
					inventory.addItem(istack);
			    }
			}
			*/
			//***TODO*** Reward money
			// Reward: Money
			
			// Reward: Health
			cHealth = player.getHealth();
			aHealth = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.reward.health");
			healAmount = (cHealth + aHealth);
			if(healAmount > 20) {
				player.setHealth(20);
			}
			else if(healAmount < 0)
			{
				player.setHealth(0);
			}
			else
			{
				player.setHealth(healAmount);
			}
			
			// Reward: Hunger
			cHunger = player.getFoodLevel();
			aHunger = this.getQuestConfig().getInt(this.currentQuests.get(player) + ".market.reward.hunger");
			feedAmount = (cHunger + aHunger);
			if(feedAmount > 20) {
				player.setFoodLevel(20);
			}
			else if(feedAmount < 0)
			{
				player.setFoodLevel(0);
			}
			else
			{
				player.setFoodLevel(feedAmount);
			}
			
			
			// Reset Players Quest Data
			this.doneItems.put(player, null);
			this.currentQuests.put(player, null);
			
			// Notify Player
			player.sendMessage(ChatColor.GREEN + "Quest Done!");
		}
	}
	
	public String isEntityType(Entity entity)
    {
        if (entity instanceof Player) return "Player";
        if(entity instanceof Sheep) return "Sheep";
        if(entity instanceof Cow) return "Cow";
        if(entity instanceof Pig) return "Pig";
        if(entity instanceof Creeper) return "Creeper";
        if(entity instanceof PigZombie) return "PigZombie";
        if(entity instanceof Skeleton)return "Skeleton";
        if(entity instanceof Spider) return "Spider";
        if(entity instanceof Squid) return "Squid";
        if(entity instanceof Zombie) return "Zombie";
        if(entity instanceof Ghast) return "Ghast";
        if(entity instanceof Slime) return "Slime";
        if(entity instanceof Giant) return "Giant";
        if(entity instanceof Blaze) return "Blaze";
        if(entity instanceof CaveSpider) return "CaveSpider";
        if(entity instanceof Chicken) return "Chicken";
        if(entity instanceof Enderman) return "Enderman";
        if(entity instanceof MagmaCube) return "MagmaCube";
        if(entity instanceof MushroomCow) return "MushroomCow";
        if(entity instanceof Snowman) return "Snowman";
        if(entity instanceof Wolf) return "Wolf";
        return null;
    }
	
	private Boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
}