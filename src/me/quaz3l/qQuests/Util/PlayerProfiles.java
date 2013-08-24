package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
	public Integer getQuestsTimesCompleted(String player, Quest quest) {
		return getInt(player, "FinishCount." + quest.name());
	}
	
	// Set Values
	public void set(String player, String path, String value)
	{
		this.getPlayerProfiles().set(player + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(String player, String path, Integer value)
	{
		this.getPlayerProfiles().set(player + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(String player, String path, boolean value)
	{
		this.getPlayerProfiles().set(player + "." + path, value);
		this.savePlayerProfiles();
	}
	public void set(String player, String path, @SuppressWarnings("rawtypes") List value)
	{
		this.getPlayerProfiles().set(player + "." + path, value);
		this.savePlayerProfiles();
	}
	public void clear(String player)
	{
		this.getPlayerProfiles().set(player, null);
		this.savePlayerProfiles();
	}
	
	
	// Get Values
	public String getString(String player, String path)
	{
		return this.getPlayerProfiles().getString(player + "." + path);
	}
	public Integer getInt(String player, String path)
	{
		return this.getPlayerProfiles().getInt(player + "." + path);
	}
	public boolean getBoolean(String player, String path)
	{
		return this.getPlayerProfiles().getBoolean(player + "." + path);
	}
	@SuppressWarnings("rawtypes")
	public List getList(String player, String path)
	{
		return this.getPlayerProfiles().getList(player + "." + path);
	}
	public List<String> getStringList(String player, String path)
	{
		return this.getPlayerProfiles().getStringList(player + "." + path);
	}
	public List<Integer> getIntegerList(String player, String path)
	{
		return this.getPlayerProfiles().getIntegerList(player + "." + path);
	}
}

