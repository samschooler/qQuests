package me.quaz3l.qQuests.API.TaskTypes;

import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Plugins.qPlugin;

public class Task extends qPlugin {
	private Quest quest;
	private int index;
		
	public Task(Quest quest) {
		this.quest = quest;
	}
	
	public Quest getQuest() {
		return this.quest;
	}
	
	/**
	 * Set the index of the task
	 * @param i - index
	 */
	public void setIndex(int i) {
		this.index = i;
	}
	/**
	 * Get the index of the task
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Setup function called when the task is given
	 * @param player - Player to setup
	 */
	public void onGiven(String player) {

	}

	/**
	 * Check to see if the player has completed the task
	 * @param player - Player's name to check
	 */
	public boolean isComplete(String player) {
		return false;
	}

	/**
	 * Reset a player's values
	 * @param player - Player's name to reset
	 */
	public void resetPlayer(String player) {

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
}
