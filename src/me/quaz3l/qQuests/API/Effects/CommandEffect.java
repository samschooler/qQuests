package me.quaz3l.qQuests.API.Effects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import me.quaz3l.qQuests.Util.Chat;

public class CommandEffect extends qEffect {

	@Override
	public void executeEffect(String player, Object value) {
		Chat.logger("debug", "CommandEffect.Exe();");
		try {
			@SuppressWarnings("unchecked")
			ArrayList<String> cmds = (ArrayList<String>) value;
			if(cmds.get(0) == null)
				return;
			
			for(String cmd : cmds)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("`player", player));
			}
			
		} catch(ClassCastException e) {
			return;
		}
	}

	@Override
	public int passedRequirement(String player, Object value) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<String> cmds = (ArrayList<String>) value;
			if(cmds.get(0) == null)
				return -1;
			
		} catch(ClassCastException e) {
			return -1;
		}
		return 0;
	}

	@Override
	public int validate(Object value) {
		if(value == null) {
			Chat.logger("debug", "CMD.validate().0");
			return -1;
		}
		try {
			@SuppressWarnings("unchecked")
			ArrayList<String> cmds = (ArrayList<String>) value;
			if(cmds.get(0) == null) {
				Chat.logger("debug", "CMD.validate().0");
				return -1;
			}
		} catch(ClassCastException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public String getName() {
		return "commands";
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public String parseError(String player, Object value, int errorCode) {
		switch(errorCode) {
		case -1: return "The effect " + this.getName() + ",  is invalid!";
		default: return "Unknown Error! LULZ! :p";
		}
	}
}
