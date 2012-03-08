package me.quaz3l.qQuests.API.Util;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildonSomething;

public class onSomething {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	private HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> amounts = new HashMap<Integer, Integer>();

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.money = build.money();
		this.health = build.health();
		this.hunger = build.hunger();
		this.items = build.items();
		this.amounts = build.amounts();
	}
	public String message() {
		return this.message;
	}
	public Integer money() {
		return this.money;
	}
	public Integer health() {
		return this.health;
	}
	public Integer hunger() {
		return this.hunger;
	}
	public HashMap<Integer, Integer> items() {
		return this.items;
	}
	public HashMap<Integer, Integer> amounts() {
		return this.amounts;
	}
	
	public boolean feeReward(Player p)
	{
		// Requirements
		if(qQuests.plugin.economy != null) 
			if(qQuests.plugin.economy.getBalance(p.getDisplayName()) < this.money()) 
				return false;
		if((p.getHealth() + this.health()) < 0)
			return false;
		if((p.getFoodLevel() + this.hunger()) < 0)
			return false;
		
		// Transaction
		// Money
		if(qQuests.plugin.economy != null) 
		{
			if (qQuests.plugin.economy.bankBalance(p.getDisplayName()) != null) 
				qQuests.plugin.economy.createPlayerAccount(p.getDisplayName());
			if(this.money() < 0) 
				qQuests.plugin.economy.withdrawPlayer(p.getDisplayName(), this.money() * -1);
			else
				qQuests.plugin.economy.depositPlayer(p.getDisplayName(), this.money());
		}
		
		// Items
		
		//if ((itemid != -1) && (itemamount != -1)) {
		//      ItemStack reward = new ItemStack(itemid, itemamount);
		//      p.getInventory().addItem(new ItemStack[] { reward });
		//}
		
		// Health
		Integer healAmount = (p.getHealth() + this.health());
		if(healAmount >= 20)
			p.setHealth(20);
		else
			p.setHealth(healAmount);
		
		// Hunger
		Integer hungerAmount = (p.getFoodLevel() + this.health());
		if(hungerAmount >= 20)
			p.setFoodLevel(20);
		else
			p.setHealth(hungerAmount);
		
		// Successful
		return true;
	}
}
