package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Persist {
	private YamlConfiguration Config = null;
	private File File = null;

	public Persist() {
		this.File = new File(qQuests.plugin.getDataFolder(), "savedState.yml");
		this.Config = this.loadConfig(this.File);
		
		this.getConfig();
		/*qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {

			public void run() {
				qQuests.plugin.persist.save();
			}
		},  (Storage.persistDelay *60 * 20) + 100, Storage.persistDelay *60 * 20);*/
	}

	// Set a persistent data point in memory
	public void set(String key, String value) {
		this.getConfig().set(key, value);
	}
	public void set(String key, HashMap<String,String> value) {
		getConfig().set(key, null);
		for(Object s : value.keySet()) {
			getConfig().set(key+"."+s, value.get(s));
		}
	}
	public void setArrayHashMap(String key, HashMap<String, Object[]> value) {
		getConfig().set(key, null);
		for(Object s : value.keySet()) {
			getConfig().set(key+"."+s, value.get(s));
		}
	}
	
	// Get a persisted data point from memory
	public Object get(String key) {
		return this.getConfig().get(key);
	}
	public HashMap<String, String> getStringHashMap(String key) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		ConfigurationSection sect = getConfig().getConfigurationSection(key);
		if(sect == null)
			return map;
		
		for(String s : sect.getKeys(false)) {
			map.put(s, sect.getString(s));
		}
		return map;
	}
	public HashMap<String, Integer> getIntegerHashMap(String key) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		ConfigurationSection sect = getConfig().getConfigurationSection(key);
		if(sect == null)
			return map;
		
		for(String s : sect.getKeys(false)) {
			map.put(s, sect.getInt(s));
		}
		return map;
	}
	public HashMap<String, Object[]> getArrayHashMap(String key) {
		HashMap<String, Object[]> map = new HashMap<String, Object[]>();
		
		ConfigurationSection sect = getConfig().getConfigurationSection(key);
		if(sect == null)
			return map;
		
		for(String s : sect.getKeys(false)) {
			map.put(s, sect.getList(s).toArray());
		}
		return map;
	}
	public HashMap<String, ArrayList<Integer>> getIntegerArrayHashMap(String key) {
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		
		ConfigurationSection sect = getConfig().getConfigurationSection(key);
		if(sect == null)
			return map;
		
		for(String s : sect.getKeys(false)) {
			map.put(s, (ArrayList<Integer>) sect.getIntegerList(s));
		}
		return map;
	}
	
	// Saves persistent data from memory to saveState.yml
	public void save() {
		this.saveConfig(this.File, this.Config);
	}
	
	private YamlConfiguration getConfig()
	{
		if (Config == null) {
			return loadConfig(this.File);
		}
		return Config;
	}
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
}
