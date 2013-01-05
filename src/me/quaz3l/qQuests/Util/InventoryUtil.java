package me.quaz3l.qQuests.Util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Acrobot, Quaz3l
 */
public class InventoryUtil {
	/**
	 * Takes items from a players inventory, if the player does not have all the items, it leaves everything alone.
	 * 
	 * @param items     Array of ItemStacks to remove
	 * @param inventory The inventory to remove from
	 * @return if the items were taken
	 */
	public static boolean removeItems(ItemStack[] items, Inventory inventory) {	
		Chat.logger("debug", "+++++++++++++++++++++");
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		for (ItemStack item : items) {
			amounts.add(item.getAmount());
		}
		Chat.logger("debug", "Amount: " + items[0].getAmount());
		// Check to see if the inventory has the items
		if(!hasSimilarItems(items, inventory))
			return false;
		Chat.logger("debug", "Amount: " + items[0].getAmount());
		for(int i=0; i < items.length; i++) {
			HashMap<Integer, ? extends ItemStack> inv = inventory.all(items[i].getType());
			for (ItemStack stack : inv.values()) {
				if(amounts.get(i) <= 0)
					break; // Got requested amount
				
				Chat.logger("debug", "Inv Damage: " + stack.getDurability());
				Chat.logger("debug", "Request Damage: " + items[i].getDurability());
				
				if(items[i].getDurability() > 0)
					if(items[i].getDurability() != stack.getDurability())
						continue;
				
				Chat.logger("debug", "I: " + String.valueOf(i));
				Chat.logger("debug", "F Amount: " + (amounts.get(i) - stack.getAmount()));
				Chat.logger("debug", "Item Amount: " + amounts.get(i));
				Chat.logger("debug", "Stack Amount: " + stack.getAmount());
				
				if((amounts.get(i) - stack.getAmount()) < 0) { // Remove the requested amount, and
					Chat.logger("debug", "Dick.");
					stack.setAmount(stack.getAmount() - amounts.get(i));
					Chat.logger("debug", "Stack Result: " + (stack.getAmount() - amounts.get(i)));
					amounts.set(i, 0);
				} else { // Remove stack, and amount that it had from requested
					amounts.set(i, amounts.get(i) - stack.getAmount());
					Chat.logger("debug", "Removed. BITCH");
					inventory.removeItem(stack);
				}
			}
		}
		return true;
	}
	public static boolean hasSimilarItems(ItemStack[] items, Inventory inventory) {
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		for (ItemStack item : items) {
			amounts.add(item.getAmount());
		}
		for(int i=0; i < items.length; i++) {
			HashMap<Integer, ? extends ItemStack> inv = inventory.all(items[i].getType());
			Chat.logger("debug", "Type: " + items[i].getType());
			Chat.logger("debug", "Matches: " + inv.values().size());
			for (ItemStack stack : inv.values()) {
				if(amounts.get(i) <= 0)
					break; // Got requested amount
				
				Chat.logger("debug", "Inv Damage: " + stack.getDurability());
				Chat.logger("debug", "Request Damage: " + items[i].getDurability());
				
				if(items[i].getDurability() > 0)
					if(items[i].getDurability() != stack.getDurability())
						continue;
				
				Chat.logger("debug", "I: " + String.valueOf(i));
				Chat.logger("debug", "F Amount: " + (amounts.get(i) - stack.getAmount()));
				Chat.logger("debug", "Item Amount: " + amounts.get(i));
				Chat.logger("debug", "Stack Amount: " + stack.getAmount());
				
				if((amounts.get(i) - stack.getAmount()) < 0) { // Remove the requested amount, and
					amounts.set(i, 0);
				} else { // Remove stack, and amount that it had from requested
					amounts.set(i, amounts.get(i) - stack.getAmount());
				}
			}		
		}
		Chat.logger("debug", "Amounts: " + amounts);
		for (int amount : amounts) {
			Chat.logger("debug", "Z Amount: " + amount);
			if(amount <= 0) {
				Chat.logger("debug", "It's FUCKING true!@!");
				return true;
			}
		}
		return false;
	}
    /**
     * Returns the amount of the item inside the inventory
     *
     * @param item      Item to check
     * @param inventory inventory
     * @return amount of the item
     */
    public static int getAmount(ItemStack item, Inventory inventory) {
        if (!inventory.contains(item.getType())) {
            return 0;
        }

        if (inventory.getType() == null) {
            return Integer.MAX_VALUE;
        }

        HashMap<Integer, ? extends ItemStack> items = inventory.all(item.getType());
        int itemAmount = 0;

        for (ItemStack iStack : items.values()) {
            if (!MaterialUtil.equals(iStack, item)) {
                continue;
            }

            itemAmount += iStack.getAmount();
        }

        return itemAmount;
    }

    /**
     * Tells if the inventory is empty
     *
     * @param inventory inventory
     * @return Is the inventory empty?
     */
    public static boolean isEmpty(Inventory inventory) {
        for (ItemStack stack : inventory.getContents()) {
            if (!MaterialUtil.isEmpty(stack)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the inventory has stock of this type
     *
     * @param items     items
     * @param inventory inventory
     * @return Does the inventory contain stock of this type?
     */
    public static boolean hasItems(ItemStack[] items, Inventory inventory) {
        for (ItemStack item : items) {
        	Chat.logger("info", "Durability: " + item.getDurability());
            if (!inventory.containsAtLeast(item, item.getAmount())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the item fits the inventory
     *
     * @param item      Item to check
     * @param inventory inventory
     * @return Does item fit inside inventory?
     */
    public static boolean fits(ItemStack item, Inventory inventory) {
        int left = item.getAmount();

        if (inventory.getMaxStackSize() == Integer.MAX_VALUE) {
            return true;
        }

        for (ItemStack iStack : inventory.getContents()) {
            if (left <= 0) {
                return true;
            }

            if (MaterialUtil.isEmpty(iStack)) {
                left -= item.getMaxStackSize();
                continue;
            }

            if (!MaterialUtil.equals(iStack, item)) {
                continue;
            }

            left -= (iStack.getMaxStackSize() - iStack.getAmount());
        }

        return left <= 0;
    }

    /**
     * Adds an item to the inventory
     * (it currently uses a custom method of adding items, because Bukkit hasn't fixed it for 6 months now)
     *
     * @param item      Item to add
     * @param inventory Inventory
     * @return Number of leftover items
     */
    public static int add(ItemStack item, Inventory inventory) {
        if (item.getAmount() < 1) {
            return 0;
        }

        int amountLeft = item.getAmount();
        int maxStackSize = item.getMaxStackSize();

        for (int currentSlot = 0; currentSlot < inventory.getSize() && amountLeft > 0; currentSlot++) {
            ItemStack currentItem = inventory.getItem(currentSlot);
            ItemStack duplicate = item.clone();

            if (MaterialUtil.isEmpty(currentItem)) {
                duplicate.setAmount(Math.min(amountLeft, maxStackSize));
                duplicate.addEnchantments(item.getEnchantments());

                amountLeft -= duplicate.getAmount();

                inventory.setItem(currentSlot, duplicate);
            } else if (currentItem.getAmount() < maxStackSize && MaterialUtil.equals(currentItem, item)) {
                int currentAmount = currentItem.getAmount();
                int neededToAdd = Math.min(maxStackSize - currentAmount, amountLeft);

                duplicate.setAmount(currentAmount + neededToAdd);
                duplicate.addEnchantments(item.getEnchantments());

                amountLeft -= neededToAdd;

                inventory.setItem(currentSlot, duplicate);
            }
        }

        return amountLeft;
    }

    /**
     * Removes an item from the inventory
     *
     * @param item      Item to remove
     * @param inventory Inventory
     * @return Number of items that couldn't be removed
     */
    public static int remove(ItemStack item, Inventory inventory) {
        Map<Integer, ItemStack> leftovers = inventory.removeItem(item);

        return countItems(leftovers);
    }

    /**
     * If items in arguments are similar, this function merges them into stacks of the same type
     *
     * @param items Items to merge
     * @return Merged stack array
     */
    public static ItemStack[] mergeSimilarStacks(ItemStack... items) {
        List<ItemStack> itemList = new LinkedList<ItemStack>();

        Iterating:
        for (ItemStack item : items) {
            for (ItemStack iStack : itemList) {
                if (MaterialUtil.equals(item, iStack)) {
                    iStack.setAmount(iStack.getAmount() + item.getAmount());
                    continue Iterating;
                }
            }

            itemList.add(item);
        }

        return itemList.toArray(new ItemStack[itemList.size()]);
    }

    /**
     * Counts the amount of items in ItemStacks
     *
     * @param items ItemStacks of items to count
     * @return How many items are there?
     */
    public static int countItems(ItemStack... items) {
        int count = 0;

        for (ItemStack item : items) {
            count += item.getAmount();
        }

        return count;
    }

    /**
     * Counts leftovers from a map
     *
     * @param items Leftovers
     * @return Number of leftovers
     */
    public static int countItems(Map<Integer, ?> items) {
        int totalLeft = 0;

        for (int left : items.keySet()) {
            totalLeft += left;
        }

        return totalLeft;
    }
}