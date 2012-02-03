package me.quaz3l.qQuests.API.Util;

import me.quaz3l.qQuests.API.Build.BuildReward;

public class Reward {
	public Integer no;
	public String money;
	public Integer health;
	public String hunger;

	public Reward(BuildReward build) 
	{
		no = build.no;
		money = build.money;
		health = build.health;
		hunger = build.hunger;
	}
}
