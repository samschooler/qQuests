package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Action action = e.getAction();
		if(action != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getClickedBlock().getType() != Material.WALL_SIGN && 
				e.getClickedBlock().getType() != Material.SIGN_POST && 
				e.getClickedBlock().getType() != Material.SIGN)
			return;
		Sign sign = (Sign) e.getClickedBlock().getState();
		if(!sign.getLine(0).equalsIgnoreCase("[qQuests]"))
			return;
		if(sign.getLine(2).equalsIgnoreCase("give") || !sign.getLine(1).isEmpty())
		{
			if(!qQuests.plugin.qAPI.getQuests().containsKey(sign.getLine(1).toLowerCase()))
			{
				Chat.error(e.getPlayer(), "The quest is not a vaild quest on line 2 of the sign!");
				return;
			}
		}
		if(!sign.getLine(1).isEmpty() && !sign.getLine(2).equalsIgnoreCase("give"))
		{
			if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
			{
				if(!qQuests.plugin.qAPI.getActiveQuests().get(e.getPlayer()).name().equalsIgnoreCase(sign.getLine(1)))
				{
					Chat.error(e.getPlayer(), "This quest does not match your quest!");
					return;
				}
			}
		}
		if(sign.getLine(2).isEmpty())
		{
			Chat.error(e.getPlayer(), "There is no action on line 3! (Give, Info, Tasks, Drop, Done)");
			return;
			
		}
		if(sign.getLine(2).equalsIgnoreCase("give"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "give.sign"))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(e.getPlayer(), sign.getLine(1).toLowerCase(), false);
				if(result == 0)
				{
					Storage.wayCurrentQuestsWereGiven.put(e.getPlayer(), "Signs");
					Chat.message(e.getPlayer(), qQuests.plugin.qAPI.getActiveQuest(e.getPlayer()).onJoin().message());
				}
				else if(result == 1)
					Chat.error(e.getPlayer(), Texts.NOT_VALID_QUEST);
				else
					Chat.error(e.getPlayer(), Chat.errorCode(result));
			}
			else Chat.noPerms(e.getPlayer());
			
		}
		else if(sign.getLine(2).equalsIgnoreCase("info"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "info.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
				{
					Quest q = qQuests.plugin.qAPI.getActiveQuest(e.getPlayer());
					Chat.noPrefixMessage(e.getPlayer(), ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Info " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
					Chat.noPrefixMessage(e.getPlayer(), "Name: " + ChatColor.GREEN + q.name());
					if(q.nextQuest() != null && !q.nextQuest().isEmpty())
						Chat.noPrefixMessage(e.getPlayer(), "Next Quest: " + ChatColor.GREEN + q.nextQuest());
					if(q.repeated() == -1)
						Chat.noPrefixMessage(e.getPlayer(), "Repeatable: " + ChatColor.GREEN + "Infinite");
					else if((q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted(e.getPlayer(), q)) == 0)
						Chat.noPrefixMessage(e.getPlayer(), "Repeatable: " + ChatColor.GREEN + "None");
					else
						Chat.noPrefixMessage(e.getPlayer(), "Repeatable: " + ChatColor.GREEN + (q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted(e.getPlayer(), q)) + " More Times");
					Chat.noPrefixMessage(e.getPlayer(), "Tasks: " + ChatColor.YELLOW + Texts.PRIMARY_COMMAND + " " + Texts.TASKS_COMMAND + ChatColor.GREEN + " For The Tasks.");
					Chat.noPrefixMessage(e.getPlayer(), "Rewards:");
					if(qQuests.plugin.economy != null && q.onComplete().money() != 0)
						Chat.noPrefixMessage(e.getPlayer(), "     " + Texts.MONEY + ": " + ChatColor.GREEN + q.onComplete().money());
					if(q.onComplete().health() != 0)
						Chat.noPrefixMessage(e.getPlayer(), "     " + Texts.HEALTH + ": " + ChatColor.GREEN + q.onComplete().health());
					if(q.onComplete().hunger() != 0)
						Chat.noPrefixMessage(e.getPlayer(), "     " + Texts.FOOD + ": " + ChatColor.GREEN + q.onComplete().hunger());
					if(q.onComplete().items().size() > 0)
					{
						Chat.noPrefixMessage(e.getPlayer(), "     " + Texts.ITEMS + ":");
						for(int i=0;i<(q.onComplete().items().size()); i++)
						{
							Chat.noPrefixMessage(e.getPlayer(), "     " + ChatColor.GREEN + q.onComplete().items().get(i).get(1).toString() + " " + ChatColor.GOLD + Material.getMaterial(q.onComplete().items().get(i).get(0)).toString());
						}
					}
				}
				else Chat.error(e.getPlayer(), Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(sign.getLine(2).equalsIgnoreCase("tasks"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "tasks.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
				{
					Quest q = qQuests.plugin.qAPI.getActiveQuest(e.getPlayer());
					Chat.noPrefixMessage(e.getPlayer(), ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Tasks " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
					int i=0;
					while(q.tasks().size() > i) 
					{
						if(q.tasks().get(i).type().equalsIgnoreCase("collect"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
						else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
						else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
						else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
						else if(q.tasks().get(i).type().equalsIgnoreCase("enchant"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
						else if(q.tasks().get(i).type().equalsIgnoreCase("tame"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Tame " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.YELLOW + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.YELLOW + ")");
						i++;
					}
				} else Chat.error(e.getPlayer(), Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(sign.getLine(2).equalsIgnoreCase("drop"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "drop"))
			{
				Quest q = qQuests.plugin.qAPI.getActiveQuest(e.getPlayer());
				Integer result = qQuests.plugin.qAPI.dropQuest(e.getPlayer());
				if(result == 0)
					Chat.message((e.getPlayer()), q.onDrop().message());
				else
					Chat.error(e.getPlayer(), Chat.errorCode(result));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(sign.getLine(2).equalsIgnoreCase("done"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "done"))
			{
				Integer result = qQuests.plugin.qAPI.completeQuest(e.getPlayer());
				if(result != 0)
					Chat.error(e.getPlayer(), Chat.errorCode(result));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else
		{
			Chat.error(e.getPlayer(), "This is not a valid action on line 3! (Give, Info, Tasks, Drop, Done)");
			return;
		}
	}
}
