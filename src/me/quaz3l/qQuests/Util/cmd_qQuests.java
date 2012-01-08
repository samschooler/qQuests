package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class cmd_qQuests implements CommandExecutor 
{
	
	private qQuests plugin;
    
    public cmd_qQuests(qQuests plugin) 
    {
	this.plugin = plugin;
    }
    
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) 
	{
		if (s instanceof Player) 
		{
			if(args.length < 1)
			{
				((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
				return false;
			}
			else
			{
				if((args[0].equalsIgnoreCase("give")))
				{
					if (args.length < 2)
					{
						plugin.giveQuest(((Player) s));
						return true;
					}
					else
					{
						plugin.giveQuest(((Player) s), args[1]);
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(plugin.currentQuests.get(((Player) s)) != null) 
					{
						if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type") != null && plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("collect") || plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("destroy") || plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("damage") || plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("place")) 
						{
							((Player) s).sendMessage(ChatColor.AQUA + "Quest Info: ");
							((Player) s).sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".info.name"));
							((Player) s).sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type") + " " + 
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") + " " +
																													  plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.name") + "(ID:" +
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.id") + ")");
							//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(((Player) s)) + ".market.reward.items");
							((Player) s).sendMessage(ChatColor.YELLOW + "Rewards:");
							((Player) s).sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.money"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.health"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.hunger"));
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("kill")) 
						{
							((Player) s).sendMessage(ChatColor.AQUA + "Quest Info: ");
							((Player) s).sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".info.name"));
							((Player) s).sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type") + " " + 
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") + " " +
																													  plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.name") + "s");
							//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(((Player) s)) + ".market.reward.items");
							((Player) s).sendMessage(ChatColor.YELLOW + "Rewards:");
							((Player) s).sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.money"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.health"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.hunger"));
						}
						else ((Player) s).sendMessage(ChatColor.RED + "The objective types are undefined for this quest!");
					}
					else
					{
						((Player) s).sendMessage(ChatColor.RED + "You Don't Have A Active Quest!");
						((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "To Get A Quest Type: " + ChatColor.GREEN + "/Quest GIVE");
					
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					if(plugin.currentQuests.get(((Player) s)) != null) {
						plugin.endQuest(((Player) s), "drop");
					}
					else
					{
						((Player) s).sendMessage(ChatColor.RED + "You Don't Have A Active Quest!");
						((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "To Get A Quest Type: " + ChatColor.GREEN + "/Quest GIVE");
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(plugin.currentQuests.get(((Player) s)) != null) {
						if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("collect")) {
							PlayerInventory inventory = ((Player) s).getInventory();
							ItemStack istack = new ItemStack(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.id"), plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount"));
							if (inventory.contains(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.id"), plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount"))) {
								//Remove items from Inventory...
								inventory.removeItem(istack);
								plugin.endQuest(((Player) s), "done");
							} 
							else 
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Not Met The Requirements Of This Quest!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("destroy"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") <= plugin.doneItems.get(((Player) s)))
								{
									plugin.endQuest(((Player) s), "done");
								}
								else
								{
									((Player) s).sendMessage(ChatColor.RED + "You Have Not Broke Enough Blocks!");
								}
							}
							else
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Not Broke Enough Blocks!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("damage"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") <= plugin.doneItems.get(((Player) s)))
								{
									plugin.endQuest(((Player) s), "done");
								}
								else
								{
									((Player) s).sendMessage(ChatColor.RED + "You Have Not Damaged Enough Blocks!");
								}
							}
							else
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Not Damaged Enough Blocks!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("place"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") <= plugin.doneItems.get(((Player) s)))
								{
									plugin.endQuest(((Player) s), "done");
								}
								else
								{
									((Player) s).sendMessage(ChatColor.RED + "You Have Not Placed Enough Blocks!");
								}
							}
							else
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Not Placed Enough Blocks!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.type").equalsIgnoreCase("kill"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".tasks.0.amount") <= plugin.doneItems.get(((Player) s)))
								{
									plugin.endQuest(((Player) s), "done");
								}
								else
								{
									((Player) s).sendMessage(ChatColor.RED + "You Have Killed Enough " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.name") + "s!");
								}
							}
							else
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Killed Enough " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".tasks.0.object.name") + "s!");
							}
						}
					}
					else
					{
						((Player) s).sendMessage(ChatColor.RED + "You Don't Have A Active Quest!");
						((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "To Get A Quest Type: " + ChatColor.GREEN + "/Quest GIVE");
					}
					return true;
				}
				else
				{
					((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop" + ChatColor.RED + "]");
					return false;
				}
			}
		}
		else
		{
			s.sendMessage(ChatColor.RED + "[" + plugin.getDescription().getName() + "] Sorry A Quest Can Only Be Used By Players!");
			return false;
		}
	}
}