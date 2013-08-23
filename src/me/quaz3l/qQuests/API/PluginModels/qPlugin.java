package me.quaz3l.qQuests.API.PluginModels;

public abstract class qPlugin {
	
	/**
	 * @return The unique name of the qPlugin
	 */
	public abstract String getName();
	
	/**
	 * Called when a qPlugin is added to qQuests
	 */
	public abstract void onEnable();
	/**
	 * Called when a qPlugin is disabled in qQuests
	 */
	public abstract void onDisable();
}
