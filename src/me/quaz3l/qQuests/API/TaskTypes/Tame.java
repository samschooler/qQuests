package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class Tame implements Listener {
	@EventHandler
	public void onEntityTame(EntityTameEvent e)
	{
		if(e.isCancelled())
			return;
		String player = ((Player)e.getOwner()).getName();		
		if(!qQuests.plugin.qAPI.hasActiveQuest(player))
			return;
		String type = e.getEntityType().getName();
		
		
		
		int i=-1;
		// Go Through All The Tasks Of The Players Quest
		for(Task task : qQuests.plugin.qAPI.getActiveQuest(player).tasks()) 
		{
			i++;
			// Check For Tame Quests
			if(task.type().equalsIgnoreCase("tame"))
				continue;
			// Check For The Correct Block Id
			if(task.idString().equalsIgnoreCase(type))
				continue;
			
			// Check If The Player Is Done With The Task
			if(Storage.currentTaskProgress.get(player).get(i) < (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).set(i, (Storage.currentTaskProgress.get(player).get(i) + 1));

				// If The Source Is Commands, Tell The Player They're Current Status
				Chat.quotaMessage(player, Texts.TAME_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), task.amount(), task.display());
			}
			// Check If The Player Is Just Finished
			else if(Storage.currentTaskProgress.get(player).get(i) == (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).set(i, (Storage.currentTaskProgress.get(player).get(i) + 1));

				// Check For The Source Of The Players Quest
				if(Storage.wayCurrentQuestsWereGiven.get(player) != null) {
					if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.TAME_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(qQuests.plugin.qAPI.getActiveQuest(player).isDone(player))
							Chat.green(player, Texts.COMMANDS_TASKS_HELP);
						else
							Chat.green(player, Texts.COMMANDS_DONE_HELP);
					} 
					else if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Signs"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.TAME_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(qQuests.plugin.qAPI.getActiveQuest(player).isDone(player))
							Chat.green(player, Texts.SIGNS_TASKS_HELP);
						else
							Chat.green(player, Texts.SIGNS_DONE_HELP);
					}
				}
			}
		}
	}
}
