package me.quaz3l.qQuests.API.Util.Events;

import me.quaz3l.qQuests.API.Build.Events.BuildonComplete;

public class onComplete {
	public String message;
	public Integer money;
	public Integer health;
	public Integer hunger;

	public onComplete(BuildonComplete build) 
	{
		message = build.message();
		money = build.money();
		health = build.health();
		hunger = build.hunger();
	}
}
