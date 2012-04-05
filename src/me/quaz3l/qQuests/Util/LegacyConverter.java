package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

public class LegacyConverter {
	public static void convert()
	{
		for(Object i : qQuests.plugin.Config.getQuestConfig().getKeys(false))
		{
			String name = qQuests.plugin.Config.getQuestConfig().getString(i + ".info.name");
			String messageStart = qQuests.plugin.Config.getQuestConfig().getString(i + ".info.messageStart");
			String messageEnd = qQuests.plugin.Config.getQuestConfig().getString(i + ".info.messageEnd");
			
			String type = qQuests.plugin.Config.getQuestConfig().getString(i + ".tasks.0.type");
			Integer id = qQuests.plugin.Config.getQuestConfig().getInt(i + ".tasks.0.object.id");
			String ids = qQuests.plugin.Config.getQuestConfig().getString(i + ".tasks.0.object.name");
			Integer amount = qQuests.plugin.Config.getQuestConfig().getInt(i + ".tasks.0.amount");
			
			Integer toJoinMoney = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toJoin.money");
			Integer toJoinHealth = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toJoin.health");
			Integer toJoinHunger = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toJoin.hunger");
			
			Integer toDropMoney = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toDrop.money");
			Integer toDropHealth = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toDrop.health");
			Integer toDropHunger = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.fee.toDrop.hunger");
			
			Integer rMoney = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.reward.money");
			Integer rHealth = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.reward.health");
			Integer rHunger = qQuests.plugin.Config.getQuestConfig().getInt(i + ".market.reward.hunger");
			
			qQuests.plugin.Config.getQuestConfig().set(i + "", null);
			
			qQuests.plugin.Config.getQuestConfig().set(name + ".tasks.0.type", type);
			if(type.equalsIgnoreCase("kill"))
				qQuests.plugin.Config.getQuestConfig().set(name + ".tasks.0.id", ids);
			else
				qQuests.plugin.Config.getQuestConfig().set(name + ".tasks.0.id", id);
			qQuests.plugin.Config.getQuestConfig().set(name + ".tasks.0.display", ids);
			qQuests.plugin.Config.getQuestConfig().set(name + ".tasks.0.amount", amount);
			
			qQuests.plugin.Config.getQuestConfig().set(name + ".onJoin.message", messageStart);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onJoin.money", toJoinMoney);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onJoin.health", toJoinHealth);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onJoin.hunger", toJoinHunger);
			
			qQuests.plugin.Config.getQuestConfig().set(name + ".onDrop.message", "Quest Dropped!");
			qQuests.plugin.Config.getQuestConfig().set(name + ".onDrop.money", toDropMoney);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onDrop.health", toDropHealth);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onDrop.hunger", toDropHunger);
			
			qQuests.plugin.Config.getQuestConfig().set(name + ".onComplete.message", messageEnd);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onComplete.money", rMoney);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onComplete.health", rHealth);
			qQuests.plugin.Config.getQuestConfig().set(name + ".onComplete.hunger", rHunger);
			
			
		}
	}
}
