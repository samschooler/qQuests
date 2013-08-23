package me.quaz3l.qQuests.API.Requirements;

import java.util.HashMap;

import me.quaz3l.qQuests.Util.Chat;

public class RequirementHandler {
	private HashMap<String, qRequirement> requirements = new HashMap<String, qRequirement>();

	/**
	 * Used to connect a qRequirement with qQuests properly
	 * @param requirement - The qRequirement to be added
	 */
	public void addRequirement(Object req) {
		Chat.logger("debug", "Adding requirement!!");
		qRequirement requirement = (qRequirement) req;
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
	public boolean checkRequirements(String player, HashMap<String, Object> requirements, boolean silent) {		
		Chat.logger("debug", "Req: "+ requirements.keySet().toString()+"");
		for(String name : requirements.keySet()) {
			Chat.logger("debug", name);
			Chat.logger("debug", player);
			if(this.isRequirement(name)) {
				int result = this.requirements.get(name).passedRequirement(player, requirements.get(name));
				if(result != 0) {
					// TODO Send the message to the qPlugin to handle
					if(!silent)
						Chat.error(player, this.requirements.get(name).parseError(player, requirements.get(name), result));
					return false;
				}
			}
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
	public boolean validate(String origin, String key, Object value) {
		int result = this.requirements.get(key).validate(value);
		if(result != 0) {
			Chat.logger("severe", "[" + origin + "."+ key + "](Requirement): " + this.requirements.get(key).parseError(null, value, result));
			return false;
		} else return true;
	}
}
