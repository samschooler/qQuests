package me.quaz3l.qQuests.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Interwebs {
	private static boolean updateNotified = false;
	
	public static void start()
	{
		qQuests.plugin.getServer().getScheduler().runTaskTimerAsynchronously(qQuests.plugin, new Runnable() {
			public void run() {talk();}
		}, 1L, (12 * 60 * 1200));
	}
	public static void talk() {
		// Ping My Server
    	pingStatus();
		
		// Check For Update
		checkForUpdates();
	}
	public static void checkForUpdates()
	{
		File latestFile = newTempFile("https://raw.github.com/quaz3l/qQuests/master/latest.yml");
		if(latestFile != null)
		{
			FileConfiguration latestConfig = YamlConfiguration.loadConfiguration(latestFile);
			 
			long currentVersion = Long.valueOf(qQuests.plugin.getDescription().getVersion());
			long latestVersion = latestConfig.getLong("version");
			Chat.logger("debug", "Current Version: " + currentVersion);
			Chat.logger("debug", "Latest Version: " + latestVersion);
			if(latestVersion > currentVersion)
			{
				if(!updateNotified)
				{
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "####################### UPDATE AVALIBLE! #######################");
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "########################### CHANGELOG ##########################");
					for(Object o : latestConfig.getStringList("changelog"))
						Chat.logger("warning", "> " + o);
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "############### Type 'qQuests update' to update. ###############");
					Chat.logger("warning", "################################################################");
				}
				else
					Chat.logger("warning", "There is an update avalible, type 'qQuests update' to update.");
				//updatePlugin(latestConfig.getString("Source"), qQuests.plugin.getDataFolder().getPath() + ".jar");
			}
		}
	}
	public static boolean pingStatus() {
		if(qQuests.plugin.getConfig().getBoolean("tellMeYourUsingMyPlugin"))
		{
			try {
				final URL url = new URL("http://qquests.aws.af.cm/report.php?dickMove=noThanks&port=" + qQuests.plugin.getServer().getPort() + "&version=" + qQuests.plugin.getDescription().getVersion() + "&onlinePlayerCount=" + qQuests.plugin.getServer().getOnlinePlayers().length);
				final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
				urlConn.connect();
				if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Chat.logger("debug", "Server was pung... Thank You! :)");
					return true;
				}
				else
					urlConn.disconnect();
			} catch (Exception e) {}
		}
		return false;
	}
	private static File newTempFile(String site)
	{
	        try {
	                File file = File.createTempFile("latest", "");
	                file.deleteOnExit();
	 
	                BufferedInputStream in = new BufferedInputStream(new URL(site).openStream());
	                FileOutputStream fout = new FileOutputStream(file);
	 
	                byte data[] = new byte[1024]; //Download 1 KB at a time
	                int count;
	                while((count = in.read(data, 0, 1024)) != -1)
	                {
	                        fout.write(data, 0, count);
	                }
	 
	                in.close();
	                fout.close();
	 
	                return file;
	        } catch (Exception e) {}
	 
	        return null;
	}
	public static boolean tryUpdate(CommandSender s) {
		File latestFile = newTempFile("https://raw.github.com/quaz3l/qQuests/master/latest.yml");
		if(latestFile != null)
		{
			FileConfiguration latestConfig = YamlConfiguration.loadConfiguration(latestFile);
			 
			long currentVersion = Long.valueOf(qQuests.plugin.getDescription().getVersion());
			long latestVersion = latestConfig.getLong("version");
			if(latestVersion > currentVersion)
			{
				if(updatePlugin(latestConfig.getString("source"), qQuests.plugin.getDataFolder().getPath() + ".jar")) {
					Chat.message(s, "Reload server for update to take effect!");
				} else Chat.message(s, "qQuests didn't download! Now the JAR is most likely corrupt! Go download the update from: " + latestConfig.getString("source"));
			}
		}
		return false;
	}
	private static boolean updatePlugin(String site, String destination)
	{
	        try {
	                BufferedInputStream in = new BufferedInputStream(new URL(site).openStream());
	                FileOutputStream fout = new FileOutputStream(destination);
	 
	                byte data[] = new byte[1024]; //Download 1 KB at a time
	                int count;
	                while((count = in.read(data, 0, 1024)) != -1)
	                {
	                        fout.write(data, 0, count);
	                }
	 
	                in.close();
	                fout.close();
	        } catch(Exception e) {
	        	return false;
	        }
	        return true;
	}
}