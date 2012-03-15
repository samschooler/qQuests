package me.quaz3l.qQuests.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Texts {
	// Commands
	public static final String PRIMARY_COMMAND = "/q";
	public static final String GIVE_COMMAND = "give";
	public static final String INFO_COMMAND = "info";
	public static final String TASKS_COMMAND = "tasks";
	public static final String DONE_COMMAND = "done";
	public static final String HELP_COMMAND = "help";
	
	// Help Text
	public static final String HELP_TEXT = PRIMARY_COMMAND + " " + ChatColor.RED + "[" + ChatColor.YELLOW + GIVE_COMMAND + ", " + INFO_COMMAND + ", " + TASKS_COMMAND + ", " + DONE_COMMAND + ", " + HELP_COMMAND + ChatColor.RED + "]";
	public static final String TASKS_HELP = "Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + TASKS_COMMAND + ChatColor.GREEN + " For Your Other Tasks";
	
	// No Permission
	public static final String NO_PERMISSION = "You don't have permissions to do this!";
	public static final String ONLY_PLAYERS = "Sorry A Quest Can Only Be Used By Players!";
	
	// Non-Fatal Quest Errors
	public static final String HAS_ACTIVE_QUEST = "You Already Have An Active Quest! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + INFO_COMMAND + ChatColor.RED + " To Get More Info On Your Quest.";
	public static final String NO_ACTIVE_QUEST = "You Don't Have An Active Quest! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + GIVE_COMMAND + ChatColor.RED + " To Get One.";
	public static final String NOT_ENOUGH_FOR_QUEST = "You Don't Have Enough To Get This Quest!";
	public static final String NOT_VALID_QUEST = "This Isn't A Valid Quest!";
	
	// Non-Fatal Task Errors
	public static final String TASKS_NOT_COMPLETED = "You Haven't Completed All The Tasks! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + TASKS_COMMAND + ChatColor.RED + " To See Them.";
	
	// Listener Texts
	public static final String DESTROY_COMPLETED_QUOTA = "You Have Broken Enough";
	public static final String DAMAGE_COMPLETED_QUOTA = "You Have Damaged Enough";
	public static final String PLACE_COMPLETED_QUOTA = "You Have Placed Enough";
	
	public static final String KILL_COMPLETED_QUOTA = "You Have Killed Enough";
	public static final String KILL_PLAYER_COMPLETED_QUOTA = "You Have Successfully Killed";
	
	public static final String GOTO_COMPLETED_QUOTA = "You Have Successfully Reached";
	public static final String DISTANCE_COMPLETED_QUOTA = "You Have Successfully Traveled";
	
	// Quest Plugin Control
	public static final String NOT_CONTROLLED_BY(Player p) {return "Your Quest Is Controlled By " + Storage.wayCurrentQuestsWereGiven.get(p);}	
}
