package me.quaz3l.qQuests.API.QuestModels;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildonSomething;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.InventoryUtil;
import me.quaz3l.qQuests.Util.Storage;

public class onSomething {
	private String message;
	private int delay;
	private String nextQuest;
	
	private double money;
	private int health;
	private int hunger;

	private int levelAdd;
	private int levelSet = -1;
		
	private HashMap<Integer, ArrayList<Integer>> items = new HashMap<Integer, ArrayList<Integer>>();
	private HashMap<Integer, String> commands = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsAdd = new HashMap<Integer, String>();
	private HashMap<Integer, String> permissionsTake = new HashMap<Integer, String>();

	public onSomething(BuildonSomething build) 
	{
		this.message = build.message();
		this.delay = build.delay();
		this.nextQuest = build.nextQuest();
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
	public int delay() {
		return this.delay;
	}
	public final String nextQuest() {
		return this.nextQuest;
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
	
	public Integer feeReward(final Player p)
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
		
		// Find removable items
		ItemStack[] itemz = null;
		for(int i=0; i < this.items().size(); i++) {
			if(this.items().get(i).get(1) < 0) {
				ItemStack item = null;
				if(this.items().get(i).size() == 2) {
					item = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1)*-1);
			    } else {
					item = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1)*-1, this.items().get(i).get(2).shortValue());
			    }
				if(itemz == null)
					itemz = new ItemStack[] { item };
				else
					itemz[i] = item;
			}
		}
		// Remove the items
		if(itemz!=null)
			if(!InventoryUtil.removeItems(itemz, p.getInventory()))
				return 8;
		
		// Add items
		for(int i=0; i < this.items().size(); i++)
		{
			Chat.logger("debug", "Items: " + this.items().get(i));
			if(this.items().get(i).get(1) > 0) { // If amount is positive
				ItemStack item = null;
				if(this.items().get(i).size() == 2) {
					item = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1));
			    } else {
					item = new ItemStack(this.items().get(i).get(0), this.items().get(i).get(1), this.items().get(i).get(2).shortValue());
			    }
				p.getInventory().addItem(new ItemStack[] { item });
			}
		}
		
		// Commands
		for(int i=0; i < this.commands().size(); i++)
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
		
		// Delay
		Storage.cannotGetQuests.add(p);
		qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {
			public void run() {
				Storage.cannotGetQuests.remove(p);
			}
		}, (this.delay() * 20));
		
		// Successful
		return 0;
	}
}
