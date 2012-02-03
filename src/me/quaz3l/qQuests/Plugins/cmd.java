package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmd implements CommandExecutor 
{
	private qQuests plugin;
    public cmd(qQuests plugin) 
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
				((Player) s).sendMessage(plugin.chatPrefix + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
				return false;
			}
			else
			{
				if((args[0].equalsIgnoreCase("give")))
				{
					if (args.length < 2)
					{
						
						return true;
					}
					else
					{
						// TODO Give Specified
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
					((Player) s).sendMessage(plugin.chatPrefix + "/Quest " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop" + ChatColor.RED + "]");
					return false;
				}
			}
		}
		else
		{
			s.sendMessage(plugin.prefix + ChatColor.RED + "Sorry A Quest Can Only Be Used By Players!");
			return false;
		}
	}
}
