package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

import org.bukkit.entity.Player;

public class PlayerUtil {
	public static Player get(String name)
	{
		return (Player)qQuests.plugin.getServer().getOfflinePlayer(name);
	}
}
