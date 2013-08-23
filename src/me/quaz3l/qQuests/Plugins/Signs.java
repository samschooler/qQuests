package me.quaz3l.qQuests.Plugins;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.PluginModels.qPlugin;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Alias;
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

public class Signs extends qPlugin implements Listener {
	private HashMap<Sign, HashMap<String, Integer>> cmds = new HashMap<Sign, HashMap<String, Integer>>();
	@Override
	public String getName() {
		return "Signs";
	}
	@Override
	public void onEnable() {
		qQuests.plugin.getServer().getPluginManager().registerEvents(new Signs(), qQuests.plugin);
	}
	@Override
	public void onDisable() {

	}
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
				if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer().getName(), "destroy.sign"))
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
				if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer().getName(), "destroy.sign"))
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
		if(!qQuests.plugin.qAPI.checkPerms(e.getPlayer().getName(), "create.sign"))
		{
			Chat.noPerms(e.getPlayer().getName());
			dropSign(e);
		}
		else {
			if(!getLine(e.getLines(), 1).isEmpty()) {
				if(qQuests.plugin.qAPI.getQuest(QuestFrag.get(getLine(e.getLines(), 1))) == null) {
					Chat.error(e.getPlayer().getName(), "The quest on line 2 of the sign is not valid!");
					dropSign(e);
					return;
				}
			}
			Chat.logger("debug", getLine(e.getLines(), 2));
			if(getLine(e.getLines(), 2).isEmpty() || Alias.getRealCMD(getLine(e.getLines(), 2)) == null) {
				Chat.error(e.getPlayer().getName(), "There is no valid action on line 3! (Give, Info, Tasks, Drop, Done)");
				dropSign(e);
				return;
			}
			// Default Format

			// Line 0
			e.setLine(0, ChatColor.WHITE + e.getLine(0));

			// Line 2
			if(getLine(e.getLines(), 2).equalsIgnoreCase("give")) {
				e.setLine(2, ChatColor.DARK_BLUE + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will give a random visible quest.");
				else
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will give the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("tasks") || getLine(e.getLines(), 2).equalsIgnoreCase("info")) {
				e.setLine(2, ChatColor.GOLD + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will show for tasks any quest.");
				else
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will show tasks for the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("drop")) {
				e.setLine(2, ChatColor.DARK_RED + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will drop any quest.");
				else
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will drop the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 2).equalsIgnoreCase("done")) {
				e.setLine(2, ChatColor.GREEN + e.getLine(2));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will complete any quest.");
				else
					Chat.green(e.getPlayer().getName(), "Quest sign created! This will complete the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}

			// Line 3
			if(getLine(e.getLines(), 3).equalsIgnoreCase("give")) {
				e.setLine(3, ChatColor.DARK_BLUE + e.getLine(3));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "The second command will give a random visible quest.");
				else
					Chat.green(e.getPlayer().getName(), "The second command will give the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 3).equalsIgnoreCase("tasks") || getLine(e.getLines(), 2).equalsIgnoreCase("info")) {
				e.setLine(3, ChatColor.GOLD + e.getLine(3));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "The second command will show for tasks any quest.");
				else
					Chat.green(e.getPlayer().getName(), "The second command will show tasks for the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 3).equalsIgnoreCase("drop")) {
				e.setLine(3, ChatColor.DARK_RED + e.getLine(3));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "The second command will drop any quest.");
				else
					Chat.green(e.getPlayer().getName(), "The second command This will drop the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
			}
			if(getLine(e.getLines(), 3).equalsIgnoreCase("done")) {
				e.setLine(3, ChatColor.GREEN + e.getLine(3));
				if(getLine(e.getLines(), 1).isEmpty())
					Chat.green(e.getPlayer().getName(), "The second command This will complete any quest.");
				else
					Chat.green(e.getPlayer().getName(), "The second command This will complete the " + QuestFrag.get(getLine(e.getLines(), 1)) + " quest.");
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

		// Check for a qQuests Sign
		if(!getLine(sign, 0).equalsIgnoreCase("[qQuests]") && !getLine(sign, 0).equalsIgnoreCase("[Quest]") && !getLine(sign, 0).equalsIgnoreCase(Chat.removeColors(qQuests.plugin.chatPrefix).trim()))
			return;

		// Cancel block placement
		e.setCancelled(true);


		if(Storage.wayCurrentQuestsWereGiven.get(e.getPlayer().getName()) != null) {
			if(!Storage.access("signs", Storage.wayCurrentQuestsWereGiven.get(e.getPlayer().getName()), getLine(sign, 2)) && !getLine(sign, 2).equalsIgnoreCase("give"))
			{
				Chat.error((e.getPlayer().getName()), Texts.CANNOT_USE_CURRENTLY);
				return;
			}
		}
		int rel = getMostRelevantLine(sign, e.getPlayer().getName());
		if(rel >= 0)
			executeLine(sign, rel, e.getPlayer().getName());		
	}

	private int getMostRelevantLine(Sign sign, String player) {
		String[] lines = sign.getLines();
		int rturn = -1;
		
		if(cmds.get(sign) == null) {
			Chat.logger("debug", "HERP");
			cmds.put(sign, new HashMap<String, Integer>());
		}
		if(cmds.get(sign).get(player) == null) {
			Chat.logger("debug", "DERP");
			cmds.get(sign).put(player, -1);
		}

		// Check for 4 lines
		if(lines.length != 4)
			rturn = 2;

		String ln2 = Alias.getRealCMD(lines[2]);
		String ln3 = Alias.getRealCMD(lines[3]);
		
		if(ln3 == "") {
			return 2;
		}

		Chat.logger("debug", ln2 + " " + ln3);

		if(this.cmds.get(player) != null && 
				qQuests.plugin.qAPI.hasActiveQuest(player) &&
				!ln2.equalsIgnoreCase("give") &&
				!ln3.equalsIgnoreCase("give")) {
			Chat.logger("debug", "1");
			if(cmds.get(sign).get(player) < 2) {
				rturn = 2;
			} else if(cmds.get(sign).get(player) == 2) {
				rturn = 3;
			} else {
				rturn = 2;
			}
		}
		// Check if a player doesn't have a quest
		else if(!qQuests.plugin.qAPI.hasActiveQuest(player)) {
			if(ln2.equalsIgnoreCase("give")) {
				rturn = 2;
			} else if(ln3.equalsIgnoreCase("give")) {
				rturn = 3;
			} else {
				Chat.error(player, Texts.NO_ACTIVE_QUEST);
			}
		} else if(qQuests.plugin.qAPI.hasActiveQuest(player)) {
			Chat.logger("debug", "3");
			Chat.logger("debug", cmds.get(sign).get(player)+"");
			if(ln2.equalsIgnoreCase("give") && ln3.equalsIgnoreCase("give")) {
				Chat.error(player, Texts.HAS_ACTIVE_QUEST(player));
			} else if(ln3.equalsIgnoreCase("give")) {
				rturn = 2;
			} else if(ln2.equalsIgnoreCase("give")) {
				rturn = 3;
			} else {
				if(cmds.get(sign).get(player) == 2) {
					rturn = 3;
				} else {
					rturn = 2;
				}
			}
		}
		cmds.get(sign).put(player, rturn);
		return rturn;
	}
	private boolean executeLine(Sign sign, int line, String player) {
		if((getLine(sign, line).equalsIgnoreCase("give") || getLine(sign, line).equalsIgnoreCase("start")) && !getLine(sign, 1).isEmpty())
		{
			if(!qQuests.plugin.qAPI.getQuests().containsKey(QuestFrag.get(getLine(sign, 1).toLowerCase()).toLowerCase()))
			{
				Chat.error(player, "The quest on line 2 of the sign is not valid!");
				return false;
			}
		}
		if(!getLine(sign, 1).isEmpty() && (!getLine(sign, line).equalsIgnoreCase("give") || !getLine(sign, line).equalsIgnoreCase("start")))
		{
			if(qQuests.plugin.qAPI.hasActiveQuest(player))
			{
				if(!qQuests.plugin.qAPI.getActiveQuests().get(player).name().equalsIgnoreCase(QuestFrag.get(getLine(sign, 1))))
				{
					Chat.error(player, "This quest doesn't match your quest! " + ChatColor.YELLOW + qQuests.plugin.qAPI.getActiveQuest(player).name() + ChatColor.RED + " is your quest.");
					return false;
				}
			}
		}
		if(getLine(sign, line).isEmpty())
		{
			Chat.error(player, "There is no action on line " + (line+1) + "! (Give, Info, Tasks, Drop, Done)");
			return false;

		}
		if((getLine(sign, line).equalsIgnoreCase("give") && getLine(sign, 1).isEmpty()))
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "give.specific.sign"))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(player, "Signs");
				if(result == 0)
				{
					Storage.wayCurrentQuestsWereGiven.put((player), "Signs");
					Chat.message((player), qQuests.plugin.qAPI.getActiveQuest(player).onJoin().message(player));
					return true;
				}
				else if(result == 1)
				{
					Chat.error((player), Texts.NO_QUESTS_AVAILABLE);
				}
				else
					Chat.error(player, Chat.errorCode(result, "Signs", player));
			}
			else Chat.noPerms(player);
		}
		else if(getLine(sign, line).equalsIgnoreCase("give") && !getLine(sign, 1).isEmpty())
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "give.sign"))
			{
				Integer result = qQuests.plugin.qAPI.giveQuest(player, getLine(sign, 1), false, "Signs");
				if(result == 0)
				{
					Chat.message(player, qQuests.plugin.qAPI.getActiveQuest(player).onJoin().message(player));
					return true;
				}
				else if(result == 1)
					Chat.error(player, Texts.NOT_VALID_QUEST);
				else
					Chat.error(player, Chat.errorCode(result, "Signs", player));
			}
			else Chat.noPerms(player);

		}
		else if(getLine(sign, line).equalsIgnoreCase("info"))
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "info.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(player))
				{
					Texts.INFO(qQuests.plugin.qAPI.getActiveQuest(player), player);
					return true;
				}
				else Chat.error(player, Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(player);
		}
		else if(getLine(sign, line).equalsIgnoreCase("tasks"))
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "tasks.sign"))
			{
				if(qQuests.plugin.qAPI.hasActiveQuest(player))
				{
					Quest q = qQuests.plugin.qAPI.getActiveQuest(player);
					Chat.noPrefixMessage(player, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + " Quest Tasks " + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
					int i=0;
					while(q.tasks().size() > i) 
					{
						if(q.tasks().get(i).type().equalsIgnoreCase("collect"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Collect " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("damage"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Damage " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("destroy"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Destroy " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("place"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Place " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill"))
							Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("kill_player"))
							Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Kill The Player '" + q.tasks().get(i).idString() + "' " + q.tasks().get(i).amount() + " Times");
						else if(q.tasks().get(i).type().equalsIgnoreCase("enchant"))
							if(Storage.info.showItemIds)
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display() + ChatColor.GOLD + "(" + ChatColor.RED + "ID:" + q.tasks().get(i).idInt() + ChatColor.GOLD + ")");
							else
								Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Enchant " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("tame"))
							Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Tame " + q.tasks().get(i).amount() + " " + q.tasks().get(i).display());
						else if(q.tasks().get(i).type().equalsIgnoreCase("goto"))
							Chat.noPrefixMessage(player, ChatColor.GREEN + "" + (i + 1) + ". " + ChatColor.LIGHT_PURPLE + "Go To " + q.tasks().get(i).display());
						i++;
					}
					return true;
				} else Chat.error(player, Texts.NO_ACTIVE_QUEST);
			}
			else Chat.noPerms(player);
		}
		else if(getLine(sign, line).equalsIgnoreCase("drop"))
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "drop.sign"))
			{
				Quest q = qQuests.plugin.qAPI.getActiveQuest(player);
				Integer result = qQuests.plugin.qAPI.dropQuest(player);
				if(result == 0) {
					Chat.message((player), q.onDrop().message(player));
					return true;
				} else
					Chat.error(player, Chat.errorCode(result, "Signs", player));
			}
			else Chat.noPerms(player);
		}
		else if(getLine(sign, line).equalsIgnoreCase("done"))
		{
			if(qQuests.plugin.qAPI.checkPerms(player, "done.sign"))
			{
				Integer result = qQuests.plugin.qAPI.completeQuest(player);
				if(result != 0) {
					Chat.error(player, Chat.errorCode(result, "Signs", player));
					return true;
				}
			}
			else Chat.noPerms(player);
		}
		else
		{
			Chat.error(player, "This is not a valid action on line " + (line+1) + "! (Give, Info, Tasks, Drop, Done)");
		}
		return false;
	}
	private static String getLine(Sign sign, int line) {
		Chat.logger("debug", Alias.getRealCMD(Chat.removeColors(sign.getLine(line))));
		if(line != 2 && line != 3)
			return Chat.removeColors(sign.getLine(line));
		else
			return Alias.getRealCMD(Chat.removeColors(sign.getLine(line)));
	}
	private static String getLine(String[] lines, int line) {
		Chat.logger("debug", Chat.removeColors(lines[line]));
		if(line != 2 && line != 3)
			return Chat.removeColors(lines[line]);
		else
			return Alias.getRealCMD(Chat.removeColors(lines[line]));
	}
	private static void dropSign(SignChangeEvent event)
	{
		event.setCancelled(true);

		Block block = event.getBlock();
		block.setType(Material.AIR);
		block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SIGN, 1));
	}

}
