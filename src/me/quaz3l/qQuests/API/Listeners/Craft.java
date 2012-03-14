package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class Craft implements Listener {
	@EventHandler
    public void onCraftItem(CraftItemEvent e) 
	{
		Chat.logger("info", "Hi");
		if(e.isCancelled())
			return;
		if(!(e.getWhoClicked() instanceof Player))
			return;
		if(!qQuests.plugin.qAPI.hasActiveQuest((Player) e.getWhoClicked()))
			return;
		Chat.logger("info", "0000");
	}
}