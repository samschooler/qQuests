package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.configuration.file.YamlConfiguration;

// TODO Add class specific set and get methods, or push functions of the getConfig straight to public



public class Persist {
	private YamlConfiguration Config = null;
	private File File = null;

	public Persist() {
		this.File = new File(qQuests.plugin.getDataFolder(), "savedState.yml");
		this.Config = this.loadConfig(this.File);
		
		this.getConfig();
		qQuests.plugin.getServer().getScheduler().runTaskTimer(qQuests.plugin, new Runnable() {

			public void run() {
				qQuests.plugin.persist.save();
			}
		},  (Storage.persistDelay *60 * 20) + 100, Storage.persistDelay *60 * 20);
	}

	// Set a persistent data point in memory
	public void set(String key, Object value) {
		this.getConfig().set(key, value);
	}
	
	// Get a persisted data point from memory
	public Object get(String key) {
		return this.getConfig().get(key);
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
