package me.quaz3l.qQuests.listeners;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class eListener extends EntityListener 
{
	
	public static qQuests plugin;
	
	public eListener(qQuests instance) 
	{
		plugin = instance;
	}
//***FIX** ***RAGE!!!!**** Doesn't F***N' WORK!
	public void onEntityDeath(EntityDeathEvent event)
	{        
        if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)
        {
        	Entity entity = event.getEntity();
        	EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        	if(nEvent.getDamager() instanceof Player) 
        	{
        		Player player = (Player) nEvent.getDamager();
        		String entityType = plugin.isEntityType(entity);
        		if(plugin.currentQuests.get(player) != null) 
        		{
        			if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.type").equalsIgnoreCase("kill")) 
        			{
        				if(plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.object.name").trim().equalsIgnoreCase(entityType.trim())) 
    					{
    						if (plugin.doneItems.get(player) == null) 
    						{
    							plugin.doneItems.put(player, 1);
    						}
    						else
    						{
    							plugin.doneItems.put(player, plugin.doneItems.get(player) +  1);
    						}
    						if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.amount") > plugin.doneItems.get(player)) 
    						{
    							player.sendMessage(ChatColor.LIGHT_PURPLE + "You have killed " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.amount") + " " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.object.name") + "s");
    						}
    						else
    						{
    							player.sendMessage(ChatColor.LIGHT_PURPLE + "You have completed the quest with " + ChatColor.GREEN + plugin.doneItems.get(player) + "/" + plugin.getQuestConfig().getInt(plugin.currentQuests.get(player) + ".task.amount") + " killed " + plugin.getQuestConfig().getString(plugin.currentQuests.get(player) + ".task.object.name") + "s");
    							player.sendMessage(ChatColor.GREEN + "To Turn In The Quest Type: " + ChatColor.YELLOW + "/Quest DONE");
    						}
    					}
        			}
        		}
        	}
        }
	}
}
