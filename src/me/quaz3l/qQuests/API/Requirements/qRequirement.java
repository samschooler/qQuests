package me.quaz3l.qQuests.API.Requirements;

import me.quaz3l.qQuests.Plugins.qPlugin;

public abstract class qRequirement extends qPlugin {
	/**
	 * This checks if a player passes the requirement
	 * @param player - The player to check
	 * @param value - The value of the requirement
	 * @return -1 and lower - Fatal error; configuration/server error; Output to log, and player if has qquests.admin.errors perms (ex. the amount of money in the quests.yml is not a number)
	 * 			0           - Passed; No output on qQuests' side (ex. has enough money)
	 * 		    1 and up    - Failed; Not serious, the player needs to fix something (ex. get more money)
	 */
	public abstract int passedRequirement(String player, Object value);
	
	/**
	 * This is used to validate the object going into the passedRequirement method from the configuration.
	 * @param value - The value of the requirement
	 * @return -1 and lower - Fatal error; configuration/server error; Output to log, and player if has qquests.admin.errors perms (ex. the amount of money in the quests.yml is not a number)
	 * 			0           - Passed.
	 */
	public abstract int validate(Object value);
	
	/**
	 * This parses the error codes spit out from the methods above
	 * @param player - The player to check
	 * @param value - The value of the requirement
	 * @param errorCode - An integer representing an error, which points to a specific string.
	 * @return The error message to send to the player if positive, or the server log if negative.
	 */
	public abstract String parseError(String player, Object value, int errorCode);
}
