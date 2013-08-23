package me.quaz3l.qQuests.API.PluginModels;


public abstract class qEffect extends qRequirement {
	/**
	 * Executes an effect
	 * @param player - The player that called the effect
	 * @param value - The value of the effect
	 */
	public abstract void executeEffect(String player, Object value);
}
