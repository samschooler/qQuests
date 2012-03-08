package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
	public static void message(Player p, String msg)
	{
		p.sendMessage(qQuests.plugin.chatPrefix + msg);
	}
	public static void message(CommandSender s, String msg)
	{
		s.sendMessage(qQuests.plugin.prefix + msg);
	}
	public static void noPrefixMessage(Player p, String msg)
	{
		p.sendMessage(msg);
	}
	public static void noPrefixMessage(CommandSender s, String msg)
	{
		s.sendMessage(msg);
	}
	public static void error(Player p, String msg)
	{
		p.sendMessage(qQuests.plugin.chatPrefix + ChatColor.RED + msg);
	}
	public static void error(CommandSender s, String msg)
	{
		s.sendMessage(qQuests.plugin.prefix + ChatColor.RED + msg);
	}
	public static void done(Player p, String msg)
	{
		p.sendMessage(qQuests.plugin.chatPrefix + ChatColor.GREEN + msg);
	}
	public static void done(CommandSender s, String msg)
	{
		s.sendMessage(qQuests.plugin.prefix + ChatColor.GREEN + msg);
	}
	public static void noPerms(Player p)
	{
		p.sendMessage(qQuests.plugin.chatPrefix + ChatColor.RED + "You don't have permissions to do this!");
	}
	public static void noPerms(CommandSender s)
	{
		s.sendMessage(qQuests.plugin.prefix + ChatColor.RED + "You don't have permissions to do this!");
	}
	
	// Logger
	public static void logger(String lvl, String s)
	{
		if(lvl.equalsIgnoreCase("info"))
			qQuests.plugin.logger.info(qQuests.plugin.prefix + s);
		else if(lvl.equalsIgnoreCase("warning"))
			qQuests.plugin.logger.warning(qQuests.plugin.prefix + s);
		else if(lvl.equalsIgnoreCase("severe"))
			qQuests.plugin.logger.severe(qQuests.plugin.prefix + s);
		else
			qQuests.plugin.logger.warning(qQuests.plugin.prefix + s);
	}
}
