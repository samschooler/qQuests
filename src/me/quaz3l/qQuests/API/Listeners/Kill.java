package me.quaz3l.qQuests.API.Listeners;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.MobType;
import me.quaz3l.qQuests.Util.Storage;

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
        	while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
        	{
        		if(qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill"))
        			if(qQuests
        					.plugin
        					.qAPI
        					.getActiveQuest(player)
        					.tasks()
        					.get(i)
        					.idString()
        					.equalsIgnoreCase(entityType))
        			{
        				Integer a = Storage.currentTaskProgress.get(player).get(i);
        				if(a < qQuests.plugin.qAPI.getActiveQuest(player).tasks().get(i).amount())
        					Storage.currentTaskProgress.get(player).put(i, (a + 1));
        			}
        		i++;
        	}
        }
	}
}
