package me.quaz3l.qQuests.API;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Listeners.Collect;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.PlayerProfiles;
import me.quaz3l.qQuests.Util.Storage;

public class QuestAPI {
	private QuestWorker QuestWorker;
	private PlayerProfiles Profiles;
	
	public QuestAPI() {
		this.QuestWorker = new QuestWorker();
		this.Profiles = new PlayerProfiles();
	}
	
	public QuestWorker getQuestWorker() 
	{
		return QuestWorker;
	}
	
	public PlayerProfiles getProfiles() 
	{
		return Profiles;
	}
	
	/*
	public Map<String, Quest> getVisibleQuests()
	{
		return QuestWorker.getVisibleQuests();
	}
	*/
	public Map<Player, Quest> getActiveQuests()
	{
		return QuestWorker.getActiveQuests();
	}
    
    public Quest getActiveQuest(Player player)
    {
    	return QuestWorker.getActiveQuests().get(player);
    }
    
    public boolean hasActiveQuest(Player p)
    {
    	if(QuestWorker.getActiveQuests().get(p) == null) 
    		return false;
    	else 
    		return true;
    }
    
	// Quest Functions
	public Integer giveQuest(Player player)
    {
		Integer endResult = 0;
		// Check For Active Quest
		if(qQuests.plugin.qAPI.hasActiveQuest(player))
			return 3;
		// Check To See If THe Player Can Get Quests
		if(Storage.cannotGetQuests.contains(player))
			return 10;
		// Check To See If There Are Any Quests Visible
		if(QuestWorker.getVisibleQuests().size() == 0)
			return 1;
		
		// Generate Random Starting Point
		Random gen = new Random();
		Object[] values = QuestWorker.getVisibleQuests().values().toArray();
		Integer num_o = gen.nextInt(values.length);
		Integer num = num_o;
		boolean b = false;
		boolean first = true;
		
		// Loop Until The Player Gets A Quest, Or Goes Through All The Quests
		while(b == false)
		{
			if(num == num_o && first == false)
			{
				b = true;
				return 1;
			}
			first = false;
			num++;
			if(num >= values.length)
				num = 0;
			Quest q = (Quest) values[num];
			
			// Check If The Quest Is Repeatable For The Player
			if(q.repeated() == -1 || (q.repeated() - qQuests.plugin.qAPI.getProfiles().getInt(player, "FinishCount." + q.name()) >= 0))
			{
				// Rewards/Fees
				Integer u = q.onJoin().feeReward(player);
				if(u == 0)
				{
					// Start Quest
					startQuest(player, q);
					
					b = true;
					return 0;
				}
				else endResult = u;
			}
		}
		return endResult;
    }
	
	public Integer giveQuest(Player player, String quest, boolean onlyVisible)
    {
		// Check If The Player Already Has A Quest
		if(qQuests.plugin.qAPI.hasActiveQuest(player))
			return 3;
		// Check If The Player Can Get Quests
		if(Storage.cannotGetQuests.contains(player))
			return 10;
		Quest q;
		
		// Choose The Quest Map To Get Quests From
		if(onlyVisible)
			q = QuestWorker.getVisibleQuests().get(quest);
		else
			q = QuestWorker.getQuests().get(quest);
		
		// Check If Is A Valid Quest
		if(q == null)
			return 1;
		Integer u = q.onJoin().feeReward(player);
		if(u != 0)
			return u;
		
		// Start Quest
		startQuest(player, q);
		return 0;
    }
	
	public Integer dropQuest(Player player){
		Quest q = QuestWorker.getActiveQuests().get(player);
		if(!qQuests.plugin.qAPI.hasActiveQuest(player))
			return 9;
		Integer u = q.onDrop().feeReward(player);
		if(u == 0)
		{
			Profiles.set(player, "Dropped", (Profiles.getInt(player, "Dropped") + 1));
			this.resetPlayer(player);
			return 0;
		}
		else return u;
	}
	
	public Integer completeQuest(final Player player){
		// Check if the player even has a quest
		if(!hasActiveQuest(player))
			return 3;
		
		int i=0;
		// Check If Tasks Are Completed
		while(qQuests.plugin.qAPI.getActiveQuest(player).tasks().size() > i) 
		{
			if(getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("collect"))
				if(!Collect.check(player))
					return 4;
			if(getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("damage") || 
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("destroy") || 
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("place") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill_player") //||
					//type.equalsIgnoreCase("goto") ||
					//type.equalsIgnoreCase("distance")
					)
			{	
				Integer a = Storage.currentTaskProgress.get(player).get(i);
				if(a < getActiveQuest(player).tasks().get(i).amount())
					return 4;
			}
			i++;
		}
		
		// Charge/Give Fee/Reward
		Integer u = getActiveQuest(player).onComplete().feeReward(player);
		if(u != 0)
			return u;
		
		// Take Items In Collect Tasks
		Collect.take(player);
		
		// Update Player Profile
		Profiles.set(player, "Completed", (Profiles.getInt(player, "Completed") + 1));
		Profiles.set(player, "FinishCount." + getActiveQuest(player).name(), (Profiles.getInt(player, "FinishCount." + getActiveQuest(player).name()) + 1));
		
		// Set Delay
		if(getActiveQuest(player).delay() > 0)
		{
			Storage.cannotGetQuests.add(player);
			qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {
				
				public void run() {
					Storage.cannotGetQuests.remove(player);
				}
			}, (getActiveQuest(player).delay() * 1200));
		}
		
		
		// If it makes it here reset player data and return with no errors
		this.resetPlayer(player);
		return 0;
	}
	
	public void cancelQuest(Player player)
	{
		this.resetPlayer(player);
	}
	
	private void startQuest(Player player, Quest q)
	{
		// Give The Quest
		QuestWorker.getActiveQuests().put(player, q);
		
		// Setup Tasks
		HashMap<Integer, Integer> ctp = new HashMap<Integer, Integer>();
		int i=0;
		while((q.tasks().size() - 1) >= i)
		{
			ctp.put(i, 0);
			i++;
		}
		Storage.currentTaskProgress.put(player, ctp);
		Storage.tasksLeftInQuest.put(player, q.tasks().size());
		Profiles.set(player, "Given", (Profiles.getInt(player, "Given") + 1));
	}
	
	private void resetPlayer(Player player)
	{
		QuestWorker.getActiveQuests().remove(player);
		Storage.currentTaskProgress.get(player).clear();
		Storage.tasksLeftInQuest.remove(player);
		Storage.wayCurrentQuestsWereGiven.clear();
	}
	
	// Streamline The Permissions
	public boolean checkPerms(Player p, String perm)
	{
		if(p.hasPermission("qquests." + perm) || p.isOp())
			return true;
		else
			return false;
	}
}
