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
				Chat.noPrefixMessage((Player) s, "/q " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
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
								Chat.error((Player) s, "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/q info" + ChatColor.RED + " To Get More Info On Your Quest.");
							else
							{
								Quest q =qQuests.plugin.qAPI.giveQuest((Player) s);
								if(q != null)
								{
									qQuests.plugin.qAPI.getActiveQuests().put(((Player) s), q);
									Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
								}
								else
								{
									Chat.error((Player) s, "You Don't Have Enough To Get This Quest!");
								}
							}
						}
						else Chat.noPerms((Player) s);
					}
					else if(args.length == 2)
					{
						if(qQuests.plugin.qAPI.checkPerms((Player) s, "give.specific"))
						{
							if(!qQuests.plugin.qAPI.hasActiveQuest((Player) s)) {
								if(qQuests.plugin.qAPI.getQuestWorker().getQuests().containsKey(args[1]))
								{
									qQuests.plugin.qAPI.getActiveQuests().put(((Player) s), qQuests.plugin.qAPI.getQuestWorker().getQuests().get(args[1]));
									Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
								}
								else
									Chat.error((Player) s, "This Isn't A Valid Quest!");
							}
							else
							{
								Chat.error((Player) s, "You Already Have An Active Quest! Type " + ChatColor.YELLOW + "/q info" + ChatColor.RED + " To Get More Info On Your Quest.");
							}
						}
						else Chat.noPerms((Player) s);
					}
					else
						Chat.noPrefixMessage((Player) s, "/q " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop, done" + ChatColor.RED + "]");
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
							int i=0;
							while(q.tasks().size() > i) 
							{
								if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + "(ID:" + q.tasks().get(i).idInt() + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + "(ID:" + q.tasks().get(i).idInt() + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + "(ID:" + q.tasks().get(i).idInt() + ")");
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
								else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Kill the player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " times");
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
							qQuests.plugin.qAPI.dropQuest((Player) s);
						else
							Chat.error((Player) s, "You Don't Have An Active Quest! Type " + ChatColor.YELLOW + "/q give" + ChatColor.RED + " To Get One.");
					}
					else Chat.noPerms((Player) s);
				}
				else if(args[0].equalsIgnoreCase("done")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "done"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
							if(qQuests.plugin.qAPI.completeQuest((Player) s))
								Chat.done((Player) s, "Quest Completed!");
							else
								Chat.error((Player) s, "You Haven't Completed All The Tasks! Type " + ChatColor.YELLOW + "/q info" + ChatColor.RED + " See Them.");
						else
							Chat.error((Player) s, "You Don't Have An Active Quest! Type " + ChatColor.YELLOW + "/q give" + ChatColor.RED + " To Get One.");
					}
					else Chat.noPerms((Player) s);
				}
				else
				{
					Chat.noPrefixMessage((Player) s, "/q " + ChatColor.RED + "[" + ChatColor.YELLOW + "give, info, drop" + ChatColor.RED + "]");
				}
			}
		}
		else
			Chat.error(s, "Sorry A Quest Can Only Be Used By Players!");
		return false;
	}
}
