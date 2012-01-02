package me.quaz3l.qQuests.Util;

import java.util.Random;
import java.util.Set;

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
				if( (args[0].equalsIgnoreCase("give")) )
				{
					if(plugin.currentQuests.get(((Player) s)) == null) 
					{
						Set<String> questNo = plugin.getQuestConfig().getKeys(false);
						Random rand = new Random(); 
						Object SelectedQuest = rand.nextInt(questNo.size()); 
						
						plugin.currentQuests.put(((Player) s), SelectedQuest);
						
						plugin.endQuest(((Player) s), "join");
						
						((Player) s).sendMessage(ChatColor.AQUA + "Your Quest: " + ChatColor.LIGHT_PURPLE + plugin.getQuestConfig().getString(SelectedQuest + ".info.messageStart"));
					}
					else
					{
						((Player) s).sendMessage(ChatColor.RED + "You already have a active quest!");
						((Player) s).sendMessage(ChatColor.AQUA + "Your Quest: " + ChatColor.LIGHT_PURPLE + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".info.messageStart"));
					}
					return true;
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(plugin.currentQuests.get(((Player) s)) != null) 
					{
						if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("collect") || plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("destroy")) 
						{
							((Player) s).sendMessage(ChatColor.AQUA + "Quest Info: ");
							((Player) s).sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".info.name"));
							((Player) s).sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type") + " " + 
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount") + " " +
																													  plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.object.name") + "(ID:" +
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.object.id") + ")");
							//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(((Player) s)) + ".market.reward.items");
							((Player) s).sendMessage(ChatColor.YELLOW + "Rewards:");
							((Player) s).sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.money"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.health"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.hunger"));
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("kill")) 
						{
							((Player) s).sendMessage(ChatColor.AQUA + "Quest Info: ");
							((Player) s).sendMessage(ChatColor.YELLOW + "Name: " + ChatColor.GREEN + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".info.name"));
							((Player) s).sendMessage(ChatColor.YELLOW + "Task: " + ChatColor.GREEN + "You need to " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type") + " " + 
																													  plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount") + " " +
																													  plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.object.name") + "s");
							//List<String> rewardItems = plugin.getQuestConfig().getStringList(plugin.currentQuests.get(((Player) s)) + ".market.reward.items");
							((Player) s).sendMessage(ChatColor.YELLOW + "Rewards:");
							((Player) s).sendMessage(ChatColor.YELLOW + "	Money: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.money"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Health: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.health"));
							((Player) s).sendMessage(ChatColor.YELLOW + "	Food: " + ChatColor.GREEN + plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".market.reward.hunger"));
						}
					}
					else
					{
						((Player) s).sendMessage(ChatColor.RED + "You Don't Have A Active Quest!");
						((Player) s).sendMessage(ChatColor.LIGHT_PURPLE + "To Get A Quest Type: " + ChatColor.YELLOW + "/Quest GIVE");
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
						if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("collect")) {
							PlayerInventory inventory = ((Player) s).getInventory();
							ItemStack istack = new ItemStack(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.object.id"), plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount"));
							if (inventory.contains(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.object.id"), plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount"))) {
								//Remove items from Inventory...
								inventory.removeItem(istack);
								plugin.endQuest(((Player) s), "done");
							} 
							else 
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Not Met The Requirements Of This Quest!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("destroy"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount") <= plugin.doneItems.get(((Player) s)))
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
								((Player) s).sendMessage(ChatColor.RED + "You Have Broke Enough Blocks!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("damage"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount") <= plugin.doneItems.get(((Player) s)))
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
								((Player) s).sendMessage(ChatColor.RED + "You Have Broke Enough Blocks!");
							}
						}
						else if(plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.type").equalsIgnoreCase("kill"))
						{
							if(plugin.doneItems.get(((Player) s)) != null)
							{
								if(plugin.getQuestConfig().getInt(plugin.currentQuests.get(((Player) s)) + ".task.amount") <= plugin.doneItems.get(((Player) s)))
								{
									plugin.endQuest(((Player) s), "done");
								}
								else
								{
									((Player) s).sendMessage(ChatColor.RED + "You Have Killed Enough " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.object.name") + "s!");
								}
							}
							else
							{
								((Player) s).sendMessage(ChatColor.RED + "You Have Killed Enough " + plugin.getQuestConfig().getString(plugin.currentQuests.get(((Player) s)) + ".task.object.name") + "s!");
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