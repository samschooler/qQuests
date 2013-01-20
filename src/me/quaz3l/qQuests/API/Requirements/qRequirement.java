package me.quaz3l.qQuests.API.Requirements;

import me.quaz3l.qQuests.Plugins.qPlugin;

public abstract class qRequirement extends qPlugin {
	/**
	 * @param player - The player to check
	 * @param value - The value of the requirement
	 * @return If the player passes the requirement
	 */
	public abstract boolean passedRequirement(String player, Object value);
	
	/**
	 * 
	 * @param value - The value of the requirement
	 * @return If the requirement is valid
	 */
	public abstract boolean validate(Object value);
}
