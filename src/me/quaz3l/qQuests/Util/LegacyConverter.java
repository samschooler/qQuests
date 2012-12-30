package me.quaz3l.qQuests.Util;

import me.quaz3l.qQuests.qQuests;

public class LegacyConverter {
	public static void convert(int fixNo)
	{
		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();
			
			switch(fixNo) {
			case 0:
				qQuests.plugin.Config.getQuestConfig().set(root + ".onComplete.delay", 
						qQuests.plugin.Config.getQuestConfig().getInt(root + ".setup.delay")*60);
				qQuests.plugin.Config.getQuestConfig().set(root + ".setup.delay", null);
				qQuests.plugin.Config.saveQuestConfig();
				break;
			case 1:
				qQuests.plugin.Config.getQuestConfig().set(root + ".onComplete.nextQuest", 
						qQuests.plugin.Config.getQuestConfig().getString(root + ".setup.nextQuest"));
				qQuests.plugin.Config.getQuestConfig().set(root + ".setup.nextQuest", null);
				qQuests.plugin.Config.saveQuestConfig();
				break;
			}
			
		}
	}
}
