package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.Util.InventoryUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Collect {
	public static boolean check(Player p)
	{
		for(Task task : qQuests.plugin.qAPI.getActiveQuest(p).tasks().values()) 
		{
			if(task.type().equalsIgnoreCase("collect")) {
				if(!InventoryUtil.hasSimilarItems(new ItemStack[] { new ItemStack(task.idInt(), task.amount(), task.durability()) }, p.getInventory())) {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean take(Player p)
	{
		for(Task task : qQuests.plugin.qAPI.getActiveQuest(p).tasks().values()) 
		{
			if(task.type().equalsIgnoreCase("collect")) {
				if(!InventoryUtil.removeItems(new ItemStack[] { new ItemStack(task.idInt(), task.amount(), task.durability()) }, p.getInventory())) {
					return false;
				}
			}
		}
		return true;
	}
}
