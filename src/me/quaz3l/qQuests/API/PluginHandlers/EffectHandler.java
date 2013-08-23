package me.quaz3l.qQuests.API.PluginHandlers;

import java.util.HashMap;

import me.quaz3l.qQuests.API.PluginModels.qEffect;
import me.quaz3l.qQuests.Util.Chat;

public class EffectHandler {
	private HashMap<String, qEffect> effects = new HashMap<String, qEffect>();

	/**
	 * Used to connect a qEffect with qQuests properly
	 * @param effect - The qEffect to be added
	 */
	public void addEffect(Object req) {
		Chat.logger("debug", "Adding effect!!");
		qEffect effect = (qEffect) req;
		if(effect.getName() == null) {
			Chat.logger("debug", "A effect that was added does not have a name! Effects need to be named with the name method!");
			return;
		}
		this.effects.put(effect.getName(), effect);
		effect.onEnable();

		Chat.logger("debug", "Added effect: " + effect.getName());
		Chat.logger("debug", "Effect count: " + effects.size());
	}
	/**
	 * Checks if a node have a qEffect yet
	 * @param The string to check
	 */
	public boolean isEffect(String effect) {
		Chat.logger("debug", "isEffect():" + this.effects.containsKey(effect));
		return this.effects.containsKey(effect);
		
	}
	/**
	 * Returns the qEffect class for a effect string
	 * @param The string to get
	 */
	public qEffect getEffect(String effect) {
		Chat.logger("debug", "getEffect():" + this.effects.containsKey(effect));
		return this.effects.get(effect);
		
	}
	/**
	 * 
	 * @param player - The player to check
	 * @param effects - The effects to check
	 * @return If the player meets the effects
	 */
	public boolean checkEffects(String player, HashMap<String, Object> effects) {		
		Chat.logger("debug", "Req: "+ effects.keySet().toString()+"");
		for(String name : effects.keySet()) {
			Chat.logger("debug", name);
			Chat.logger("debug", player);
			if(this.isEffect(name)) {
				int result = this.effects.get(name).passedRequirement(player, effects.get(name));
				if(result != 0) {
					// TODO Send the message to the qPlugin to handle
					Chat.error(player, this.effects.get(name).parseError(player, effects.get(name), result));
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Runs through all effects and executes them
	 * @param player - The player to execute on
	 * @param effects - The effects to execute
	 */
	public void executeEffects(String player, HashMap<String, Object> effects) {		
		Chat.logger("debug", "Req: "+ effects.keySet().toString()+"");
		for(String name : effects.keySet()) {
			Chat.logger("debug", name);
			Chat.logger("debug", player);
			if(this.isEffect(name)) {
				this.effects.get(name).executeEffect(player, effects.get(name));
			}
		}
	}
	
	/**
	 * Calls the enable function in all qEffects
	 */
	public void callEnable() {
		for(qEffect req:this.effects.values()) {
			req.onEnable();
		}
	}
	/**
	 * Calls the disable function in all qEffects
	 */
	public void callDisable() {
		for(qEffect req:this.effects.values()) {
			req.onDisable();
		}
	}
	public boolean validate(String origin, String key, Object value) {
		int result = this.effects.get(key).validate(value);
		if(result != 0) {
			Chat.logger("severe", "[" + origin + "."+ key + "](Effect): " + this.effects.get(key).parseError(null, value, result));
			return false;
		} else return true;
	}
}
