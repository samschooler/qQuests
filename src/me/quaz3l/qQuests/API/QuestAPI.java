package me.quaz3l.qQuests.API;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Listeners.Collect;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.PlayerProfiles;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

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
	
	public HashMap<String, Quest> getQuests()
	{
		return Storage.quests;
	}
	public HashMap<String, Quest> getVisibleQuests()
	{
		return Storage.visibleQuests;
	}
	public HashMap<Player, Quest> getActiveQuests()
	{
		return Storage.currentQuests;
	}
    
    public Quest getActiveQuest(Player player)
    {
    	return Storage.currentQuests.get(player);
    }
    
    public boolean hasActiveQuest(Player p)
    {
    	if(Storage.currentQuests.get(p) == null) 
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
		if(this.getVisibleQuests().size() == 0)
			return 1;
		
		// Generate Random Starting Point
		Random gen = new Random();
		Object[] values = this.getVisibleQuests().values().toArray();
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
			q = this.getVisibleQuests().get(quest);
		else
			q = this.getQuests().get(quest);
		
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
		Quest q = this.getActiveQuests().get(player);
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
			return 9;
		
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
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill_player") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("enchant") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("tame")
					//type.equalsIgnoreCase("goto") ||
					//type.equalsIgnoreCase("distance")
					)
				if(Storage.currentTaskProgress.get(player).get(i) < getActiveQuest(player).tasks().get(i).amount())
					return 4;
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
		
		Chat.green(player, getActiveQuest(player).onComplete().message());
		// Set Delay
		if(getActiveQuest(player).delay() > 0)
		{
			Storage.cannotGetQuests.add(player);
			Storage.previousQuest.put(player, getActiveQuest(player));
			Storage.wayPreviousQuestWereGiven.put(player, Storage.wayCurrentQuestsWereGiven.get(player));
			qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {
				
				public void run() {
					Storage.cannotGetQuests.remove(player);
					if(Storage.previousQuest.get(player).nextQuest() != null)
					{
						int result = giveQuest(player, Storage.previousQuest.get(player).nextQuest(), false);
						if(result == 0)
						{
							getActiveQuests().put(player, getQuests().get(Storage.previousQuest.get(player).nextQuest()));
							if(Storage.wayPreviousQuestWereGiven.get(player).equalsIgnoreCase("Commands"))
								Chat.message(player, getActiveQuest(player).onJoin().message());
						}
						else
							Chat.errorCode(result);
					}
					Storage.previousQuest.remove(player);
					Storage.wayPreviousQuestWereGiven.remove(player);
				}
			}, (getActiveQuest(player).delay() * 1200));
		}
		
		Quest q = getActiveQuest(player);
		// If it makes it here reset player data and return with no errors
		this.resetPlayer(player);
		
		if(q.nextQuest() != null)
		{
			if(this.getQuests().containsKey(q.nextQuest().toLowerCase()))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(player, q.nextQuest().toLowerCase(), false);
				if(result == 0)
				{
					qQuests.plugin.qAPI.getActiveQuests().put((player), getQuests().get(q.nextQuest().toLowerCase()));
					Storage.wayCurrentQuestsWereGiven.put((player), "Commands");
					Chat.message((player), qQuests.plugin.qAPI.getActiveQuest(player).onJoin().message());
				}
				else
					Chat.error(player, Chat.errorCode(result));
				}
			else if(!q.nextQuest().isEmpty())
				Chat.logger("warning", Texts.QUEST + " '" + q.name() + "' " + Texts.INVALID + " " + Texts.NEXT_QUEST + "! '" + q.nextQuest() + "'");
		}
		return 0;
	}
	
	public void cancelQuest(Player player)
	{
		this.resetPlayer(player);
	}
	
	private void startQuest(Player player, Quest q)
	{
		// Give The Quest
		this.getActiveQuests().put(player, q);
		
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
		this.getActiveQuests().remove(player);
		Storage.currentTaskProgress.remove(player);
		Storage.tasksLeftInQuest.remove(player);
		Storage.wayCurrentQuestsWereGiven.remove(player);
	}
	
	// Streamline The Permissions
	public boolean checkPerms(Player p, String perm)
	{
		if(qQuests.plugin.permission != null)
			if(qQuests.plugin.permission.has(p, "qquests." + perm))
				return true;
			else
				return false;
		else
			if(p.hasPermission("qquests." + perm))
				return true;
			else
				return false;
	}
}
