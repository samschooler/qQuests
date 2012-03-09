package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Storage;

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
					Integer a = Storage.currentTaskProgress.get(player).get(i);
					if(a < qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount())
						Storage.currentTaskProgress.get(player).put(i, (a + 1));
				}
			i++;
		}
	}
}
