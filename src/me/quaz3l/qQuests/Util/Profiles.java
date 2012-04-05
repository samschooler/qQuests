package me.quaz3l.qQuests.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.qQuests;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Profiles
{
  private FileConfiguration pConfig = null;
  private File pConfigFile = null;

  public void initializePlayerProfiles() {
    getPlayerProfiles();
    getPlayerProfiles().options().copyDefaults(true);
    savePlayerProfiles();
  }
  public FileConfiguration getPlayerProfiles() {
    if (this.pConfig == null) {
      reloadPlayerProfiles();
    }
    return this.pConfig;
  }
  public void reloadPlayerProfiles() {
    if (this.pConfigFile == null)
    {
      this.pConfigFile = new File(qQuests.plugin.getDataFolder(), "profiles.yml");
    }
    this.pConfig = YamlConfiguration.loadConfiguration(this.pConfigFile);

    InputStream defConfigStream = qQuests.plugin.getResource("profiles.yml");
    if (defConfigStream != null) {
      YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
      this.pConfig.setDefaults(defConfig);
    }
  }

  public void savePlayerProfiles() {
    if ((this.pConfig == null) || (this.pConfigFile == null))
      return;
    try
    {
      this.pConfig.save(this.pConfigFile);
    } catch (IOException ex) {
      qQuests.plugin.getClass(); Chat.logger("severe", "[qQuests] " + "Could not save config to " + this.pConfigFile);
    }
  }

  public Integer getQuestsTimesCompleted(Player p, Quest q)
  {
    return getInt(p, "FinishCount." + q.name());
  }

  public void set(Player player, String path, String value)
  {
    getPlayerProfiles().set(player.getName() + "." + path, value);
    savePlayerProfiles();
  }

  public void set(Player player, String path, Integer value) {
    getPlayerProfiles().set(player.getName() + "." + path, value);
    savePlayerProfiles();
  }

  public void set(Player player, String path, boolean value) {
    getPlayerProfiles().set(player.getName() + "." + path, Boolean.valueOf(value));
    savePlayerProfiles();
  }

  public void set(Player player, String path, @SuppressWarnings("rawtypes") List value) {
    getPlayerProfiles().set(player.getName() + "." + path, value);
    savePlayerProfiles();
  }

  public String getString(Player player, String path)
  {
    return getPlayerProfiles().getString(player.getName() + "." + path);
  }

  public Integer getInt(Player player, String path) {
    return Integer.valueOf(getPlayerProfiles().getInt(player.getName() + "." + path));
  }

  public boolean getBoolean(Player player, String path) {
    return getPlayerProfiles().getBoolean(player.getName() + "." + path);
  }

  @SuppressWarnings("rawtypes")
  public List getList(Player player, String path)
  {
    return getPlayerProfiles().getList(player.getName() + "." + path);
  }

  public List<String> getStringList(Player player, String path) {
    return getPlayerProfiles().getStringList(player.getName() + "." + path);
  }

  public List<Integer> getIntegerList(Player player, String path) {
    return getPlayerProfiles().getIntegerList(player.getName() + "." + path);
  }
}