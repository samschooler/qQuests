package me.quaz3l.qQuests.listeners;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

public class bListener extends BlockListener
{

	public static qQuests plugin;
	
	public bListener(qQuests instance) 
	{
		plugin = instance;
	}

	public void onBlockBreak(BlockBreakEvent event)
	{ 
		if(plugin.currentQuests.get(event.getPlayer()) != null) 
		{
			Player player = event.getPlayer();		
			Block block = event.getBlock();
			Integer blockId = block.getTypeId();
			if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.type").equalsIgnoreCase("destroy")) 
			{
				if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.object.id") == blockId) 
				{
					if (plugin.doneItems.get(player) == null) 
					{
						plugin.doneItems.put(player, 1);
					}
					else
					{
						plugin.doneItems.put(player, plugin.doneItems.get(player) +  1);
					}
					if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") > plugin.doneItems.get(player)) 
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE + "You have broken " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") + " " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.object.name"));
					}
					else
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE + "You have completed the quest with " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") + " broken " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.object.name"));
						player.sendMessage(ChatColor.GREEN + "To Turn In The Quest Type: " + ChatColor.YELLOW + "/Quest DONE");
					}
				}
			}
		}
	}
	public void onBlockDamage(BlockDamageEvent event)
	{
		if(plugin.currentQuests.get(event.getPlayer()) != null) 
		{
			Player player = event.getPlayer();
			Block block = event.getBlock();
			Integer blockId = block.getTypeId();
			
			if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.type").equalsIgnoreCase("damage")) 
			{
				if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.object.id") == blockId) 
				{
					if (plugin.doneItems.get(player) == null) 
					{
						plugin.doneItems.put(player, 1);
					}
					else
					{
						plugin.doneItems.put(player, plugin.doneItems.get(player) +  1);
					}
					if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") > plugin.doneItems.get(player)) 
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE + "You have damaged " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") + " " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.object.name"));
					}
					else
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE + "You have completed the quest with " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".tasks.0.amount") + " damaged " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".tasks.0.object.name"));
						player.sendMessage(ChatColor.GREEN + "To Turn In The Quest Type: " + ChatColor.YELLOW + "/Quest DONE");
					}
				}
			}
		}
	}
}