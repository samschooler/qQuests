package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
	// General Message
	public static void message(Player p, String msg)
	{
		p.sendMessage(parseColors(qQuests.plugin.chatPrefix + msg));
	}
	public static void message(CommandSender s, String msg)
	{
		s.sendMessage(parseColors(qQuests.plugin.prefix + msg));
	}
	
	// Message With No Former Formatting
	public static void noPrefixMessage(Player p, String msg)
	{
		p.sendMessage(parseColors(ChatColor.LIGHT_PURPLE + msg));
	}
	public static void noPrefixMessage(CommandSender s, String msg)
	{
		s.sendMessage(parseColors(ChatColor.LIGHT_PURPLE + msg));
	}
	
	// A Good Message For Successes
	public static void quotaMessage(Player p, String msg, Integer currentAmount, Integer totalAmount, String unit)
	{
			p.sendMessage(parseColors(qQuests.plugin.chatPrefix + ChatColor.LIGHT_PURPLE + msg + " " + ChatColor.GREEN + currentAmount + "/" + totalAmount + " " + unit));
	}
		
	// Red Error With Prefix
	public static void error(Player p, String msg)
	{
		p.sendMessage(parseColors(qQuests.plugin.chatPrefix + ChatColor.RED + msg));
	}
	public static void error(CommandSender p, String msg)
	{
		p.sendMessage(parseColors(qQuests.plugin.chatPrefix + ChatColor.RED + msg));
	}
	
	// A Good Message For Successes
	public static void green(Player p, String msg)
	{
		p.sendMessage(parseColors(qQuests.plugin.chatPrefix + ChatColor.GREEN + msg));
	}
	
	// No Permissions Message
	public static void noPerms(Player p)
	{
		p.sendMessage(parseColors(ChatColor.RED + Texts.NO_PERMISSION));
	}
	public static void noPerms(CommandSender s)
	{
		s.sendMessage(parseColors(ChatColor.RED + Texts.NO_PERMISSION));
	}
	
	// Logger
	public static void logger(String lvl, String s)
	{
		if(lvl.equalsIgnoreCase("info"))
			qQuests.plugin.logger.info(parseColors(qQuests.plugin.prefix + s));
		else if(lvl.equalsIgnoreCase("warning"))
			qQuests.plugin.logger.warning(parseColors(qQuests.plugin.prefix + s));
		else if(lvl.equalsIgnoreCase("severe"))
			qQuests.plugin.logger.severe(parseColors(qQuests.plugin.prefix + s));
		else
			qQuests.plugin.logger.warning(parseColors(qQuests.plugin.prefix + s));
	}
	
	// Parses Error Codes To Phrases
	public static String errorCode(Integer code, String type)
	{
		if(type.equalsIgnoreCase("Commands"))
		{
			switch(code)
			{
			case 0:
				return "Success";
			case 1:
				return null;
			case 2:
				return Texts.NOT_ENOUGH_FOR_QUEST;
			case 3:
				return Texts.COMMANDS_HAS_ACTIVE_QUEST;
			case 4:
				return Texts.COMMANDS_TASKS_NOT_COMPLETED;
			case 5:
				return Texts.NOT_ENOUGH_MONEY;
			case 6:
				return Texts.NOT_ENOUGH_HEALTH;
			case 7:
				return Texts.NOT_ENOUGH_FOOD;
			case 8:
				return Texts.NOT_ENOUGH_ITEMS;
			case 9:
				return Texts.COMMANDS_NO_ACTIVE_QUEST;
			case 10:
				return Texts.DELAY_NOT_FINISHED;
			case 11:
				return Texts.NO_QUESTS_AVAILABLE;
			case 12:
				return Texts.LEVEL_TOO_HIGH;
			case 13:
				return Texts.LEVEL_TOO_LOW;
			default:
				return "Unknown";
			}
		}
		else
		{
			switch(code)
			{
			case 0:
				return "Success";
			case 1:
				return null;
			case 2:
				return Texts.NOT_ENOUGH_FOR_QUEST;
			case 3:
				return Texts.HAS_ACTIVE_QUEST;
			case 4:
				return Texts.TASKS_NOT_COMPLETED;
			case 5:
				return Texts.NOT_ENOUGH_MONEY;
			case 6:
				return Texts.NOT_ENOUGH_HEALTH;
			case 7:
				return Texts.NOT_ENOUGH_FOOD;
			case 8:
				return Texts.NOT_ENOUGH_ITEMS;
			case 9:
				return Texts.NO_ACTIVE_QUEST;
			case 10:
				return Texts.DELAY_NOT_FINISHED;
			case 11:
				return Texts.NO_QUESTS_AVAILABLE;
			case 12:
				return Texts.LEVEL_TOO_HIGH;
			case 13:
				return Texts.LEVEL_TOO_LOW;
			default:
				return "Unknown";
			}
		}
	}
	
	
	// Corrects Color Codes
	public static String parseColors(String s)
	{
		return s.replaceAll("`0", ChatColor.BLACK + "")
				.replaceAll("`1", ChatColor.DARK_BLUE + "")
				.replaceAll("`2", ChatColor.DARK_GREEN + "")
				.replaceAll("`3", ChatColor.DARK_AQUA + "")
				.replaceAll("`4", ChatColor.DARK_RED + "")
				.replaceAll("`5", ChatColor.DARK_PURPLE + "")
				.replaceAll("`6", ChatColor.GOLD + "")
				.replaceAll("`7", ChatColor.GRAY + "")
				.replaceAll("`8", ChatColor.DARK_GRAY + "")
				.replaceAll("`9", ChatColor.BLUE + "")
				.replaceAll("`a", ChatColor.GREEN + "")
				.replaceAll("`b", ChatColor.AQUA + "")
				.replaceAll("`c", ChatColor.RED + "")
				.replaceAll("`d", ChatColor.LIGHT_PURPLE + "")
				.replaceAll("`e", ChatColor.YELLOW + "")
				.replaceAll("`f", ChatColor.WHITE + "")
				.replaceAll("`g", ChatColor.MAGIC + "");
	}
	public static void attention(int type) {
		switch(type)
		{
		case 0:
			Chat.logger("info", "################################################################");
			Chat.logger("info", "############################## INFO ############################");
			Chat.logger("info", "################################################################");
			break;
		case 1:
			Chat.logger("warning", "################################################################");
			Chat.logger("warning", "######################### WARNING! #############################");
			Chat.logger("warning", "################################################################");
			break;
		case 2:
			Chat.logger("severe", "################################################################");
			Chat.logger("severe", "########################## SEVERE! #############################");
			Chat.logger("severe", "################################################################");
			break;
		case 3:
			Chat.logger("warning", "################################################################");
			Chat.logger("warning", "################################################################");
			Chat.logger("warning", "################################################################");
			break;
		default:
			Chat.logger("warning", "################################################################");
			Chat.logger("warning", "################################################################");
			Chat.logger("warning", "################################################################");
			break;
		}
	}
}
