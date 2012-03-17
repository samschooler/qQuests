package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.MobType;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Kill implements Listener {
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) 
	{
		if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)
        {
			Entity entity = e.getEntity();
        	EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        	Player player = null;
        	if(nEvent.getDamager() instanceof Player) 
        		player = (Player) nEvent.getDamager();
        	else if(nEvent.getDamager() instanceof Fireball)
        	{
        		if(((Fireball) nEvent.getDamager()).getShooter() instanceof Player)
        			player = (Player) ((Fireball) nEvent.getDamager()).getShooter();
        	}
        	else if(nEvent.getDamager() instanceof Arrow)
        		if(((Arrow) nEvent.getDamager()).getShooter() instanceof Player)
        			player = (Player) ((Arrow) nEvent.getDamager()).getShooter();
        	if(!qQuests.plugin.qAPI.hasActiveQuest(player))
        		return;
        	String entityType =  MobType.isEntityType(entity);
        	int i=0;
        	
        	// Go Through All The Tasks Of The Players Quest
        	while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
    		{
    			// Check For Destroy Quests
    			if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill"))
    				// Check For The Correct Mob
    				if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).idString().equalsIgnoreCase(entityType))
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
    								Chat.quotaMessage(player, Texts.KILL_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount(), qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display());
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
    								Chat.green(player, Texts.KILL_COMPLETED_QUOTA + " Enough " + qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).display() + ",");
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
}
