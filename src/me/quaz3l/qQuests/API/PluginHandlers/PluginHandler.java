package me.quaz3l.qQuests.API.PluginHandlers;

import java.util.HashMap;

import me.quaz3l.qQuests.API.PluginModels.qPlugin;
import me.quaz3l.qQuests.Util.Chat;

public class PluginHandler {
	private HashMap<String, qPlugin> plugins = new HashMap<String, qPlugin>();

	/**
	 * Used to connect a qPlugin with qQuests properly
	 * @param plugin - The qPlugin to be added
	 */
	public void addPlugin(qPlugin plugin) {
		if(plugin.getName() == null) {
			Chat.logger("debug", "A plugin that was added does not have a name! Plugins need to be named with the name function!");
			return;
		}
		this.plugins.put(plugin.getName(), plugin);
		plugin.onEnable();
	}
	/**
	 * Calls the enable function in all qPlugins
	 */
	public void callEnable() {
		for(qPlugin pl:this.plugins.values()) {
			pl.onEnable();
		}
	}

	/**
	 * Calls the disable function in all qPlugins
	 */
	public void callDisable() {
		for(qPlugin pl:this.plugins.values()) {
			pl.onDisable();
		}
	}
}
