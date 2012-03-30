package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerProfiles {
	private FileConfiguration pConfig = null;
	private File pConfigFile = null;
	
	public void initializePlayerProfiles() {
		this.getPlayerProfiles();
		this.getPlayerProfiles().options().copyDefaults(true);
		this.savePlayerProfiles();
	}
	public FileConfiguration getPlayerProfiles() {
		if (pConfig == null) {
			reloadPlayerProfiles();
		}
		return pConfig;
	}
	public void reloadPlayerProfiles() {
		if (pConfigFile == null) 
		{
			pConfigFile = new File(qQuests.plugin.getDataFolder(), "profiles.yml");
		}
		pConfig = YamlConfiguration.loadConfiguration(pConfigFile);
		
		InputStream defConfigStream = qQuests.plugin.getResource("profiles.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			pConfig.setDefaults(defConfig);
		}
	}
	public void savePlayerProfiles() {
		if (pConfig == null || pConfigFile == null) {
			return;
		}
		try {
			pConfig.save(pConfigFile);
		} catch (IOException ex) {
			Chat.logger("severe", qQuests.plugin.prefix + "Could not save config to " + pConfigFile);
		}
	}
	
	// Functions
	public Integer getQuestsTimesCompleted(Player p, Quest q) {
		return getInt(p, "FinishCount." + q.name());
	}
	
	// Set Values
	public void set(Player player, String path, String value)
	{
		this.getPlayerProfiles().set(player.getName() + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(Player player, String path, Integer value)
	{
		this.getPlayerProfiles().set(player.getName() + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(Player player, String path, boolean value)
	{
		this.getPlayerProfiles().set(player.getName() + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(Player player, String path, @SuppressWarnings("rawtypes") List value)
	{
		this.getPlayerProfiles().set(player.getName() + "." + path, value);
		this.savePlayerProfiles();
	}
	
	
	// Get Values
	public String getString(Player player, String path)
	{
		return this.getPlayerProfiles().getString(player.getName() + "." + path);
	}
	public Integer getInt(Player player, String path)
	{
		return this.getPlayerProfiles().getInt(player.getName() + "." + path);
	}
	public boolean getBoolean(Player player, String path)
	{
		return this.getPlayerProfiles().getBoolean(player.getName() + "." + path);
	}
	@SuppressWarnings("rawtypes")
	public List getList(Player player, String path)
	{
		return this.getPlayerProfiles().getList(player.getName() + "." + path);
	}
	public List<String> getStringList(Player player, String path)
	{
		return this.getPlayerProfiles().getStringList(player.getName() + "." + path);
	}
	public List<Integer> getIntegerList(Player player, String path)
	{
		return this.getPlayerProfiles().getIntegerList(player.getName() + "." + path);
	}
}

