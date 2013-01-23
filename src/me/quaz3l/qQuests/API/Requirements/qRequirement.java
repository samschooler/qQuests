package me.quaz3l.qQuests.API.Requirements;

import me.quaz3l.qQuests.Plugins.qPlugin;

public class qRequirement extends qPlugin {
	/**
	 * @param player - The player to check
	 * @param value - The value of the requirement
	 * @return If the player passes the requirement
	 */
	public boolean passedRequirement(String player, Object value) {return false;}
	
	/**
	 * 
	 * @param value - The value of the requirement
	 * @return If the requirement is valid
	 */
	public boolean validate(Object value) {return false;}

	@Override
	public String getName() {return null;}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}
}
