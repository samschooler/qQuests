package me.quaz3l.qQuests.Plugins;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Interwebs;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
			if(args.length < 1)
			{
				Chat.noPrefixMessage((Player) s, Texts.COMMANDS_HELP_TEXT);
			}
			else
			{
				if(Storage.wayCurrentQuestsWereGiven.get((Player) s) != null)
					if(!Storage.wayCurrentQuestsWereGiven.get((Player) s).equalsIgnoreCase("Commands"))
					{
						Chat.error(((Player) s), Texts.NOT_CONTROLLED_BY((Player) s));
						return false;
					}
				if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("start"))
				{
					if (args.length == 1)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give"))
						{
							Integer result = qQuests.plugin.qAPI.giveQuest((Player) s, "Commands");
							if(result == 0)
							{
								Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
								Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
							}
							else if(result == 1)
							{
								Chat.error(((Player) s), Texts.NO_QUESTS_AVAILABLE);
							}
							else
								Chat.error((Player) s, Chat.errorCode(result, "Commands"));
						}
						else Chat.noPerms((Player) s);
					}
					else if(args.length >= 2)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give.specific"))
						{
							String fullName = args[1];
							if(args.length > 2) {
								for(int i=2; i < args.length; i++) {
									fullName += args[i];
								}
							}
							Integer result = qQuests.plugin.qAPI.giveQuest((Player) s, fullName, true, "Commands");
							if(result == 0)
							{
								Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
								Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
							}
							else if(result == 1)
								Chat.error((Player) s, Texts.NOT_VALID_QUEST);
							else
								Chat.error((Player) s, Chat.errorCode(result, "Commands"));
						}
						else Chat.noPerms((Player) s);
					}
					else
						Chat.noPrefixMessage((Player) s, Texts.COMMANDS_HELP_TEXT);
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "info"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Chat.noPrefixMessage((Player) s, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Info " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							Chat.noPrefixMessage((Player) s, "Name: " + ChatColor.GREEN + q.name());
							if(q.onComplete().nextQuest() != null && !q.onComplete().nextQuest().isEmpty())
								Chat.noPrefixMessage((Player) s, "Next Quest: " + ChatColor.GREEN + q.onComplete().nextQuest());
							if(q.repeated() == -1)
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + "Infinite");
							else if((q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted((Player) s, q)) == 0)
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + "None");
							else
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + (q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted((Player) s, q)) + " More Times");
							Chat.noPrefixMessage((Player) s, "Tasks: " + ChatColor.YELLOW + Texts.PRIMARY_COMMAND + " " + Texts.TASKS_COMMAND + ChatColor.GREEN + " For The Tasks.");
							Chat.noPrefixMessage((Player) s, "Rewards:");
							if(Storage.showMoney)
								if(qQuests.plugin.economy != null && q.onComplete().money() != 0)
									Chat.noPrefixMessage((Player) s, "     " + Texts.MONEY + ": " + ChatColor.GREEN + q.onComplete().money());
							if(Storage.showHealth)
								if(q.onComplete().health() != 0)
									Chat.noPrefixMessage((Player) s, "     " + Texts.HEALTH + ": " + ChatColor.GREEN + q.onComplete().health());
							if(Storage.showFood)
								if(q.onComplete().hunger() != 0)
									Chat.noPrefixMessage((Player) s, "     " + Texts.FOOD + ": " + ChatColor.GREEN + q.onComplete().hunger());
							if(Storage.showLevelsAdded)
								if(q.onComplete().levelAdd() != 0)
									Chat.noPrefixMessage((Player) s, "     " + Texts.LEVELADD + ": " + ChatColor.GREEN + q.onComplete().levelAdd());
							if(Storage.showSetLevel)
								if(q.onComplete().levelSet() != -1)
									Chat.noPrefixMessage((Player) s, "     " + Texts.LEVELSET + ": " + ChatColor.GREEN + q.onComplete().levelSet());
							if(Storage.showCommands)
								if(q.onComplete().items().size() > 0)
								{
									Chat.noPrefixMessage((Player) s, "     " + Texts.COMMANDS + ":");
									for(int i=0;i<(q.onComplete().commands().size()); i++)
									{
										Chat.noPrefixMessage((Player) s, "     " + ChatColor.GREEN + "- /" + ChatColor.GOLD + q.onComplete().commands().get(i).replace("`player", ((Player) s).getName()));
									}
								}
							if(Storage.showItems)
								if(q.onComplete().items().size() > 0)
								{
									Chat.noPrefixMessage((Player) s, "     " + Texts.ITEMS + ":");
									for(int i=0;i<(q.onComplete().items().size()); i++)
									{
										Chat.noPrefixMessage((Player) s, "     " + ChatColor.GREEN + q.onComplete().items().get(i).get(1).toString() + " " + ChatColor.GOLD + Material.getMaterial(q.onComplete().items().get(i).get(0)).toString());
									}
								}
						}
						else Chat.error((Player) s, Texts.NO_ACTIVE_QUEST);
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("tasks") || args[0].equalsIgnoreCase("progress")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "tasks"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Chat.noPrefixMessage((Player) s, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Tasks " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							int i=0;
							while(q.tasks().size() > i) 
							{
								if(q.tasks().get(i).type().equalsIgnoreCase("collect"))
									if(Storage.showItemIds)
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
									if(Storage.showItemIds)
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
									if(Storage.showItemIds)
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
									if(Storage.showItemIds)
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
									else
										Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
								else if(q.tasks().get(i).type().equalsIgnoreCase("enchant"))
									if(Storage.showItemIds)
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
								else
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("tame"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Tame " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								i++;
							}
						} else Chat.error((Player) s, Texts.NO_ACTIVE_QUEST);
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "drop"))
					{
						Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
						Integer result = qQuests.plugin.qAPI.dropQuest((Player) s);
						if(result == 0)
							Chat.message(((Player) s), q.onDrop().message());
						else
							Chat.error((Player) s, Chat.errorCode(result, "Commands"));
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "done"))
					{
						Integer result = qQuests.plugin.qAPI.completeQuest((Player) s);
						if(result != 0)
							Chat.error((Player) s, Chat.errorCode(result, "Commands"));
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("reload")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "reload"))
					{
						qQuests.plugin.qAPI.Index.unloadPlugin();
						qQuests.plugin.qAPI.Index.loadPlugin();
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("list")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "list"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
							Chat.error((Player) s, Texts.HAS_ACTIVE_QUEST);
						if(Storage.cannotGetQuests.contains((Player) s))
							Chat.error((Player) s, Texts.DELAY_NOT_FINISHED);
						if(qQuests.plugin.qAPI.getVisibleQuests().size() == 0)
							Chat.error((Player) s, Texts.NO_QUESTS_AVAILABLE);
						HashMap<Integer, Quest> q = qQuests.plugin.qAPI.getAvailableQuests((Player) s);
						if(!q.isEmpty())
						{
							Chat.noPrefixMessage((Player) s, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Available Quests " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							Integer i=1;
							for(Quest a : q.values()) {
								Chat.noPrefixMessage((Player) s, ChatColor.GREEN + i.toString() + ". " + ChatColor.LIGHT_PURPLE + a.name());
								i++;
							}
						}
						else Chat.error((Player) s, Texts.NO_QUESTS_AVAILABLE);
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("stats")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "stats"))
					{
						Chat.noPrefixMessage((Player) s, "Level: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt((Player) s, "Level"));
						Chat.noPrefixMessage((Player) s, "Quests Given: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt((Player) s, "Given"));
						Chat.noPrefixMessage((Player) s, "Quests Dropped: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt((Player) s, "Dropped"));
						Chat.noPrefixMessage((Player) s, "Quests Completed: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt((Player) s, "Completed"));
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("update"))
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "update"))
					{
						if(!Interwebs.tryUpdate(s))
							Chat.message(s, "qQuests is up to date.");
					}
				}
				else
				{
					Chat.noPrefixMessage((Player) s, Texts.COMMANDS_HELP_TEXT);
				}
			}
		}
		else if(s instanceof ConsoleCommandSender) {
			if(args[0].equalsIgnoreCase("update"))
			{
				if(!Interwebs.tryUpdate(s))
					Chat.message(s, "qQuests is up to date.");
			} else 
				Chat.message(s, Texts.ONLY_PLAYERS);
		} else
			Chat.message(s, Texts.ONLY_PLAYERS);
		return false;
	}
}
