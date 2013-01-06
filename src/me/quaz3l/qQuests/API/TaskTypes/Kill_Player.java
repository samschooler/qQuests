package me.quaz3l.qQuests.API.TaskTypes;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Kill_Player implements Listener {
	private HashMap<Player, Player> damageList = new HashMap<Player, Player>();
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) 
	{
		if(!(e.getEntity() instanceof Player))
			return;
		
		Player attacker = null;		
		switch (e.getCause()) {
		case ENTITY_ATTACK:
			EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e;
			if(nEvent.getDamager() instanceof Player) 
				attacker = (Player) nEvent.getDamager();
			else if(nEvent.getDamager() instanceof Fireball)
			{
				if(((Fireball) nEvent.getDamager()).getShooter() instanceof Player)
					attacker = (Player) ((Fireball) nEvent.getDamager()).getShooter();
			}
			else if(nEvent.getDamager() instanceof Arrow)
			{
				if(((Arrow) nEvent.getDamager()).getShooter() instanceof Player)
					attacker = (Player) ((Arrow) nEvent.getDamager()).getShooter();
			}
			break;
		default:
			break;
		}
		
		if(attacker != null && e.getEntity() != null)
		{
			if (damageList.get(((Player) e.getEntity())) != null)
                damageList.remove(((Player) e.getEntity()));
            damageList.put(((Player) e.getEntity()), attacker);
		}
	}
	@EventHandler
    public void onEntityDeath(EntityDeathEvent e) 
	{
    	if(!(e.getEntity() instanceof Player))
    		return;
    	
    	Player player = damageList.get((Player)e.getEntity());
		if(!qQuests.plugin.qAPI.hasActiveQuest(player))
    		return;
    		
		int i=-1;
		// Go Through All The Tasks Of The Players Quest
		for(Task task : qQuests.plugin.qAPI.getActiveQuest(player).tasks().values()) 
		{
			i++;
			// Check For Kill_Player Quests
			if(!task.type().equalsIgnoreCase("kill_player"))
				continue;
			// Check For The Correct Player
			if(!task.idString().equalsIgnoreCase(((Player)e.getEntity()).getName()) || !task.idString().equalsIgnoreCase("`any"))
				continue;
			
			// Check If The Player Is Done With The Task
			if(Storage.currentTaskProgress.get(player).get(i) < (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));

				// Tell The Player They're Current Status
				Chat.quotaMessage(player, Texts.KILL_PLAYER_COMPLETED_QUOTA, Storage.currentTaskProgress.get(player).get(i), task.amount(), task.display());
			}
			// Check If The Player Is Just Finished
			else if(Storage.currentTaskProgress.get(player).get(i) == (task.amount() - 1))
			{
				// Add To The Players Task Progress
				Storage.currentTaskProgress.get(player).put(i, (Storage.currentTaskProgress.get(player).get(i) + 1));
				Storage.tasksLeftInQuest.put(player, Storage.tasksLeftInQuest.get(player) - 1);

				// Check For The Source Of The Players Quest
				if(Storage.wayCurrentQuestsWereGiven.get(player) != null) {
					if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Commands"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.KILL_PLAYER_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(Storage.tasksLeftInQuest.get(player) != 0)
							Chat.green(player, Texts.COMMANDS_TASKS_HELP);
						else
							Chat.green(player, Texts.COMMANDS_DONE_HELP);
					} 
					else if(Storage.wayCurrentQuestsWereGiven.get(player).equalsIgnoreCase("Signs"))
					{
						// If The Source Is Commands, Tell The Player They're Done With The Task
						Chat.green(player, Texts.KILL_PLAYER_COMPLETED_QUOTA + " Enough " + task.display() + ",");
						if(Storage.tasksLeftInQuest.get(player) != 0)
							Chat.green(player, Texts.SIGNS_TASKS_HELP);
						else
							Chat.green(player, Texts.SIGNS_DONE_HELP);
					}
				}
			}
		}
	}
}
