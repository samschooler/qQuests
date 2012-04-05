package me.quaz3l.qQuests.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Interwebs {
	private static boolean updateNotified = false;
	
	public static void repeatCheck()
	{
		qQuests.plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(qQuests.plugin, new Runnable() {

		    public void run() {
		    	// Ping My Server
		    	Interwebs.pingStatus();
				
				// Check For Update
				Interwebs.checkForUpdates();
		    }
		}, 1L, (3 * 60 * 1200));
	}
	
	public static void checkForUpdates()
	{
		File latestFile = newTempFile("http://www.mycube.co/qQuests/latest.yml");
		if(latestFile != null)
		{
			FileConfiguration latestConfig = YamlConfiguration.loadConfiguration(latestFile);
			 
			double[] currentVersion = {Double.valueOf(qQuests.plugin.getDescription().getVersion().split("\\.")[0]),
			                Double.valueOf(qQuests.plugin.getDescription().getVersion().split("\\.")[1]),
			                Double.valueOf(qQuests.plugin.getDescription().getVersion().split("\\.")[2])};
			double[] latestVersion = {latestConfig.getDouble("Major", currentVersion[0]),
			                latestConfig.getDouble("Minor", currentVersion[1]),
			                latestConfig.getDouble("Build", currentVersion[2])};
			if(latestVersion[0] > currentVersion[0] ||
			                latestVersion[1] > currentVersion[1] ||
			                latestVersion[2] > currentVersion[2])
			{
				if(updateNotified)
				{
					Chat.logger("warning", "################################################################");
					Chat.logger("warning", "####################### UPDATE AVALIBLE! #######################");
					Chat.logger("warning", "################################################################");
					if(Storage.autoUpdate)
					{
						if(updatePlugin(latestConfig.getString("Source"), qQuests.plugin.getDataFolder().getPath() + ".jar"))
						{
							Chat.logger("warning", "################### qQuests IS AUTOUPDATING! ###################");
							Chat.logger("warning", "############# PLEASE RELOAD OR RESTART THE SERVER! #############");
							Chat.logger("warning", "################################################################");
						}
						else
						{
							Chat.logger("warning", "################## PLEASE UPDATE qQuests FROM ##################");
							Chat.logger("warning", "######## http://www.github.com/quaz3l/qQuests/downloads ########");
							Chat.logger("warning", "################################################################");
						}
					}
					else
					{
						Chat.logger("warning", "################## PLEASE UPDATE qQuests FROM ##################");
						Chat.logger("warning", "######## http://www.github.com/quaz3l/qQuests/downloads ########");
						Chat.logger("warning", "################################################################");
					}
					Chat.logger("warning", "########################### CHANGELOG ##########################");
					for(Object o : latestConfig.getStringList("Changelog"))
						Chat.logger("warning", "- " + o);
					Chat.logger("warning", "################################################################");
				}
				else
					Chat.logger("warning", "There is an update avalible, get if from http://www.github.com/quaz3l/qQuests/downloads");
			}
		}
	}
	public static boolean pingStatus() {
		if(Storage.tellMeYourUsingMyPlugin)
		{
			try 
			{
				final URL url = new URL("http://mycube.co/qQuests/report.php?dickMove=noThanks&port=" + qQuests.plugin.getServer().getPort() + "&version=" + qQuests.plugin.getDescription().getVersion() + "&onlinePlayerCount=" + qQuests.plugin.getServer().getOnlinePlayers().length);
				final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
				urlConn.connect();
				if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK)
					return true;
				else
					urlConn.disconnect();
			} 
			catch (final Exception e) {} 
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
		} 
		catch(MalformedURLException e) {} 
		catch(IOException e) {}
		return null;
	}
	private static boolean updatePlugin(String site, String destination)
	{
		try 
		{
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
		} 
		catch(MalformedURLException e) {
			return false;
		} 
		catch(IOException e) {
			return false;
		}
		return true;
	}
}
