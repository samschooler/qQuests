package me.quaz3l.qQuests.Util;

import java.util.ArrayList;

import org.bukkit.command.Command;

import me.quaz3l.qQuests.qQuests;

public class Reloader {
	private ArrayList<Command>commands = new ArrayList<Command>();
	
	public Reloader() {
		
	}
	
	public void unloadPlugin() {
		
	}
	public void loadPlugin() {
		
	}
	
	public void addCommand(String cmd) {
		commands.add(qQuests.plugin.getCommand(cmd));
	}
	public void clearCommands() {
		commands.clear();
	}
}
