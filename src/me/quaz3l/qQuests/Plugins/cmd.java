package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.Util.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

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
							if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
								Chat.error((Player) s, Texts.HAS_ACTIVE_QUEST);
							else
							{
								Quest q =qQuests.plugin.qAPI.giveQuest((Player) s);
								if(q != null)
								{
									qQuests.plugin.qAPI.getActiveQuests().put(((Player) s), q);
									Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
									Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
								}
								else
								{
									Chat.error((Player) s, Texts.NOT_ENOUGH_FOR_QUEST);
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
									Storage.wayCurrentQuestsWereGiven.put(((Player) s), "Commands");
									Chat.message(((Player) s), qQuests.plugin.qAPI.getActiveQuest((Player) s).onJoin().message());
								}
								else
									Chat.error((Player) s, Texts.NOT_VALID_QUEST);
							}
							else
							{
								Chat.error((Player) s, Texts.HAS_ACTIVE_QUEST);
							}
						}
						else Chat.noPerms((Player) s);
					}
					else
						Chat.noPrefixMessage((Player) s, Texts.HELP_TEXT);
				}
				else if(args[0].equalsIgnoreCase("tasks")) 
				{
					if(qQuests.plugin.qAPI.checkPerms((Player) s, "tasks"))
					{
						if(qQuests.plugin.qAPI.hasActiveQuest((Player) s))
						{
							Quest q = qQuests.plugin.qAPI.getActiveQuest((Player) s);
							Chat.noPrefixMessage((Player) s, ":======== Quest Tasks ========:");
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
									Chat.noPrefixMessage((Player) s, (i + 1) + ". Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
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
							String msg = qQuests.plugin.qAPI.getActiveQuest((Player) s).onComplete().message();
							if(qQuests.plugin.qAPI.completeQuest((Player) s))
								Chat.green((Player) s, msg);
							else
								Chat.error((Player) s, Texts.TASKS_NOT_COMPLETED);
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
