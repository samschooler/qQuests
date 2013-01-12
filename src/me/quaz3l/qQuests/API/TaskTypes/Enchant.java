package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class Enchant implements Listener {
	@EventHandler
	public void onEnchant(EnchantItemEvent e)
	{
		if(e.isCancelled())
			return;
		if(!qQuests.plugin.qAPI.hasActiveQuest(e.getEnchanter().getName()))
			return;
		String player = e.getEnchanter().getName();		
		ItemStack item = e.getItem();
		Integer itemId = item.getTypeId();
		short itemDam = item.getDurability();
		
		int i=-1;
		// Go Through All The Tasks Of The Players Quest
		for(Task task : qQuests.plugin.qAPI.getActiveQuest(player).tasks().values()) 
		{
			i++;
			// Check For Enchant Quests
			if(!task.type().equalsIgnoreCase("enchant"))
				continue;
			// Check For The Correct Block Id
			if(task.idInt() != itemId)
				continue;
			// Check For The Correct Block Id
			if(task.durability() > 0)
				if(task.durability() != itemDam)
					continue;
			// Check If The Player Is Done With The Task
			if(Storage.currentTaskProgress.get(player).get(i) < (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));

				// Tell The Player They're Current Status
				Chat.quotaMessage(player, Texts.ENCHANT_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), task.amount(), task.display());
			}
			// Check If The Player Is Just Finished
			else if(Storage.currentTaskProgress.get(player).get(i) == (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
				Storage.tasksLeftInQuest.put(player, Storage.tasksLeftInQuest.get(player) - 1);

				// Check For The Source Of The Players Quest
				if(Storage.wayCurrentQuestsWereGiven.get(player) != null) {
					if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.ENCHANT_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(Storage.tasksLeftInQuest.get(player) != 0)
							Chat.green(player, Texts.COMMANDS_TASKS_HELP);
						else
							Chat.green(player, Texts.COMMANDS_DONE_HELP);
					} 
					else if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Signs"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.ENCHANT_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(Storage.tasksLeftInQuest.get(player) != 0)
							Chat.green(player, Texts.SIGNS_TASKS_HELP);
						else
							Chat.green(player, Texts.SIGNS_DONE_HELP);
					}
				}
			}
		}
	}
}
