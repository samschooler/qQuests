package me.quaz3l.qQuests.API.Util.Events;

import me.quaz3l.qQuests.API.Build.Events.BuildonDrop;

public class onDrop {
	public String message;
	public Integer money;
	public Integer health;
	public Integer hunger;

	public onDrop(BuildonDrop build) 
	{
		message = build.message();
		money = build.money();
		health = build.health();
		hunger = build.hunger();
	}
}
