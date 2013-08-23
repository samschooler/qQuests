package me.quaz3l.qQuests.API.Effects;

import java.util.ArrayList;
import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.InventoryUtil;
import me.quaz3l.qQuests.Util.MaterialUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemsEffect extends qEffect {
	public HashMap<String, String> enough = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	@Override
	public void executeEffect(String player, Object value) {
		ArrayList<String> itemList = null;
		try {
			itemList = (ArrayList<String>) value;

		} catch(ClassCastException e) {}
		String[][] strs = new String[itemList.size()][2];
		String[][] qtrs = new String[itemList.size()][2];
		int rm=0;
		int i=0;
		for (String s : itemList) {
			strs[i] = s.split(" ");
			qtrs[i] = strs[i][0].split(":");

			// Don't include additions; we don't need to check anything there
			if(Integer.parseInt(strs[i][1]) < 0)
				rm++;
			i++;
		}
		ItemStack[] removeItems = new ItemStack[rm];
		ItemStack[] addItems = new ItemStack[itemList.size()-rm];
		i=0;
		while (i<itemList.size()) {
			if(Material.matchMaterial(qtrs[i][0]) != null)
			{

				try
				{
					if(Integer.parseInt(strs[i][1]) < 0) {
						short rdamage = -1;
						if(qtrs.length == 2)
							rdamage = Short.parseShort(qtrs[i][1]);

						if(rdamage >= 0) {
							removeItems[i] = new ItemStack(
									Integer.parseInt(qtrs[i][0]), // Item ID
									Integer.parseInt(strs[i][1])*-1, // Amount
									(short) rdamage);           // Damage
						} else {
							removeItems[i] = new ItemStack(
									Integer.parseInt(qtrs[i][0]), 
									Integer.parseInt(strs[i][1])*-1);
						}
					} else {
						short rdamage = -1;
						if(qtrs.length == 2)
							rdamage = Short.parseShort(qtrs[i][1]);

						if(rdamage >= 0) {
							addItems[i] = new ItemStack(
									Integer.parseInt(qtrs[i][0]), // Item ID
									Integer.parseInt(strs[i][1]), // Amount
									(short) rdamage);           // Damage
						} else {
							addItems[i] = new ItemStack(
									Integer.parseInt(qtrs[i][0]), 
									Integer.parseInt(strs[i][1]));
						}
					}
				}
				catch(Exception e){}
			}
			i++;
		}
		PlayerInventory inv =qQuests.plugin.getServer().getPlayer(player).getInventory();
		if(removeItems.length > 0) {
			InventoryUtil.removeItems(removeItems, inv);
		}
		if(addItems.length > 0) {
			for(ItemStack item : addItems) {
				Chat.logger("debug", item + "");
				if(InventoryUtil.fits(item, inv)) {
					InventoryUtil.add(item, inv);
				} else {
					InventoryUtil.drop(item, inv);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int passedRequirement(String player, Object value) {
		if(value == null) {
			return -1;
		}
		ArrayList<String> itemList;
		try {
			itemList = (ArrayList<String>) value;
			if(itemList.get(0) == null) return -2;

		} catch(ClassCastException e) {
			return -2;
		}
		String[] strs = {""};
		String[] qtrs = {""};
		for (String s : itemList) {
			strs = s.split(" ");
			qtrs = strs[0].split(":");

			// Don't include additions; we don't need to check anything there
			if(Integer.parseInt(strs[1]) >= 0) continue;

			if(Material.matchMaterial(qtrs[0]) != null)
			{
				try
				{
					ItemStack item;
					short damage = -1;
					if(qtrs.length == 2)
						damage = Short.parseShort(qtrs[1]);

					if(damage >= 0) {
						item = new ItemStack(
								Integer.parseInt(qtrs[0]), // Item ID
								Integer.parseInt(strs[1])*-1, // Amount
								(short) damage);           // Damage
					} else {
						item = new ItemStack(
								Integer.parseInt(qtrs[0]), 
								Integer.parseInt(strs[1])*-1);
					}
					if(!InventoryUtil.hasItem(item, qQuests.plugin.getServer().getPlayer(player).getInventory())) {
						this.enough.put(player, MaterialUtil.getName(item));
						return 1;
					}
				}
				catch(Exception e)
				{
					return -3;
				}
			} else return -4;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int validate(Object value) {
		if(value == null) {
			return -1;
		}
		ArrayList<String> itemList;
		try {
			itemList = (ArrayList<String>) value;
			if(itemList.get(0) == null) return -2;

		} catch(ClassCastException e) {
			return -2;
		}
		String[] strs = {""};
		String[] qtrs = {""};
		for (String s : itemList) {
			strs = s.split(" ");
			qtrs = strs[0].split(":");

			if(Material.matchMaterial(qtrs[0]) != null)
			{
				try
				{
					Integer.parseInt(qtrs[0]); // Item Id
					Integer.parseInt(strs[1]); // Amount
					if(qtrs.length == 2)
						Integer.parseInt(qtrs[1]); // Item Damage
				}
				catch(Exception e)
				{
					return -3;
				}
			} else return -4;
		}
		return 0;
	}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -4: return "Some material IDs maybe invalid!";
		case -3: return "Some items may not be formatted correctly!";
		case -2: return "The items seem not to be a list!";
		case -1: return "This doesn't seem right; I am not getting anything for items, it is turning up NULL!";
		case 1: return "You don't enough " + this.enough.get(player);
		default: return "Unknown Error! LULZ! :p";
		}
	}

	@Override
	public String getName() {
		return "items";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

}
