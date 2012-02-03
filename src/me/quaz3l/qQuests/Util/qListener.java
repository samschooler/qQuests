package me.quaz3l.qQuests.Util;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.quaz3l.qQuests.qQuests;

public class qListener implements Listener {
	public qQuests plugin;
 
	public qListener(qQuests plugin) {
		this.plugin = plugin;
	}
	
	// Block Events
	@EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent e) 
	{
		
	}
	@EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent e) 
	{
		
	}
	@EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDamage(BlockDamageEvent e) 
	{
		
	}
	
	// Entity Events
	@EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent e) 
	{
		
	}
}