package me.quaz3l.qQuests.API.QuestModels;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildonSomething;

public class onSomething {
	private String message;
	private double money;
	private int health;
	private int hunger;

	private int levelAdd = 0;
	private int levelSet = -1;
	
	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();
	private HashMap<Integer, String> commands = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsAdd = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsTake = new HashMap<Integer, String>();

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.money = build.money();
		this.health = build.health();
		this.hunger = build.hunger();
		this.levelAdd = build.levelAdd();
		this.levelSet = build.levelSet();
		this.items = build.items();
		this.commands = build.commands();
	}
	public String message() {
		return this.message;
	}
	public double money() {
		return this.money;
	}
	public int health() {
		return this.health;
	}
	public int hunger() {
		return this.hunger;
	}

	public int levelAdd() {
		return this.levelAdd;
	}
	public int levelSet() {
		return this.levelSet;
	}
	
	public HashMap<Integer, ArrayList<Integer>> items() {
		return this.items;
	}
	public HashMap<Integer, String> commands() {
		return this.commands;
	}
	public HashMap<Integer, String> permissionsAdd() {
		return this.permissionsAdd;
	}
	public HashMap<Integer, String> permissionsTake() {
		return this.permissionsTake;
	}
	
	public Integer feeReward(Player p)
	{
		// Fee Requirements
		if(qQuests.plugin.economy != null) 
			if(qQuests.plugin.economy.getBalance(p.getName()) < (this.money() * -1)) 
				return 5;
		if((p.getHealth() + this.health()) < 0)
			return 6;
		if((p.getFoodLevel() + this.hunger()) < 0)
			return 7;
		
		// Money
		if(qQuests.plugin.economy != null) 
		{
			if (qQuests.plugin.economy.bankBalance(p.getName()) != null) 
				qQuests.plugin.economy.createPlayerAccount(p.getName());
			if(this.money() < 0) 
				qQuests.plugin.economy.withdrawPlayer(p.getName(), this.money() * -1);
			else
				qQuests.plugin.economy.depositPlayer(p.getName(), this.money());
		}
		
		/*
		int i=0;
		while(i < this.permissionsAdd().size())
			qQuests.plugin.permission.playerAdd(p, this.permissionsAdd().get(i));
		
		i=0;
		while(i < this.permissionsTake().size())
			if(qQuests.plugin.permission.has(p, this.permissionsTake().get(i)))
				qQuests.plugin.permission.playerRemove(p, this.permissionsTake().get(i));
		*/
		
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
							p.getInventory().removeItem(itms);
						}
						i--;
					}
					return 8;
				}
			}
			i++;
		}
		
		// Commands
		i=0;
		while(i < this.commands().size())
		{
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.commands().get(i).replace("`player", p.getName()));
			i++;
		}
		
		// Level
		if(this.levelSet > -1)
			qQuests.plugin.qAPI.getProfiles().set(p, "Level", this.levelSet);

		if(this.levelAdd != 0)
			qQuests.plugin.qAPI.getProfiles().set(p, "Level", qQuests.plugin.qAPI.getProfiles().getInt(p, "Level") + this.levelAdd);
		
		if(qQuests.plugin.qAPI.getProfiles().getInt(p, "Level") < 0)
			qQuests.plugin.qAPI.getProfiles().set(p, "Level", 0);
		
		
		// Health
		Integer healAmount = (p.getHealth() + this.health());
		if(healAmount >= 20)
			p.setHealth(20);
		else
			p.setHealth(healAmount);
		
		// Hunger
		Integer hungerAmount = (p.getFoodLevel() + this.hunger());
		if(hungerAmount >= 20)
			p.setFoodLevel(20);
		else
			p.setFoodLevel(hungerAmount);
		
		// Successful
		return 0;
	}
}
