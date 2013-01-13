package me.quaz3l.qQuests.Plugins;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor 
{    
	@Override
	public boolean onCommand(final CommandSender s, Command c, String l, String[] args) 
	{
		if (s instanceof Player) 
		{
			/*
			if (args.length < 2) {
				s.sendMessage(ChatColor.RED + "You did not specify a name for this NPC!");
				s.sendMessage(ChatColor.GRAY + "/dwdnpc create [name]");
				return true;
			}
			final Location loc = ((Player) s).getLocation();
			final String name = (args[1].length() > 16) ? args[1].substring(0, 16) : args[1];
			qQuests.plugin.npcManager.spawnHumanNPC(name, loc);
	*/

			if(args.length < 1)
			{
				Texts.HELP(((Player) s).getName(), "commands");
			}
			else
			{
				if(Storage.wayCurrentQuestsWereGiven.get(((Player) s).getName()) != null)
					if(!Storage.access("commands", Storage.wayCurrentQuestsWereGiven.get(((Player) s).getName()), args[0]))
					{
						Chat.error((((Player) s).getName()), Texts.CANNOT_USE_CURRENTLY);
						return false;
					}
				if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("start"))
				{
					if (args.length == 1)
					{
						if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "give"))
						{
							Integer result = qQuests.plugin.qAPI.giveQuest((((Player) s).getName()), "commands");
							if(result == 0)
							{
								Storage.wayCurrentQuestsWereGiven.put((((Player) s).getName()), "commands");
								Chat.message((((Player) s).getName()), qQuests.plugin.qAPI.getActiveQuest((((Player) s).getName())).onJoin().message(((Player) s).getName()));
							}
							else if(result == 1)
							{
								Chat.error((((Player) s).getName()), Texts.NO_QUESTS_AVAILABLE);
							}
							else
								Chat.error(((Player) s).getName(), Chat.errorCode(result, "Commands", ((Player)s).getName()));
						}
						else Chat.noPerms(((Player) s).getName());
					}
					else if(args.length >= 2)
					{
						if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "give.specific"))
						{
							String fullName = args[1];
							if(args.length > 2) {
								for(int i=2; i < args.length; i++) {
									fullName +=  " " + args[i];
								}
							}
							Integer result = qQuests.plugin.qAPI.giveQuest(((Player) s).getName(), fullName, true, "commands");
							if(result == 0)
							{
								Storage.wayCurrentQuestsWereGiven.put((((Player) s).getName()), "commands");
								Chat.message((((Player) s).getName()), qQuests.plugin.qAPI.getActiveQuest(((Player) s).getName()).onJoin().message(((Player) s).getName()));
							}
							else if(result == 1)
								Chat.error(((Player) s).getName(), Texts.NOT_VALID_QUEST);
							else
								Chat.error(((Player) s).getName(), Chat.errorCode(result, "Commands", ((Player)s).getName()));
						}
						else Chat.noPerms(((Player) s).getName());
					}
					else
						Texts.HELP(((Player) s).getName(), "commands");
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "info"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest(((Player) s).getName()))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest(((Player) s).getName());
							Texts.INFO(q, ((Player) s).getName());
						}
						else Chat.error(((Player) s).getName(), Texts.NO_ACTIVE_QUEST);
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("tasks") || args[0].equalsIgnoreCase("progress")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "tasks"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest(((Player) s).getName()))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest(((Player) s).getName());
							Chat.noPrefixMessage(((Player) s).getName(), ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Tasks " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							int i=0;
							while(q.tasks().size() > i) 
							{
								if(q.tasks().get(i).type().equalsIgnoreCase("collect"))
									if(Storage.info.showItemIds)
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
									if(Storage.info.showItemIds)
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
									if(Storage.info.showItemIds)
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
									if(Storage.info.showItemIds)
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
									Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
									Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
								else if(q.tasks().get(i).type().equalsIgnoreCase("enchant"))
									if(Storage.info.showItemIds)
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("tame"))
									Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Tame " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								i++;
							}
						} else Chat.error(((Player) s).getName(), Texts.NO_ACTIVE_QUEST);
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "drop"))
					{
						Quest q = qQuests.plugin.qAPI.getActiveQuest(((Player) s).getName());
						Integer result = qQuests.plugin.qAPI.dropQuest(((Player) s).getName());
						if(result == 0)
							Chat.message((((Player) s).getName()), q.onDrop().message(((Player) s).getName()));
						else
							Chat.error(((Player) s).getName(), Chat.errorCode(result, "Commands", ((Player)s).getName()));
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "done"))
					{
						Integer result = qQuests.plugin.qAPI.completeQuest(((Player) s).getName());
						if(result != 0)
							Chat.error(((Player) s).getName(), Chat.errorCode(result, "Commands", ((Player)s).getName()));
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("reload")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "reload"))
					{
						Bukkit.getPluginManager().disablePlugin(qQuests.plugin);
						Bukkit.getPluginManager().enablePlugin(qQuests.plugin);
						Chat.logger("info", "Reloaded.");
						Chat.green(((Player) s).getName(), "Reloaded.");
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("list")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "list"))
					{
						HashMap<Integer, Quest> q = qQuests.plugin.qAPI.getAvailableQuests(((Player) s).getName());
						if(qQuests.plugin.qAPI.hasActiveQuest(((Player) s).getName()))
							Texts.INFO(qQuests.plugin.qAPI.getActiveQuest(((Player) s).getName()), ((Player) s).getName());
						else if(Storage.cannotGetQuests.contains(((Player) s).getName()))
							Chat.error(((Player) s).getName(), Texts.DELAY_NOT_FINISHED);
						else if(qQuests.plugin.qAPI.getVisibleQuests().size() == 0)
							Chat.error(((Player) s).getName(), Texts.NO_QUESTS_AVAILABLE);
						else if(!q.isEmpty())
						{
							Chat.noPrefixMessage(((Player) s).getName(), ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Available Quests " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							Integer i=1;
							for(Quest a : q.values()) {
								Chat.noPrefixMessage(((Player) s).getName(), ChatColor.GREEN + i.toString() + ". " + ChatColor.LIGHT_PURPLE + a.name());
								i++;
							}
						}
						else Chat.error(((Player) s).getName(), Texts.NO_QUESTS_AVAILABLE);
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else if(args[0].equalsIgnoreCase("stats")) 
				{
					if(qQuests.plugin.qAPI.checkPerms(((Player) s).getName(), "stats"))
					{
						Chat.noPrefixMessage(((Player) s).getName(), "Level: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(((Player) s).getName(), "Level"));
						Chat.noPrefixMessage(((Player) s).getName(), "Quests Given: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(((Player) s).getName(), "Given"));
						Chat.noPrefixMessage(((Player) s).getName(), "Quests Dropped: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(((Player) s).getName(), "Dropped"));
						Chat.noPrefixMessage(((Player) s).getName(), "Quests Completed: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(((Player) s).getName(), "Completed"));
					}
					else Chat.noPerms(((Player) s).getName());
				}
				else
				{
					Texts.HELP(((Player) s).getName(), "commands");
				}
			}
		}
		else if(s instanceof ConsoleCommandSender) {
			if(args.length < 1)
			{
				Chat.message(s, "qquests reload - Reloads the quests.yml, and config.yml");
			}
			else
			{ 
				if(args[0].equalsIgnoreCase("reload")) 
				{
					Bukkit.getPluginManager().disablePlugin(qQuests.plugin);
					Bukkit.getPluginManager().enablePlugin(qQuests.plugin);
					Chat.logger("info", "Reloaded.");
				}
				else 
					Chat.message(s, "qquests reload - Reloads the quests.yml, and config.yml");
			}
		} else
			Chat.message(s, Texts.ONLY_PLAYERS);
		return false;
	}
}
