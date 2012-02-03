package me.quaz3l.qQuests.API.Build;

import me.quaz3l.qQuests.API.Util.Reward;

public class BuildReward {
	public Integer no;
	public String money;
	public Integer health;
	public String hunger;
	
	public BuildReward(Integer reward) {
		this.no = reward;
	}
	public Reward create() {
		return new Reward(this);
	}
}
