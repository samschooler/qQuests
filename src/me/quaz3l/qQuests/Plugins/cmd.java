package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Util.Quest;

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
				((Player) s).sendMessage(qQuests.plugin.chatPrefix + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
				return false;
			}
			else
			{
				if((args[0].equalsIgnoreCase("give")))
				{
					if (args.length < 2)
					{
						if(!qQuests.plugin.qAPI.hasActiveQuest((Player) s)) {
							Quest q = qQuests.plugin.qAPI.giveQuest(((Player) s));
							((Player) s).sendMessage(qQuests.plugin.chatPrefix + q.onJoin().message);
						}
						else
						{
							((Player) s).sendMessage(qQuests.plugin.chatPrefix + ChatColor.RED + "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/q info" + ChatColor.RED + " To Get More Info On Your Quest.");
						}
						return true;
					}
					else
					{
						if(!qQuests.plugin.qAPI.hasActiveQuest((Player) s)) {
							if(qQuests.plugin.qAPI.getQuests().containsKey(args[1]))
							{
								Quest q = qQuests.plugin.qAPI.giveQuest(((Player) s), args[1]);
								((Player) s).sendMessage(qQuests.plugin.chatPrefix + q.onJoin().message);
							}
							else
								((Player) s).sendMessage(qQuests.plugin.chatPrefix + ChatColor.RED + "This Isn't A Valid Quest!");
						}
						else
						{
							((Player) s).sendMessage(qQuests.plugin.chatPrefix + ChatColor.RED + "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/q info" + ChatColor.RED + " To Get More Info On Your Quest.");
						}
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("info")) 
				{
					// TODO Info On Quests
					return true;
				}
				else if(args[0].equalsIgnoreCase("drop")) 
				{
					// TODO Drop Quests
					return true;
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					// TODO Finish Quests
					return true;
				}
				else
				{
					((Player) s).sendMessage(qQuests.plugin.chatPrefix + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop" + ChatColor.RED + "]");
					return false;
				}
			}
		}
		else
		{
			s.sendMessage(qQuests.plugin.prefix + ChatColor.RED + "Sorry A Quest Can Only Be Used By Players!");
			return false;
		}
	}
}
