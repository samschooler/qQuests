package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Collect {
	public static boolean check(Player p)
	{
		int w = 0;
		while(qQuests.plugin.qAPI.getActiveQuest(p).tasks().size() > w) 
		{
			if(qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).type().equalsIgnoreCase("collect"))
				if (!p.getInventory().contains(qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).idInt(), qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).amount())) 
					return false;
			w++;
		}
		return true;
	}
	public static boolean take(Player p)
	{
		int w = 0;
		while(qQuests.plugin.qAPI.getActiveQuest(p).tasks().size() > w) 
		{
			if(qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).type().equalsIgnoreCase("collect"))
			{
				ItemStack items = new ItemStack(qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).idInt(), qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).amount());
				if (p.getInventory().contains(qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).idInt(), qQuests.plugin.qAPI.getActiveQuest(p).tasks().get(w).amount())) 
					p.getInventory().removeItem(items);
				else return false;
			}
			w++;
		}
		return true;
	}
}
