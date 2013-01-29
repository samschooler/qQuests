package me.quaz3l.qQuests.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Effects.EffectHandler;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.Requirements.RequirementHandler;
import me.quaz3l.qQuests.API.TaskTypes.Collect;
import me.quaz3l.qQuests.API.TaskTypes.GoTo;
import me.quaz3l.qQuests.Plugins.PluginHandler;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.PlayerProfiles;
import me.quaz3l.qQuests.Util.QuestFrag;
import me.quaz3l.qQuests.Util.Storage;

public class QuestAPI {
	private QuestWorker QuestWorker;
	private PlayerProfiles Profiles;
	private PluginHandler pluginHandler;
	private RequirementHandler requirementHandler;
	private EffectHandler effectHandler;
	
	public QuestAPI() {
		this.QuestWorker = new QuestWorker();
		this.Profiles = new PlayerProfiles();
		this.pluginHandler = new PluginHandler();
		this.requirementHandler = new RequirementHandler();
		this.effectHandler = new EffectHandler();
	}
	
	public QuestWorker getQuestWorker() 
	{
		return QuestWorker;
	}
	
	public PlayerProfiles getProfiles() 
	{
		return Profiles;
	}
	
	// API Handlers
	public PluginHandler getPluginHandler() {
		return this.pluginHandler;
	}
	public RequirementHandler getRequirementHandler() {
		return this.requirementHandler;
	}
	public EffectHandler getEffectHandler() {
		return this.effectHandler;
	}
	
	public HashMap<String, Quest> getQuests()
	{
		return Storage.quests;
	}
	public Quest getQuest(String name)
	{
		return Storage.quests.get(name.toLowerCase());
	}
	public HashMap<String, Quest> getVisibleQuests()
	{
		return Storage.visibleQuests;
	}
	public HashMap<String, Quest> getActiveQuests()
	{
		return Storage.currentQuests;
	}
    
    public Quest getActiveQuest(String player)
    {
    	return Storage.currentQuests.get(player);
    }
    
    public boolean hasActiveQuest(String player)
    {
    	if(Storage.currentQuests.get(player) == null) 
    		return false;
    	else 
    		return true;
    }
    
	// Quest Functions
	public Integer giveQuest(String player, String via)
    {
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
		Integer u = 0;
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
			
			// Try To Give The Quest
			u = this.giveQuest(player, q.name(), true, via);
			if(u == 0)
				return u;
			else continue;
		}
		return u;
    }
	
	public Integer giveQuest(String player, String quest, boolean onlyVisible, String via)
    {
		Chat.logger("debug", quest);
		quest = QuestFrag.get(quest.toLowerCase()).toLowerCase();
		// Check If The Player Already Has A Quest
		if(qQuests.plugin.qAPI.hasActiveQuest(player))
			if(qQuests.plugin.qAPI.getActiveQuest(player).name().equalsIgnoreCase(quest))
				return 15;
			else
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
		
		Chat.logger("debug", quest);
		
		// Check If Is A Valid Quest
		if(q == null)
			return 1;
		
		// Check If The Quest Is Repeatable For The Player
		if(q.repeated() > 0 && (q.repeated() - qQuests.plugin.qAPI.getProfiles().getInt(player, "FinishCount." + q.name()) <= 0))
			return 11;
		
		// Check Requirements
		if(!this.requirementHandler.checkRequirements(player, quest))
			return 16;
		
		// Give/Take Rewards/Fees
		int u = q.onJoin().feeReward(player);
		if(u != 0)
			return u;
		
		
		// Start Quest
		startQuest(player, q);
		Storage.wayCurrentQuestsWereGiven.put(player, via);
		return 0;
    }
	
	public HashMap<Integer, Quest> getAvailableQuests(String player)
    {
		// Generate Random Starting Point
		HashMap<Integer, Quest> u = new HashMap<Integer, Quest>();
		
		// Loop Until The Player Gets A Quest, Or Goes Through All The Quests
		int i=-1;
		for(Quest q : this.getVisibleQuests().values())
		{
			i++;
			// Try To Give The Quest
			// Check If The Player Already Has A Quest
			if(qQuests.plugin.qAPI.hasActiveQuest(player))
				continue;
			
			// Check If The Player Can Get Quests
			if(Storage.cannotGetQuests.contains(player))
				continue;
			
			// Check If Is A Valid Quest
			if(q == null)
				continue;
			
			// Check If The Quest Is Repeatable For The Player
			if(q.repeated() > 0 && (q.repeated() - qQuests.plugin.qAPI.getProfiles().getInt(player, "FinishCount." + q.name()) <= 0))
				continue;
			
			// Check Requirements
			if(!this.requirementHandler.checkRequirements(player, q.name()))
				continue;
			
			u.put(i, q);
		}
		return u;
    }
	
	public Integer dropQuest(final String player){
		Quest q = this.getActiveQuests().get(player);
		if(!qQuests.plugin.qAPI.hasActiveQuest(player))
			return 9;
		if(q.forced())
			return 14;
		Integer u = q.onDrop().feeReward(player);
		if(u == 0)
		{
			Profiles.set(player, "Dropped", (Profiles.getInt(player, "Dropped") + 1));
			
			// Store previous quest
			Storage.previousQuest.put(player, getActiveQuest(player));
			Storage.wayPreviousQuestWereGiven.put(player, Storage.wayCurrentQuestsWereGiven.get(player));
			
			// If it makes it here reset player data
			this.resetPlayer(player);
			
			// Set Delay/Give Next Quest
			qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {

				public void run() {
					if(Storage.previousQuest.get(player).onDrop().nextQuest() != null)
					{
						int result = giveQuest(player, Storage.previousQuest.get(player).onDrop().nextQuest(), false, Storage.wayPreviousQuestWereGiven.get(player));
						if(result == 0)
						{
							Chat.message(player, getActiveQuest(player).onJoin().message(player));
						}
						else
							Chat.errorCode(result, Storage.wayPreviousQuestWereGiven.get(player), player);
					}
					Storage.previousQuest.remove(player);
					Storage.wayPreviousQuestWereGiven.remove(player);
				}
			}, (Storage.previousQuest.get(player).onDrop().delay() * 20));
			
			return 0;
		}
		else return u;
	}
	
	public Integer completeQuest(final String player){
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
			Chat.logger("debug", "Has items!");
			Chat.logger("debug", getActiveQuest(player).tasks().get(i).type());
			Chat.logger("debug", "Left: " + Storage.currentTaskProgress.get(player).get(i) + " " + getActiveQuest(player).tasks().get(i).amount());
			if(getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("damage") || 
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("destroy") || 
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("place") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("kill_player") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("enchant") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("tame") ||
					getActiveQuest(player).tasks().get(i).type().equalsIgnoreCase("goto")
					//type.equalsIgnoreCase("distance")
					)
				if(Storage.currentTaskProgress.get(player).get(i) < getActiveQuest(player).tasks().get(i).amount())
					return 4;
			i++;
		}
		Chat.logger("debug", "101");
		// Charge/Give Fee/Reward
		Integer u = getActiveQuest(player).onComplete().feeReward(player);
		if(u != 0)
			return u;
		Chat.logger("debug", "102");
		// Take Items In Collect Tasks
		Collect.take(player);
		Chat.logger("debug", "103");
		// Update Player Profile
		Profiles.set(player, "Completed", (Profiles.getInt(player, "Completed") + 1));
		Profiles.set(player, "FinishCount." + getActiveQuest(player).name(), (Profiles.getInt(player, "FinishCount." + getActiveQuest(player).name()) + 1));
		
		Chat.green(player, getActiveQuest(player).onComplete().message(player));
		
		// Store previous quest
		Storage.previousQuest.put(player, getActiveQuest(player));
		Storage.wayPreviousQuestWereGiven.put(player, Storage.wayCurrentQuestsWereGiven.get(player));
		
		// If it makes it here reset player data
		this.resetPlayer(player);
				
		// Set Delay/Give Next Quest
		qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {

			public void run() {
				if(Storage.previousQuest.get(player).onComplete().nextQuest() != null)
				{
					int result = giveQuest(player, Storage.previousQuest.get(player).onComplete().nextQuest(), false, Storage.wayPreviousQuestWereGiven.get(player));
					if(result == 0)
					{
						Chat.message(player, getActiveQuest(player).onJoin().message(player));
					}
					else
						Chat.errorCode(result, Storage.wayPreviousQuestWereGiven.get(player), player);
				}
				Storage.previousQuest.remove(player);
				Storage.wayPreviousQuestWereGiven.remove(player);
			}
		}, (Storage.previousQuest.get(player).onComplete().delay() * 20)+10);
		
		return 0;
	}
	
	public void cancelQuest(String player)
	{
		this.resetPlayer(player);
	}
	
	private void startQuest(String player, Quest q)
	{
		// Give The Quest
		this.getActiveQuests().put(player, q);
		
		// Setup Tasks
		ArrayList<Integer> ctp = new ArrayList<Integer>();
		int i=0;
		while((q.tasks().size() - 1) >= i)
		{
			ctp.add(i, 0);
			
			// Check For GoTo tasks TODO TEMP fix
			if(q.tasks().get(i).type().equalsIgnoreCase("goto")) {
				if(q.tasks().get(i).idLocation1() != null) {
					if(q.tasks().get(i).idLocation2() != null) {
						GoTo.launchAgent(player, q.tasks().get(i).idLocation1(), q.tasks().get(i).idLocation2());
					} else {
						GoTo.launchAgent(player, q.tasks().get(i).idLocation1(), q.tasks().get(i).radius());
					}
				}
			}
			
			i++;
		}
				
		Storage.currentTaskProgress.put(player, ctp);
		Profiles.set(player, "Given", (Profiles.getInt(player, "Given") + 1));
	}
	
	private void resetPlayer(String player)
	{
		this.getActiveQuests().remove(player);
		Storage.currentTaskProgress.remove(player);
		Storage.wayCurrentQuestsWereGiven.remove(player);
	}
	
	// Streamline The Permissions
	public boolean checkPerms(String player, String perm)
	{
		Player p = qQuests.plugin.getServer().getPlayer(player);
		if(p == null)
			return false;
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
