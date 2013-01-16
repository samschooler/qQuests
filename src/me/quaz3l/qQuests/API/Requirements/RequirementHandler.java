package me.quaz3l.qQuests.API.Requirements;

import java.util.HashMap;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.Util.Chat;

public class RequirementHandler {
	private HashMap<String, qRequirement> requirements = new HashMap<String, qRequirement>();

	/**
	 * Used to connect a qRequirement with qQuests properly
	 * @param requirement - The qRequirement to be added
	 */
	public void addRequirement(qRequirement requirement) {
		if(requirement.getName() == null) {
			Chat.logger("debug", "A requirement that was added does not have a name! Requirements need to be named with the name function!");
			return;
		}
		this.requirements.put(requirement.getName(), requirement);
		requirement.onEnable();
		
		Chat.logger("debug", "Added requirement: " + requirement.getName());
		Chat.logger("debug", "Requirement count: " + requirements.size());
	}
	/**
	 * Checks if a node have a qRequirement yet
	 * @param The string to check
	 */
	public boolean isRequirement(String requirement) {
		return this.requirements.containsKey(requirement);
	}
	/**
	 * 
	 * @param player - The player to check
	 * @param quest - The quest to check
	 * @return If the player meets the requirements
	 */
	public boolean checkRequirements(String player, String quest) {		
		Chat.logger("debug", "Req: "+ qQuests.plugin.qAPI.getQuest(quest).requirements().keySet().toString()+"");
		for(String name : qQuests.plugin.qAPI.getQuest(quest).requirements().keySet()) {
			Chat.logger("debug", quest);
			Chat.logger("debug", name);
			if(!this.requirements.get(name).passedRequirement(player, quest))
				return false;
		}
		return true;
	}
	
	/**
	 * Calls the enable function in all qRequirements
	 */
	public void callEnable() {
		for(qRequirement req:this.requirements.values()) {
			req.onEnable();
		}
	}

	/**
	 * Calls the disable function in all qRequirements
	 */
	public void callDisable() {
		for(qRequirement req:this.requirements.values()) {
			req.onDisable();
		}
	}
	public boolean validate(String key, Object value) {
		return this.requirements.get(key).validate(value);
	}
}
