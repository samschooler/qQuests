package me.quaz3l.qQuests.API;

import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.Chat;

public class QuestAPI {
	private QuestWorker QuestWorker;
	
	public QuestAPI() {
		this.QuestWorker = new QuestWorker();
		qQuests.plugin.qPlugins++;
	}
	
	public QuestWorker getQuestWorker() 
	{
		return QuestWorker;
	}
	
	/**
	 * All Quests
	 * @return A map of all the quests in the quest.yml
	 */
	public Map<String, Quest> getQuests()
	{
		return QuestWorker.getQuests();
	}
	
	/**
	 * Active Quests
	 * @return All Active Quests, And The Players Doing Them
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
	public Quest giveQuest(Player player)
    {
		Random generator = new Random();
		Object[] values = QuestWorker.getQuests().values().toArray();
		Quest q = (Quest) values[generator.nextInt(values.length)];
		
		// Rewards/Fees
		if(feeReward(player, q,  "onJoin"))
		{
			// Give The Quest
			QuestWorker.getActiveQuests().put(player, q);
			Chat.logger("info", q.onJoin().message());
			return q;
		}
		else return null;
    }
	
	public Quest giveQuest(Player player, String quest)
    {
		Quest q = QuestWorker.getQuests().get(quest);
		if(q != null)
			if(feeReward(player, q, "onJoin"))
			{
				// Give The Quest
				QuestWorker.getActiveQuests().put(player, q);
				return q;
			}
			else return null;
		else 
			throw new IllegalArgumentException("The Quest '" +quest + "' Does Not Exist!");
    }
	
	public Quest dropQuest(Player player){
		Quest q = QuestWorker.getActiveQuests().get(player);
		if(hasActiveQuest(player))
		{
			if(feeReward(player, q, "onDrop"))
			{
				QuestWorker.getActiveQuests().put(player, null);
				return q;
			}
			else return null;
		}
		else return q;
	}
	
	public boolean cancelQuest(Player player)
	{
		QuestWorker.getActiveQuests().put(player, null);
		return true;
	}
	
	private boolean feeReward(Player player, Quest q, String type)
	{
		if(type == "onJoin")
		{
			//***TODO*** Add Items
			// Items
			
			// Money
			if(qQuests.plugin.economy != null) 
			{
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
				if(q.onJoin().money() < 0) 
					if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onJoin().money()) 
						return false;
					else
						qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onJoin().money() * -1);
				else
					qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onJoin().money());
			}
			
			// Health
			Integer healAmount = (player.getHealth() + q.onJoin().health());
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onJoin().hunger());
			if(feedAmount > 20)
				player.setFoodLevel(20);
			else if(feedAmount < 0)
				return false;
			else
				player.setFoodLevel(feedAmount);
			
			// Successfully Did Reward/Fees
			return true;
		}
		else if(type == "onDrop")
		{
			//***TODO*** Add Items
			// Items
			
			// Money
			if(qQuests.plugin.economy != null) 
			{
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
				if(q.onDrop().money() < 0) 
					if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onDrop().money()) 
						return false;
					else
						qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onDrop().money() * -1);
				else
					qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onDrop().money());
			}
			
			// Health
			Integer healAmount = (player.getHealth() + q.onDrop().health());
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onDrop().hunger());
			if(feedAmount > 20)
				player.setFoodLevel(20);
			else if(feedAmount < 0)
				return false;
			else
				player.setFoodLevel(feedAmount);
			
			// Successfully Did Reward/Fees
			return true;
		}
		else if(type == "onComplete")
		{
			//***TODO*** Add Items
			// Items
			
			// Money
			if(qQuests.plugin.economy != null) 
			{
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
				if(q.onComplete().money() < 0) 
					if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onComplete().money()) 
						return false;
					else
						qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onComplete().money() * -1);
				else
					qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onComplete().money());
			}
			
			// Health
			Integer healAmount = (player.getHealth() + q.onComplete().health());
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onComplete().hunger());
			if(feedAmount > 20)
				player.setFoodLevel(20);
			else if(feedAmount < 0)
				return false;
			else
				player.setFoodLevel(feedAmount);
			
			// Successfully Did Reward/Fees
			return true;
		}
		else throw new IllegalArgumentException("This Type Of Fee/Reward Does Not Exist!");
	}
	
	// Streamline The Permissions
	public boolean checkPerms(Player p, String perm)
	{
		if(p.hasPermission("qquests." + perm) || p.isOp())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
