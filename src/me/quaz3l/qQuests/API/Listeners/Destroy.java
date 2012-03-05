package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.Util.Chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Destroy implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) 
	{
		Chat.logger("info", "Hi");
	}
}
