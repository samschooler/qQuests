package me.quaz3l.qQuests.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Interwebs {
	private static boolean updateNotified = false;
	
	public static void repeat()
	{
		qQuests.plugin.getServer().getScheduler().runTaskTimerAsynchronously(qQuests.plugin, new Runnable() {

		    public void run() {
		    	// Ping My Server
		    	pingStatus();
				
				// Check For Update
				checkForUpdates();
		    }
		}, 1L, (12 * 60 * 1200));
	}
	public static void checkForUpdates()
	{
		File latestFile = newTempFile("https://raw.github.com/quaz3l/qQuests/master/latest.yml");
		if(latestFile != null)
		{
			FileConfiguration latestConfig = YamlConfiguration.loadConfiguration(latestFile);
			 
			long currentVersion = Long.valueOf(qQuests.plugin.getDescription().getVersion());
			long latestVersion = latestConfig.getLong("Build");
			if(latestVersion > currentVersion)
			{
				if(!updateNotified)
				{
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "####################### UPDATE AVALIBLE! #######################");
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "########################### CHANGELOG ##########################");
					for(Object o : latestConfig.getStringList("Changelog"))
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
				final URL url = new URL("http://mycube.co/qQuests/report.php?dickMove=noThanks&ip=" + qQuests.plugin.getServer().getIp() + "&port=" + qQuests.plugin.getServer().getPort() + "&version=" + qQuests.plugin.getDescription().getVersion() + "&onlinePlayerCount=" + qQuests.plugin.getServer().getOnlinePlayers().length);
				final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
				urlConn.connect();
				if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Chat.logger("info", "Server was pung... Thank You! :)");
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
	public static boolean updatePlugin(String site, String destination)
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