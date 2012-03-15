package me.quaz3l.qQuests.API.Util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Build.BuildonSomething;

public class onSomething {
	private String message;
	private Integer money;
	private Integer health;
	private Integer hunger;
	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.money = build.money();
		this.health = build.health();
		this.hunger = build.hunger();
		this.items = build.items();
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
	public HashMap<Integer, ArrayList<Integer>> items() {
		return this.items;
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
		int i=0;
		while(i < this.items().size())
		{
			if(this.items().get(i).get(1) > 0)
			{
				ItemStack items = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1));
				p.getInventory().addItem(new ItemStack[] { items });
			}
			else
			{
				ItemStack items = new ItemStack(this.items().get(i).get(0), (this.items().get(i).get(1) * -1));
				if (p.getInventory().contains(this.items().get(i).get(0), (this.items().get(i).get(1) * -1))) 
					p.getInventory().removeItem(items);
				else
				{
					i--;
					while(i > -1)
					{
						if(this.items().get(i).get(1) < 0)
						{
							ItemStack itms = new ItemStack(this.items().get(i).get(0), (this.items().get(i).get(1) * -1));
							p.getInventory().addItem(new ItemStack[] { itms });
						}
						else
						{
							ItemStack itms = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1));
							if (p.getInventory().contains(this.items().get(i).get(0), this.items().get(i).get(1))) 
								p.getInventory().removeItem(itms);
							else return false;
						}
						i--;
					}
					return false;
				}
			}
			i++;
		}
		
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
