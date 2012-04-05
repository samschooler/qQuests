package me.quaz3l.qQuests.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Texts {
	// Commands
	public static String PRIMARY_COMMAND = "/" + Storage.primaryCommand;
	public static final String GIVE_COMMAND = "give";
	public static final String INFO_COMMAND = "info";
	public static final String TASKS_COMMAND = "tasks";
	public static final String DONE_COMMAND = "done";
	public static final String HELP_COMMAND = "help";
	
	// Help Text
	public static final String COMMANDS_HELP_TEXT = PRIMARY_COMMAND + " " + ChatColor.RED + "[" + ChatColor.YELLOW + GIVE_COMMAND + ", " + INFO_COMMAND + ", " + TASKS_COMMAND + ", " + DONE_COMMAND + ", " + HELP_COMMAND + ChatColor.RED + "]";
	public static final String COMMANDS_TASKS_HELP = "Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + TASKS_COMMAND + ChatColor.GREEN + " For Your Other Tasks";
	public static final String COMMANDS_DONE_HELP = "Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + DONE_COMMAND + ChatColor.GREEN + " To Complete Your Quest!";
	
	// No
	public static final String NO_PERMISSION = "You don't have permissions to do this!";
	public static final String ONLY_PLAYERS = "Sorry A Quest Can Only Be Used By Players!";
	public static final String NO_QUESTS_AVAILABLE = "There Are No Quests Available At This Time.";
	public static final String DELAY_NOT_FINISHED = "You Cannot Get Quests Right Now! Wait A Bit And Try Again.";
	
	// Non-Fatal Quest Errors
	public static final String COMMANDS_HAS_ACTIVE_QUEST = "You Already Have An Active Quest! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + INFO_COMMAND + ChatColor.RED + " To Get More Info On Your Quest.";
	public static final String COMMANDS_NO_ACTIVE_QUEST = "You Don't Have An Active Quest! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + GIVE_COMMAND + ChatColor.RED + " To Get One.";
	public static final String HAS_ACTIVE_QUEST = "You Already Have An Active Quest!";
	public static final String NO_ACTIVE_QUEST = "You Don't Have An Active Quest!";
	
	public static final String NOT_ENOUGH_FOR_QUEST = "You Don't Have Enough To Get This Quest!";
	public static final String NOT_VALID_QUEST = "This Isn't A Valid Quest!";
	
	// Non-Fatal Task Errors
	public static final String COMMANDS_TASKS_NOT_COMPLETED = "You Haven't Completed All The Tasks! Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + TASKS_COMMAND + ChatColor.RED + " To See Them.";
	public static final String TASKS_NOT_COMPLETED = "You Haven't Completed All The Tasks!";
	
	// Listener Texts
	public static final String DESTROY_COMPLETED_QUOTA = "You Have Broken";
	public static final String DAMAGE_COMPLETED_QUOTA = "You Have Damaged";
	public static final String PLACE_COMPLETED_QUOTA = "You Have Placed";
	
	public static final String KILL_COMPLETED_QUOTA = "You Have Killed";
	public static final String KILL_PLAYER_COMPLETED_QUOTA = "You Have Successfully Killed";
	public static final String TAME_COMPLETED_QUOTA = "You Have Tamed";
	
	public static final String GOTO_COMPLETED_QUOTA = "You Have Successfully Reached";
	public static final String DISTANCE_COMPLETED_QUOTA = "You Have Successfully Traveled";
	
	public static final String ENCHANT_COMPLETED_QUOTA = "You Have Enchanted";
	
	// Quest Plugin Control
	public static final String NOT_CONTROLLED_BY(Player p) {return "Your Quest Is Controlled By " + Storage.wayCurrentQuestsWereGiven.get(p);}	
	
	// Config Words
	public static final String QUEST = "Quest";
	public static final String INVALID = "Has A Invalid";
	public static final String NEXT_QUEST = "Next Quest";
	public static final String REPEATABLE = "Repeatable";
	
	// Reward Names
	public static final String MONEY = "Money";
	public static final String HEALTH = "Health";
	public static final String FOOD = "Food";
	public static final String ITEMS = "Items";
	
	// Not Enough Fees
	public static final String NOT_ENOUGH_MONEY = "You Don't Have Enough Money!";
	public static final String NOT_ENOUGH_HEALTH = "You Don't Have Enough Health!";
	public static final String NOT_ENOUGH_FOOD = "You Don't Have Enough Food!";
	public static final String NOT_ENOUGH_ITEMS = "You Don't Have Enough Items!";
}
