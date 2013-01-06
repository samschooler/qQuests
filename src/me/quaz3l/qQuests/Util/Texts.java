package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Texts {
	// Commands
	public static String PRIMARY_COMMAND = "/" + qQuests.plugin.Config.getConfig().getString("primaryCommand");;
	public static final String GIVE_COMMAND = "give";
	public static final String INFO_COMMAND = "info";
	public static final String TASKS_COMMAND = "tasks";
	public static final String DONE_COMMAND = "done";
	public static final String LIST_COMMAND = "list";
	public static final String STATS_COMMAND = "stats";
	public static final String HELP_COMMAND = "help";
	public static final String RELOAD_COMMAND = "reload";
	
	// Help Text
	public static final void HELP(Player player, String plugin) {
		Chat.noPrefixMessage(player, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + "qQuests #" + qQuests.plugin.getDescription().getVersion() + " Help" + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + GIVE_COMMAND + ChatColor.LIGHT_PURPLE + "  - Get a new quest");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + INFO_COMMAND + ChatColor.LIGHT_PURPLE + "  - Get info on your quest");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + TASKS_COMMAND + ChatColor.LIGHT_PURPLE + " - See your quest's tasks and progess");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + DONE_COMMAND + ChatColor.LIGHT_PURPLE + "  - Finsh a quest");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + LIST_COMMAND + ChatColor.LIGHT_PURPLE + "  - List quests available to you");
		Chat.noPrefixMessage(player, ChatColor.YELLOW + PRIMARY_COMMAND + " " + ChatColor.GREEN + STATS_COMMAND + ChatColor.LIGHT_PURPLE + " - List your quest stats");
	}
	public static final String COMMANDS_HELP_TEXT = PRIMARY_COMMAND + " " + ChatColor.RED + "[" + ChatColor.YELLOW + GIVE_COMMAND + ", " + INFO_COMMAND + ", " + TASKS_COMMAND + ", " + DONE_COMMAND + ", " + LIST_COMMAND + ", " + STATS_COMMAND + ", " + RELOAD_COMMAND + ", " + HELP_COMMAND + ChatColor.RED + "]";
	public static final String COMMANDS_TASKS_HELP = "Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + TASKS_COMMAND + ChatColor.GREEN + " For Your Other Tasks";
	public static final String COMMANDS_DONE_HELP = "Type " + ChatColor.YELLOW + PRIMARY_COMMAND + " " + DONE_COMMAND + ChatColor.GREEN + " To Complete Your Quest!";
	
	public static final String SIGNS_TASKS_HELP = "Find A Tasks Sign To See Your Other Tasks";
	public static final String SIGNS_DONE_HELP = "Find A Done Sign To Complete Your Quest!";
	public static final String YOUR_CURRENT_QUEST_IS = "Is You";
	
	// Info
	public static final void INFO(Quest q, Player player) {
		Chat.noPrefixMessage(player, ChatColor.AQUA + ":" + ChatColor.BLUE + "========" + ChatColor.GOLD + q.name() + ChatColor.BLUE + "========" + ChatColor.AQUA + ":");
		if(q.onComplete().nextQuest() != null && !q.onComplete().nextQuest().isEmpty())
			Chat.noPrefixMessage(player, "Next Quest: " + ChatColor.GREEN + q.onComplete().nextQuest());
		if(q.repeated() == -1)
			Chat.noPrefixMessage(player, "Repeatable: " + ChatColor.GREEN + "Infinite");
		else if((q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted(player, q)) == 0)
			Chat.noPrefixMessage(player, "Repeatable: " + ChatColor.GREEN + "None");
		else
			Chat.noPrefixMessage(player, "Repeatable: " + ChatColor.GREEN + (q.repeated() - qQuests.plugin.qAPI.getProfiles().getQuestsTimesCompleted(player, q)) + " More Times");
		Chat.noPrefixMessage(player, "Tasks: " + ChatColor.YELLOW + Texts.PRIMARY_COMMAND + " " + Texts.TASKS_COMMAND + ChatColor.GREEN + " For The Tasks.");
		Chat.noPrefixMessage(player, "Rewards:");
		if(Storage.info.showMoney)
			if(qQuests.plugin.economy != null && q.onComplete().money() != 0)
				Chat.noPrefixMessage(player, "     " + Texts.MONEY + ": " + ChatColor.GREEN + q.onComplete().money());
		if(Storage.info.showHealth)
			if(q.onComplete().health() != 0)
				Chat.noPrefixMessage(player, "     " + Texts.HEALTH + ": " + ChatColor.GREEN + q.onComplete().health());
		if(Storage.info.showFood)
			if(q.onComplete().hunger() != 0)
				Chat.noPrefixMessage(player, "     " + Texts.FOOD + ": " + ChatColor.GREEN + q.onComplete().hunger());
		if(Storage.info.showLevelsAdded)
			if(q.onComplete().levelAdd() != 0)
				Chat.noPrefixMessage(player, "     " + Texts.LEVELADD + ": " + ChatColor.GREEN + q.onComplete().levelAdd());
		if(Storage.info.showSetLevel)
			if(q.onComplete().levelSet() != -1)
				Chat.noPrefixMessage(player, "     " + Texts.LEVELSET + ": " + ChatColor.GREEN + q.onComplete().levelSet());
		if(Storage.info.showCommands)
			if(q.onComplete().items().size() > 0)
			{
				Chat.noPrefixMessage(player, "     " + Texts.COMMANDS + ":");
				for(int i=0;i<(q.onComplete().commands().size()); i++)
				{
					Chat.noPrefixMessage(player, "     " + ChatColor.GREEN + "- /" + ChatColor.GOLD + q.onComplete().commands().get(i).replace("`player", (player).getName()));
				}
			}
		if(Storage.info.showItems)
			if(q.onComplete().items().size() > 0)
			{
				Chat.noPrefixMessage(player, "     " + Texts.ITEMS + ":");
				for(int i=0;i<(q.onComplete().items().size()); i++)
				{
					Chat.noPrefixMessage(player, "     " + ChatColor.GREEN + q.onComplete().items().get(i).get(1).toString() + " " + ChatColor.GOLD + Material.getMaterial(q.onComplete().items().get(i).get(0)).toString());
				}
			}
	}
	
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
	public static final String LEVEL_TOO_HIGH = "Your Level Is To High!";
	public static final String LEVEL_TOO_LOW = "Your Level Is To Low!";
	
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
	public static final String CANNOT_USE_CURRENTLY = "You Can't Use This When You Have This Quest!";
	
	// Config Words
	public static final String QUEST = "Quest";
	public static final String INVALID = "Has A Invalid";
	public static final String NEXT_QUEST = "Next Quest";
	public static final String REPEATABLE = "Repeatable";
	
	// Reward Names
	public static final String MONEY = "Money";
	public static final String HEALTH = "Health";
	public static final String FOOD = "Food";
	public static final String COMMANDS = "Commands";
	public static final String LEVELSET = "Set Level";
	public static final String LEVELADD = "Add To Level";
	public static final String ITEMS = "Items";
	
	// Not Enough Fees
	public static final String NOT_ENOUGH_MONEY = "You Don't Have Enough Money!";
	public static final String NOT_ENOUGH_HEALTH = "You Don't Have Enough Health!";
	public static final String NOT_ENOUGH_FOOD = "You Don't Have Enough Food!";
	public static final String NOT_ENOUGH_ITEMS = "You Don't Have Enough Items!";
}
