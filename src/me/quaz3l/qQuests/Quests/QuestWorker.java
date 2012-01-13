package me.quaz3l.qQuests.Quests;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

public class QuestWorker
{
	
	private static final Map<String, Quest> quests = new HashMap<String, Quest>();
	
	public void buildQuests(FileConfiguration questConfig)
	{
		for (Object questName : questConfig.getKeys(false)) 
		{
			String root = questName.toString();
			BuildQuest quest = new BuildQuest(root);
			quest.messageStart = questConfig.getString(questName + ".info.messageStart");
			quest.messageEnd = questConfig.getString(questName + ".info.messageEnd");
			quest.tasksOrdered = questConfig.getBoolean(questName + ".info.tasksOrdered");
			this.rememberQuest(quest.create());
		}
	}
	public void rememberQuest(Quest quest) 
	{
		quests.put(quest.name.toLowerCase(), quest);
	}
}
