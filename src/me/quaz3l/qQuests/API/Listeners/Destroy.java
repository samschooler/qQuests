package me.quaz3l.qQuests.API.Listeners;

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
		while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
		{
			if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("destroy"))
				if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).idInt() == blockId)
				{
					if(Storage.currentTaskProgress.get(player).get(i) < (qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount() - 1))
					{
						Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
						if(Storage.wayCurrentQuestsWereGiven.get(player) != null)
							if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
								Chat.quotaMessage(player, Texts.DESTROY_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount(), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display());
					}
					else if(Storage.currentTaskProgress.get(player).get(i) == (qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount() - 1))
					{
						Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
						if(Storage.wayCurrentQuestsWereGiven.get(player) != null)
							if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
							{
								Chat.green(player, Texts.DESTROY_COMPLETED_QUOTA + " " + qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display() + ",");
								Chat.green(player, Texts.TASKS_HELP);
							}
					}
				}
			i++;
		}
	}
}
