package me.quaz3l.qQuests.Util;

import java.util.HashSet;

import org.bukkit.entity.Player;

public class PlayerSet extends HashSet<Player> {
	private static final long serialVersionUID = 3598580512359703774L;
	public PlayerSet()
	{
		
	}
	public PlayerSet(HashSet<Player> players)
	{
		this.addAll(players);
	}
}
