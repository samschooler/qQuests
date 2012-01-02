package me.quaz3l.qQuests.Util;

import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;

public class functions {
	private static qQuests plugin;
    
    public functions(qQuests plugin) 
    {
	functions.plugin = plugin;
    }
    
	public static void giveQuest(Player player) {
		if(plugin.currentQuests.get(player) == null) 
		{
			Set<String> questNo = plugin.getQuestConfig().getKeys(false);
			Random rand = new Random(); 
			Object SelectedQuest = rand.nextInt(questNo.size()); 
			
			plugin.currentQuests.put(player, SelectedQuest);
			
			plugin.endQuest(player, "join");
			
			player.sendMessage(ChatColor.AQUA + "Your Quest: " + ChatColor.LIGHT_PURPLE + plugin.getQuestConfig().getString(SelectedQuest + ".info.messageStart"));
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You already have a active quest!");
			player.sendMessage(ChatColor.AQUA + "Your Quest: " + ChatColor.LIGHT_PURPLE + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".info.messageStart"));
		}
	}
	public static void getQuestInfo(Player player) {
		if(plugin.currentQuests.get(player) != null) 
		{
			if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type").equalsIgnoreCase("collect") || plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type").equalsIgnoreCase("destroy")) 
			{
				player.sendMessage(ChatColor.AQUA + "Quest Info: ");
				player.sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".info.name"));
				player.sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type") + " " + 
																										  plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.amount") + " " +
																										  plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.object.name") + "(ID:" +
																										  plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.object.id") + ")");
				//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(player) + ".market.reward.items");
				player.sendMessage(ChatColor.YELLOW + "Rewards:");
				player.sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.money"));
				player.sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.health"));
				player.sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.hunger"));
			}
			else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type").equalsIgnoreCase("kill")) 
			{
				player.sendMessage(ChatColor.AQUA + "Quest Info: ");
				player.sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".info.name"));
				player.sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type") + " " + 
																										  plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.amount") + " " +
																										  plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.object.name") + "s");
				//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(player) + ".market.reward.items");
				player.sendMessage(ChatColor.YELLOW + "Rewards:");
				player.sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.money"));
				player.sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.health"));
				player.sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".market.reward.hunger"));
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You Don't Have A Active Quest!");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "To Get A Quest Type: " + ChatColor.YELLOW + "/Quest GIVE");
		}
	}
}
