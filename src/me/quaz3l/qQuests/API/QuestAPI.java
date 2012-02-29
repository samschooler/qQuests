package me.quaz3l.qQuests.API;

import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Quest.QuestWorker;
import me.quaz3l.qQuests.API.Util.Quest;

/**
 * This is the main class that is vital to link to make a qPlugin, here is how to access it:<br><br>
 * <code>
 * public QuestAPI qAPI = new QuestAPI();
 * </code>
 */
public class QuestAPI {
	private QuestWorker QuestWorker;
	
	/**
	 * This is the main class that is vital to link to make a qPlugin, here is how to access it:<br><br>
	 * <code>
	 * public QuestAPI qAPI = new QuestAPI();
	 * </code>
	 */
	public QuestAPI() {
		this.QuestWorker = new QuestWorker();
		qQuests.plugin.qPlugins++;
	}
	
	
	
	/**
	 * This is an advanced Worker that allows you to<br>
	 * <ul>
	 * <li>Refresh The quests.yml: <i><b>buildQuests();</b></i></li>
	 * <li>Add Quests To The quests.yml: <i><b>TODO</b></i></li>
	 * <li>Remove Quests From The quests.yml: <i><b>TODO</b></i></li>
	 * <li>Change Quests In The quests.yml: <i><b>TODO</b></i></li>
	 * </ul>
	 * <br>
	 * <br>
	 * To access this you will need:
	 * <code>
	 * TODO
	 * public QuestWorker qWorker = qAPI.getQuestWorker();
	 * </code>
	 *
	 */
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
    	return QuestWorker.getActiveQuests().containsKey(p);
    }
    
	// Quest Functions
	public Quest giveQuest(Player player)
    {
		if(QuestWorker.getActiveQuests().get(player) == null)
		{
			Random generator = new Random();
			Object[] values = QuestWorker.getQuests().values().toArray();
			Quest q = (Quest) values[generator.nextInt(values.length)];
			
			// Rewards/Fees
			if(feeReward(player, q,  "onJoin"))
			{
			// Give The Quest
			QuestWorker.getActiveQuests().put(player, q);
			return q;
			}
			else return null;
		}
		else return QuestWorker.getActiveQuests().get(player);
    }
	
	public Quest giveQuest(Player player, String quest)
    {
		if(hasActiveQuest(player))
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
			{
				throw new IllegalArgumentException("The Quest '" +quest + "' Does Not Exist!");
			}
		}
		else return QuestWorker.getActiveQuests().get(player);
    }
	
	public boolean dropQuest(Player player){
		if(hasActiveQuest(player))
		{
			if(feeReward(player, QuestWorker.getActiveQuests().get(player), "onDrop"))
			{
				QuestWorker.getActiveQuests().put(player, null);
				return true;
			}
			else return false;
		}
		else return true;
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
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
			if(q.onJoin().money < 0) 
				if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onJoin().money) 
					return false;
				else
					qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onJoin().money * -1);
			else
				qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onJoin().money);
			
			// Health
			Integer healAmount = (player.getHealth() + q.onJoin().health);
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onJoin().hunger);
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
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
			if(q.onDrop().money < 0) 
				if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onDrop().money) 
					return false;
				else
					qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onDrop().money * -1);
			else
				qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onDrop().money);
			
			// Health
			Integer healAmount = (player.getHealth() + q.onDrop().health);
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onDrop().hunger);
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
				if (qQuests.plugin.economy.bankBalance(player.getDisplayName()) != null) 
					qQuests.plugin.economy.createPlayerAccount(player.getDisplayName());
			if(q.onComplete().money < 0) 
				if(qQuests.plugin.economy.getBalance(player.getDisplayName()) < q.onComplete().money) 
					return false;
				else
					qQuests.plugin.economy.withdrawPlayer(player.getDisplayName(), q.onComplete().money * -1);
			else
				qQuests.plugin.economy.depositPlayer(player.getDisplayName(), q.onComplete().money);
			
			// Health
			Integer healAmount = (player.getHealth() + q.onComplete().health);
			if(healAmount > 20)
				player.setHealth(20);
			else if(healAmount < 0)
				return false;
			else
				player.setHealth(healAmount);
			
			// Hunger
			Integer feedAmount = (player.getFoodLevel() + q.onComplete().hunger);
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
}
