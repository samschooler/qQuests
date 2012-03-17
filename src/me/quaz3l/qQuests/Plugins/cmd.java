package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd implements CommandExecutor 
{    
	@Override
	public boolean onCommand(final CommandSender s, Command c, String l, String[] args) 
	{
		if (s instanceof Player) 
		{
			if(args.length < 1)
			{
				Chat.noPrefixMessage((Player) s, Texts.HELP_TEXT);
			}
			else
			{
				if(Storage.wayCurrentQuestsWereGiven.get((Player) s) != null)
					if(!Storage.wayCurrentQuestsWereGiven.get((Player) s).equalsIgnoreCase("Commands"))
					{
						Chat.message(((Player) s), Texts.NOT_CONTROLLED_BY((Player) s));
						return false;
					}
				if(args[0].equalsIgnoreCase("give"))
				{
					if (args.length == 1)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give"))
						{
							Integer result = qQuests.plugin.qAPI.giveQuest((Player) s);
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
								Chat.error((Player) s, Chat.errorCode(result));
						}
						else Chat.noPerms((Player) s);
					}
					else if(args.length == 2)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give.specific"))
						{
							Integer result = qQuests.plugin.qAPI.giveQuest((Player) s, args[1].toLowerCase(), true);
							if(result == 0)
							{
								Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
								Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
							}
							else if(result == 1)
								Chat.error((Player) s, Texts.NOT_VALID_QUEST);
							else
								Chat.error((Player) s, Chat.errorCode(result));
						}
						else Chat.noPerms((Player) s);
					}
					else
						Chat.noPrefixMessage((Player) s, Texts.HELP_TEXT);
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "tasks"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Chat.noPrefixMessage((Player) s, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Info " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
							Chat.noPrefixMessage((Player) s, "Name: " + ChatColor.GREEN + q.name());
							if(q.nextQuest() != null && !q.nextQuest().isEmpty())
								Chat.noPrefixMessage((Player) s, "Next Quest: " + ChatColor.GREEN + q.nextQuest());
							if(q.repeated() == -1)
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + "Infinite");
							else if((q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted((Player) s, q)) == 0)
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + "None");
							else
								Chat.noPrefixMessage((Player) s, "Repeatable: " + ChatColor.GREEN + (q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted((Player) s, q)) + " More Times");
							Chat.noPrefixMessage((Player) s, "Tasks: " + ChatColor.YELLOW + Texts.PRIMARY_COMMAND + " " + Texts.TASKS_COMMAND + ChatColor.GREEN + " For The Tasks.");
							Chat.noPrefixMessage((Player) s, "Rewards:");
							if(qQuests.plugin.economy != null && q.onComplete().money() != 0)
								Chat.noPrefixMessage((Player) s, "     " + Texts.MONEY + ": " + ChatColor.GREEN + q.onComplete().money());
							if(q.onComplete().health() != 0)
								Chat.noPrefixMessage((Player) s, "     " + Texts.HEALTH + ": " + ChatColor.GREEN + q.onComplete().health());
							if(q.onComplete().hunger() != 0)
								Chat.noPrefixMessage((Player) s, "     " + Texts.FOOD + ": " + ChatColor.GREEN + q.onComplete().hunger());
							if(q.onComplete().items().size() > 0)
							{
								Chat.noPrefixMessage((Player) s, "     " + Texts.ITEMS + ":");
								for(int i=0;i<(q.onComplete().items().size()); i++)
								{
									Chat.noPrefixMessage((Player) s, "     " + ChatColor.GREEN + q.onComplete().items().get(i).get(1).toString() + " " + ChatColor.GOLD + Material.getMaterial(q.onComplete().items().get(i).get(0)).toString());
								}
							}
						}
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("tasks")) 
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
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
									Chat.noPrefixMessage((Player) s, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
								i++;
							}
						}
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "drop"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onDrop().message());
							qQuests.plugin.qAPI.dropQuest((Player) s);
						}
						else
							Chat.error((Player) s, Texts.HAS_ACTIVE_QUEST);
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "done"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Integer result = qQuests.plugin.qAPI.completeQuest((Player) s);
							if(result == 0)
							{
								Chat.green((Player) s, q.onComplete().message());
								
								// Delay
								if(q.delay() > 0)
								{
									Storage.cannotGetQuests.add((Player) s);
									qQuests.plugin.getServer().getScheduler().scheduleSyncDelayedTask(qQuests.plugin, new Runnable() {

									    public void run() {
									    	Storage.cannotGetQuests.remove((Player) s);
									    }
									}, (q.delay() * 1200));
								}
								
								// Next Quest
								if(q.nextQuest() != null)
								{
									if(qQuests.plugin.qAPI.getQuestWorker().getQuests().containsKey(q.nextQuest().toLowerCase()))
									{
										result = qQuests.plugin.qAPI.giveQuest((Player) s, q.nextQuest().toLowerCase(), false);
										if(result == 0)
										{
											qQuests.plugin.qAPI.getActiveQuests().put(((Player) s), qQuests.plugin.qAPI.getQuestWorker().getQuests().get(q.nextQuest().toLowerCase()));
											Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
											Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
										}
										else
											Chat.error((Player) s, Chat.errorCode(result));
									}
									else if(!q.nextQuest().isEmpty())
										Chat.logger("warning", Texts.QUEST + " '" + q.name() + "' " + Texts.INVALID + " " + Texts.NEXT_QUEST + "! '" + q.nextQuest() + "'");
								}
							}
							else
								Chat.error((Player) s, Chat.errorCode(result));
						}
						else
							Chat.error((Player) s, Texts.NO_ACTIVE_QUEST);
					}
					else Chat.noPerms((Player) s);
				}
				else
				{
					Chat.noPrefixMessage((Player) s, Texts.HELP_TEXT);
				}
			}
		}
		else
			Chat.message(s, Texts.ONLY_PLAYERS);
		return false;
	}
}
