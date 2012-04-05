package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.qQuests;
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
		Player player = (Player)e.getOwner();		
		if(!qQuests.plugin.qAPI.hasActiveQuest(player))
			return;
		String type = e.getEntityType().getName();
		
		int i=0;
		
		// Go Through All The Tasks Of The Players Quest
		while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
		{
			// Check For Destroy Quests
			if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("tame"))
				// Check For The Correct Block Id
				if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).idString().equalsIgnoreCase(type))
					// Check If The Player Is Done With The Task
					if(Storage.currentTaskProgress.get(player).get(i) < (qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount() - 1))
					{
						// Add To The Players Task Progress
						Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
						
						// Check For The Source Of The Players Quest
						if(Storage.wayCurrentQuestsWereGiven.get(player) != null)
							if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
								
								// If The Source Is Commands, Tell The Player They're Current Status
								Chat.quotaMessage(player, Texts.TAME_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount(), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display());
					}
					// Check If The Player Is Just Finished
					else if(Storage.currentTaskProgress.get(player).get(i) == (qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount() - 1))
					{
						// Add To The Players Task Progress
						Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
						Storage.tasksLeftInQuest.put(player, Storage.tasksLeftInQuest.get(player) - 1);
						
						// Check For The Source Of The Players Quest
						if(Storage.wayCurrentQuestsWereGiven.get(player) != null)
							if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
							{
								// If The Source Is Commands, Tell The Player They're Done With The Task
								Chat.green(player, Texts.TAME_COMPLETED_QUOTA + " Enough " + qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display() + ",");
								if(Storage.tasksLeftInQuest.get(player) != 0)
									Chat.green(player, Texts.COMMANDS_TASKS_HELP);
								else
									Chat.green(player, Texts.COMMANDS_DONE_HELP);
							}
						
					}
			i++;
		}
	}
}
