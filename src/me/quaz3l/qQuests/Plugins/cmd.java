package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.Chat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd implements CommandExecutor 
{    
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) 
	{
		if (s instanceof Player) 
		{
			if(args.length < 1)
			{
				Chat.message((Player) s, "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
			}
			else
			{
				if(args[0].equalsIgnoreCase("give"))
				{
					if (args.length == 1)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give"))
						{
							if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
								Chat.error((Player) s, "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/Quest info" + ChatColor.RED + " To Get More Info On Your Quest.");
							else
							{
								// Give Quest
							}
						}
						else
							Chat.noPerms((Player) s);
					}
					else if(args.length == 2)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give.specific"))
						{
							if(!qQuests.plugin.qAPI.hasActiveQuest((Player) s)) {
								if(qQuests.plugin.qAPI.getQuests().containsKey(args[1]))
								{
									// Give Quest
								}
								else
									Chat.error((Player) s, "This Isn't A Valid Quest!");
							}
							else
							{
								Chat.error((Player) s, "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/Quest info" + ChatColor.RED + " To Get More Info On Your Quest.");
							}
						}
					}
					else
						Chat.message((Player) s, "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "info"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Chat.noPrefixMessage((Player) s, ":======== Quest Info ========:");
							Chat.noPrefixMessage((Player) s, "Name: " + q.name());
							Chat.noPrefixMessage((Player) s, "Repeat: " + q.repeated());
							Chat.noPrefixMessage((Player) s, "Amount Of Tasks: " + q.tasks().size());
							Chat.noPrefixMessage((Player) s, "Tasks: " + q.tasks().toString());
							/* if(q.tasks().get(0).type() == "collect" || q.tasks().get(0).type() == "destroy" || q.tasks().get(0).type() == "damage" || q.tasks().get(0).type() == "place")
								Chat.noPrefixMessage((Player) s, q.tasks().get(0).type() + " " + q.tasks().get(0).amount() + " " + q.tasks().get(0).name() + "(ID:" + q.tasks().get(0).id() + ")");
							else if(q.tasks().get(0).type() == "kill")
								Chat.noPrefixMessage((Player) s, q.tasks().get(0).type() + " " + q.tasks().get(0).amount() + " " + q.tasks().get(0).name());
							else if(q.tasks().get(0).type() == "kill_player")
								Chat.noPrefixMessage((Player) s, "kill player '" + q.tasks().get(0).name() + "' " + q.tasks().get(0).amount() + " times");
								*/
						}
					}
					else
						Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "drop"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							// Drop Quest
						}
						else
							Chat.error((Player) s, "You Don't Have An Active Quest! Type " + ChatColor.YELLOW + "/Quest give" + ChatColor.RED + " To Get One.");
					}
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "done"))
					{
						// Finish Quest
					}
				}
				else
				{
					Chat.message((Player) s, "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop" + ChatColor.RED + "]");
				}
			}
		}
		else
			Chat.error(s, "Sorry A Quest Can Only Be Used By Players!");
		return false;
	}
}
