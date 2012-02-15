package me.quaz3l.qQuests.API.Build;

import me.quaz3l.qQuests.API.Util.Reward;

public class BuildReward {
	public Integer no;
	public Integer money;
	public Integer health;
	public Integer hunger;
	
	public BuildReward(Integer reward) {
		this.no = reward;
	}
	public Reward create() {
		return new Reward(this);
	}
}
