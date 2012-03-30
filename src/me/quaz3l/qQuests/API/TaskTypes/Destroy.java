package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Destroy implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) 
	{
		if(e.isCancelled())
			return;
		if(!qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
			return;
		Player player = e.getPlayer();		
		Block block = e.getBlock();
		Integer blockId = block.getTypeId();
		int i=0;
		
		// Go Through All The Tasks Of The Players Quest
		while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
		{
			// Check For Destroy Quests
			if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("destroy"))
				// Check For The Correct Block Id
				if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).idInt() == blockId)
				{
					// Check If The Player Is Done With The Task
					if(Storage.currentTaskProgress.get(player).get(i) < (qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount() - 1))
					{
						// Add To The Players Task Progress
						Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
						
						// Check For The Source Of The Players Quest
						if(Storage.wayCurrentQuestsWereGiven.get(player) != null)
							if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
								
								// If The Source Is Commands, Tell The Player They're Current Status
								Chat.quotaMessage(player, Texts.DESTROY_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount(), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display());
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
								Chat.green(player, Texts.DESTROY_COMPLETED_QUOTA + " Enough " + qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display() + ",");
								if(Storage.tasksLeftInQuest.get(player) != 0)
									Chat.green(player, Texts.TASKS_HELP);
								else
									Chat.green(player, Texts.DONE_HELP);
							}
						
					}
				}
			i++;
		}
	}
}
