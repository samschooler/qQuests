package me.quaz3l.qQuests.Plugins;

public abstract class qPlugin {
	
	/**
	 * @return The unique name of the qPlugin
	 */
	public abstract String getName();
	
	/**
	 * Called when a qPlugin is added to qQuests
	 */
	public abstract void onEnable();
	public abstract void onDisable();
}
