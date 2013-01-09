package me.quaz3l.qQuests.Plugins;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.QuestFrag;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Signs implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) 
	{
		if(e.isCancelled())
			return;
		
		Block blockAbove = e.getBlock().getLocation().getWorld().getBlockAt(new Location(e.getBlock().getLocation().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY()+1, e.getBlock().getLocation().getZ()));
		if((e.getBlock().getType() == Material.WALL_SIGN || 
				e.getBlock().getType() == Material.SIGN_POST || 
				e.getBlock().getType() == Material.SIGN))
		{
			Sign sign = (Sign) e.getBlock().getState();
			if(!getLine(sign, 0).equalsIgnoreCase("[qQuests]") && !getLine(sign, 0).equalsIgnoreCase("[Quest]") && !getLine(sign, 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim()))
				return;
			else
				if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "destroy.sign"))
					e.setCancelled(true);
		}
		else if((blockAbove.getType() == Material.WALL_SIGN || 
					blockAbove.getType() == Material.SIGN_POST || 
					blockAbove.getType() == Material.SIGN)) 
		{
			Sign sign = (Sign) blockAbove.getState();
			if(!getLine(sign, 0).equalsIgnoreCase("[qQuests]") && !getLine(sign, 0).equalsIgnoreCase("[Quest]") && !getLine(sign, 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim()))
				return;
			else
				if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "destroy.sign"))
					e.setCancelled(true);
		}
		else return;
	}
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		Chat.logger("debug", getLine(e.getLines(), 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim())+"");
		Chat.logger("debug", "Prefix: "+Chat.removeColors(qQuests.plugin.chatPrefix).trim());
		Chat.logger("debug", "Line 1: "+e.getLine(0));
		if(!getLine(e.getLines(), 0).equalsIgnoreCase("[qQuests]") && !getLine(e.getLines(), 0).equalsIgnoreCase("[Quest]") && !getLine(e.getLines(), 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim()))
			return;
		if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "create.sign"))
		{
			Chat.noPerms(e.getPlayer());
			dropSign(e);
		}
		else {
			// Default Format
			
			// Line 0
			e.setLine(0, ChatColor.WHITE + e.getLine(0));
			
			// Line 2
			if(getLine(e.getLines(), 2).equalsIgnoreCase("give") || getLine(e.getLines(), 2).equalsIgnoreCase("start")) {
				e.setLine(2, ChatColor.DARK_BLUE + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer(), "Quest sign created! This will give a random visible quest.");
				else
					Chat.green(e.getPlayer(), "Quest sign created! This will give the " + getLine(e.getLines(), 1) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("tasks") || getLine(e.getLines(), 2).equalsIgnoreCase("progress") || getLine(e.getLines(), 2).equalsIgnoreCase("info") || getLine(e.getLines(), 2).equalsIgnoreCase("list")) {
				e.setLine(2, ChatColor.GOLD + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer(), "Quest sign created! This will show for tasks any quest.");
				else
					Chat.green(e.getPlayer(), "Quest sign created! This will show tasks for the " + getLine(e.getLines(), 1) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("drop")) {
				e.setLine(2, ChatColor.DARK_RED + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer(), "Quest sign created! This will drop any quest.");
				else
					Chat.green(e.getPlayer(), "Quest sign created! This will drop the " + getLine(e.getLines(), 1) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("done") || getLine(e.getLines(), 2).equalsIgnoreCase("finish") || getLine(e.getLines(), 2).equalsIgnoreCase("end")) {
				e.setLine(2, ChatColor.GREEN + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer(), "Quest sign created! This will complete any quest.");
				else
					Chat.green(e.getPlayer(), "Quest sign created! This will complete the " + getLine(e.getLines(), 1) + " quest.");
			}
		}
		
	}
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
		if(!getLine(sign, 0).equalsIgnoreCase("[qQuests]") && !getLine(sign, 0).equalsIgnoreCase("[Quest]") && !getLine(sign, 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim()))
			return;
		e.setCancelled(true);
		if(Storage.wayCurrentQuestsWereGiven.get(e.getPlayer()) != null)
			if(!Storage.access("signs", Storage.wayCurrentQuestsWereGiven.get(e.getPlayer()), getLine(sign, 2)) && !getLine(sign, 2).equalsIgnoreCase("give"))
			{
				Chat.error((e.getPlayer()), Texts.CANNOT_USE_CURRENTLY);
				return;
			}
		if((getLine(sign, 2).equalsIgnoreCase("give") || getLine(sign, 2).equalsIgnoreCase("start")) && !getLine(sign, 1).isEmpty())
		{
			if(!qQuests.plugin.qAPI.getQuests().containsKey(QuestFrag.get(getLine(sign, 1).toLowerCase()).toLowerCase()))
			{
				Chat.error(e.getPlayer(), "The quest on line 2 of the sign is not valid!");
				return;
			}
		}
		if(!getLine(sign, 1).isEmpty() && (!getLine(sign, 2).equalsIgnoreCase("give") || !getLine(sign, 2).equalsIgnoreCase("start")))
		{
			if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
			{
				if(!qQuests.plugin.qAPI.getActiveQuests().get(e.getPlayer()).name().equalsIgnoreCase(QuestFrag.get(getLine(sign, 1))))
				{
					Chat.error(e.getPlayer(), "This quest does not match your quest!");
					return;
				}
			}
		}
		if(getLine(sign, 2).isEmpty())
		{
			Chat.error(e.getPlayer(), "There is no action on line 3! (Give, Info, Tasks, Drop, Done)");
			return;
			
		}
		if((getLine(sign, 2).equalsIgnoreCase("give") || getLine(sign, 2).equalsIgnoreCase("start")) && getLine(sign, 1).isEmpty())
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "give.specific.sign"))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(e.getPlayer(), "Signs");
				if(result == 0)
				{
					Storage.wayCurrentQuestsWereGiven.put((e.getPlayer()), "Signs");
					Chat.message((e.getPlayer()), qQuests.plugin.qAPI.getActiveQuest(e.getPlayer()).onJoin().message());
					return;
				}
				else if(result == 1)
				{
					Chat.error((e.getPlayer()), Texts.NO_QUESTS_AVAILABLE);
				}
				else
					Chat.error(e.getPlayer(), Chat.errorCode(result, "Signs", e.getPlayer()));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("give") || getLine(sign, 2).equalsIgnoreCase("start") && !getLine(sign, 1).isEmpty())
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "give.sign"))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(e.getPlayer(), getLine(sign, 1), false, "Signs");
				if(result == 0)
				{
					Chat.message(e.getPlayer(), qQuests.plugin.qAPI.getActiveQuest(e.getPlayer()).onJoin().message());
				}
				else if(result == 1)
					Chat.error(e.getPlayer(), Texts.NOT_VALID_QUEST);
				else
					Chat.error(e.getPlayer(), Chat.errorCode(result, "Signs", e.getPlayer()));
			}
			else Chat.noPerms(e.getPlayer());
			
		}
		else if(getLine(sign, 2).equalsIgnoreCase("info"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "info.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
				{
					Texts.INFO(qQuests.plugin.qAPI.getActiveQuest(e.getPlayer()), e.getPlayer());
				}
				else Chat.error(e.getPlayer(), Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("tasks") || getLine(sign, 2).equalsIgnoreCase("progress"))
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
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
						else if(q.tasks().get(i).type().equalsIgnoreCase("enchant"))
							if(Storage.info.showItemIds)
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
						else
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("tame"))
							Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Tame " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						i++;
					}
				} else Chat.error(e.getPlayer(), Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("drop"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "drop.sign"))
			{
				Quest q = qQuests.plugin.qAPI.getActiveQuest(e.getPlayer());
				Integer result = qQuests.plugin.qAPI.dropQuest(e.getPlayer());
				if(result == 0)
					Chat.message((e.getPlayer()), q.onDrop().message());
				else
					Chat.error(e.getPlayer(), Chat.errorCode(result, "Signs", e.getPlayer()));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("done") || getLine(sign, 2).equalsIgnoreCase("finish") || getLine(sign, 2).equalsIgnoreCase("end"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "done.sign"))
			{
				Integer result = qQuests.plugin.qAPI.completeQuest(e.getPlayer());
				if(result != 0)
					Chat.error(e.getPlayer(), Chat.errorCode(result, "Signs", e.getPlayer()));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("list"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "list.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(e.getPlayer()))
					Chat.error(e.getPlayer(), Texts.HAS_ACTIVE_QUEST);
				if(Storage.cannotGetQuests.contains(e.getPlayer()))
					Chat.error(e.getPlayer(), Texts.DELAY_NOT_FINISHED);
				if(qQuests.plugin.qAPI.getVisibleQuests().size() == 0)
					Chat.error(e.getPlayer(), Texts.NO_QUESTS_AVAILABLE);
				HashMap<Integer, Quest> q = qQuests.plugin.qAPI.getAvailableQuests(e.getPlayer());
				if(!q.isEmpty())
				{
					Chat.noPrefixMessage(e.getPlayer(), ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Available Quests " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
					for(Quest a : q.values())
						Chat.noPrefixMessage(e.getPlayer(), ChatColor.GREEN + "- " + ChatColor.LIGHT_PURPLE + a.name());
				}
				else Chat.error(e.getPlayer(), Texts.NO_QUESTS_AVAILABLE);
			}
			else Chat.noPerms(e.getPlayer());
		}
		else if(getLine(sign, 2).equalsIgnoreCase("stats"))
		{
			if(qQuests.plugin.qAPI.checkPerms(e.getPlayer(), "stats.sign"))
			{
				Chat.noPrefixMessage(e.getPlayer(), "Level: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(e.getPlayer(), "Level"));
				Chat.noPrefixMessage(e.getPlayer(), "Quests Given: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(e.getPlayer(), "Given"));
				Chat.noPrefixMessage(e.getPlayer(), "Quests Dropped: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(e.getPlayer(), "Dropped"));
				Chat.noPrefixMessage(e.getPlayer(), "Quests Completed: " + ChatColor.GREEN + qQuests.plugin.qAPI.getProfiles().getInt(e.getPlayer(), "Completed"));
			}
			else Chat.noPerms(e.getPlayer());
		}
		else
		{
			Chat.error(e.getPlayer(), "This is not a valid action on line 3! (Give, Info, Tasks, Drop, Done)");
			return;
		}
	}
	private static String getLine(Sign sign, int line) {
		Chat.logger("debug", Chat.removeColors(sign.getLine(line)));
		return Chat.removeColors(sign.getLine(line));
	}
	private static String getLine(String[] lines, int line) {
		Chat.logger("debug", Chat.removeColors(lines[line]));
		return Chat.removeColors(lines[line]);
	}
	private static void dropSign(SignChangeEvent event)
	  {
	    event.setCancelled(true);
	
	    Block block = event.getBlock();
	    block.setType(Material.AIR);
	    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
	  }
	
}
