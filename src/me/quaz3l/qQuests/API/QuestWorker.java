package me.quaz3l.qQuests.API;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.API.QuestModels.Task;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildQuest;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildTask;
import me.quaz3l.qQuests.API.QuestModels.Builders.BuildonSomething;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;


/**
 * This class is where all the quest manipulating action goes through
 */
public class QuestWorker
{    
	/**
	 * This function reloads the quests and is not to be used unless you need to have the quests.yml re-indexed.
	 */
	public void buildQuests()
	{
		Storage.quests.clear();
		Storage.currentQuests.clear();
		qQuests.plugin.Config.loadConfigs();

		for (Object questName :
			qQuests.plugin.Config.getQuestConfig()
			.getKeys(false)) 
		{
			String root = questName.toString();

			// Validate The Quest
			if(!this.validate(root)) 
				continue;

			BuildQuest quest = new BuildQuest(root);

			// Set Setup Variables
			if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".setup.repeated") == null)
				quest.repeated(-1);
			else
				quest.repeated(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".setup.repeated"));
			quest.invisible(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.invisible"));
			quest.forced(qQuests.plugin.Config.getQuestConfig().getBoolean(questName + ".setup.forced"));

			// Set Requirements Variables
			ConfigurationSection requireNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".requirements");
			Chat.logger("debug", "HIIRTRHD: " + questName + ".requirements");
			if(requireNode != null) {
				for(String key : requireNode.getKeys(false)) {
					Chat.logger("debug", root + ".requirements." + key);
					Object value = qQuests.plugin.Config.getQuestConfig().get(root + ".requirements." + key);
					quest.requirements(key, value);
				}
			}
			// Set Tasks Variables
			int i=0;
			for (Object taskNo : qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks").getKeys(false)) 
			{
				try
				{
					Integer tRoot = Integer.parseInt(taskNo.toString().trim());
					if(qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".tasks." + i).getKeys(false).size() <= 0)
						throw new NullPointerException();
					else
					{
						BuildTask task = new BuildTask(tRoot);
						task.type(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".type"));
						if(task.type().equalsIgnoreCase("collect") ||
								task.type().equalsIgnoreCase("destroy") ||
								task.type().equalsIgnoreCase("damage") ||
								task.type().equalsIgnoreCase("place") ||
								task.type().equalsIgnoreCase("enchant"))
						{
							String slug = qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id");
							String[] info = slug.split(":");
							Chat.logger("debug", "Slug: " + slug);
							Chat.logger("debug", "ID: " + info[0]);
							if(info.length == 2) {
								try {
									Chat.logger("debug", "DR: " + info[1]);
									task.id(Integer.parseInt(info[0]));
									task.durability(((Integer)Integer.parseInt(info[1])).shortValue());
								} catch(Exception e) {
									Chat.logger("severe", "The #" + tRoot +" task of '" + root + "' does not have valid material ids! Disabling this quest...");
									continue;
								}
							} else {
								try {
									task.id(Integer.parseInt(info[0]));
								} catch(Exception e) {
									Chat.logger("severe", "The #" + tRoot +" task of '" + root + "' does not have valid material ids! Disabling this quest...");
									continue;
								}
							}
						}
						else if(task.type().equalsIgnoreCase("kill") ||
								task.type().equalsIgnoreCase("kill_player") ||
								task.type().equalsIgnoreCase("tame"))
						{
							task.id(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id"));
						}
						else if(task.type().equalsIgnoreCase("goto")) {
							World world = qQuests.plugin.getServer().getWorld(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id.world"));
							double x1 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.1.x");
							double y1 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.1.y");
							double z1 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.1.z");
							double radius = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.1.radius");

							double x2 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.2.x");
							double y2 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.2.y");
							double z2 = qQuests.plugin.Config.getQuestConfig().getDouble(questName + ".tasks." + tRoot + ".id.2.z");

							if(world != null) {
								if(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".id.2.x") != null) {
									// Cuboid
									task.id(new Location(world, x1, y1, z1), new Location(world, x2, y2, z2), 0);
								} else {
									// Radius
									task.id(new Location(world, x1, y1, z1), null, radius);
								}
							} else {
								Chat.logger("severe", "The task nodes of quest '" + root + "' do not define a valid world name! Disabling this quest...");
								continue;
							}
						}
						task.display(qQuests.plugin.Config.getQuestConfig().getString(questName + ".tasks." + tRoot + ".display"));
						task.amount(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".tasks." + tRoot + ".amount"));
						this.rememberTask(tRoot, task.create(), quest);
					}
				}
				catch (NullPointerException e)
				{
					e.printStackTrace();
					Chat.logger("severe", "The task nodes of quest '" + root + "' do not start with 0 and go in order up! Disabling this quest...");
					continue;
				}
				catch (NumberFormatException e)
				{
					Chat.logger("severe", "The task nodes of quest '" + root + "' are not numbers! Disabling this quest...");
					continue;
				}
				i++;
			}

			// Set onJoin Variables
			BuildonSomething BuildonJoin = new BuildonSomething();

			// Set Effects Variables
			ConfigurationSection onJoinEffectNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".onJoin");
			Chat.logger("debug", "QuestWorker.onJoin: Node in config: " + questName + ".onJoin");
			if(onJoinEffectNode != null) {
				for(String key : onJoinEffectNode.getKeys(false)) {
					Chat.logger("debug", root + ".onJoin." + key);
					Object value = qQuests.plugin.Config.getQuestConfig().get(root + ".onJoin." + key);
					BuildonJoin.addEffect(key, value);
				}
			}

			BuildonJoin.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onJoin.message"));
			BuildonJoin.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onJoin.delay"));

			quest.onJoin(BuildonJoin);

			// Set onDrop Variables
			BuildonSomething BuildonDrop = new BuildonSomething();

			// Set Effects Variables
			ConfigurationSection onDropEffectNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".onDrop");
			Chat.logger("debug", "QuestWorker.onDrop: Node in config: " + questName + ".onDrop");
			if(onDropEffectNode != null) {
				for(String key : onDropEffectNode.getKeys(false)) {
					Chat.logger("debug", root + ".onDrop." + key);
					Object value = qQuests.plugin.Config.getQuestConfig().get(root + ".onDrop." + key);
					BuildonDrop.addEffect(key, value);
				}
			}

			BuildonDrop.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.message"));
			BuildonDrop.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onDrop.delay"));
			BuildonDrop.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onDrop.nextQuest"));
			quest.onDrop(BuildonDrop);

			// Set onComplete Variables
			BuildonSomething BuildonComplete = new BuildonSomething();

			// Set Effects Variables
			ConfigurationSection onCompleteEffectNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(questName + ".onComplete");
			Chat.logger("debug", "QuestWorker.onJoin: Node in config: " + questName + ".onComplete");
			if(onCompleteEffectNode != null) {
				for(String key : onCompleteEffectNode.getKeys(false)) {
					Chat.logger("debug", root + ".onComplete." + key);
					Object value = qQuests.plugin.Config.getQuestConfig().get(root + ".onComplete." + key);
					BuildonComplete.addEffect(key, value);
				}
			}

			BuildonComplete.message(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.message"));
			BuildonComplete.delay(qQuests.plugin.Config.getQuestConfig().getInt(questName + ".onComplete.delay"));
			BuildonComplete.nextQuest(qQuests.plugin.Config.getQuestConfig().getString(questName + ".onComplete.nextQuest"));

			quest.onComplete(BuildonComplete);

			// Only if the quest is valid, save the quest.
			this.rememberQuest(quest.create());
		}
		Chat.logger("info", Storage.quests.size() + " Quests Successfully Loaded Into Memory.");
	}

	private boolean validate(String root) {
		if(!this.validateRequirements(root, "requirements") ||
		   !this.validateEffects(root, "onJoin") ||
		   !this.validateEffects(root, "onDrop") ||
		   !this.validateEffects(root, "onComplete"))
		   return false;
		return true;
	}
	private boolean validateRequirements(String root, String section) {
		ConfigurationSection reqNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(root + "." +  section);
		if(reqNode == null) {
			return true;
		}
		for(String key : reqNode.getKeys(false)) {
			if(qQuests.plugin.qAPI.getRequirementHandler().isRequirement(key)) {
				Object value = qQuests.plugin.Config.getQuestConfig().get(root + "." + section + "." + key);
				if(!qQuests.plugin.qAPI.getRequirementHandler().validate(root + "." + section, key, value)) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean validateEffects(String root, String section) {
		ConfigurationSection effectNode = qQuests.plugin.Config.getQuestConfig().getConfigurationSection(root + "." + section);
		if(effectNode == null) {
			return true;
		}
		for(String key : effectNode.getKeys(false)) {
			if(qQuests.plugin.qAPI.getEffectHandler().isEffect(key)) {
				Object value = qQuests.plugin.Config.getQuestConfig().get(root + "." + section + "." + key);
				if(!qQuests.plugin.qAPI.getEffectHandler().validate(root + "." + section, key, value)) {
					return false;
				}
			}
		}
		return true;
	}
	private void rememberTask(Integer taskNo, Task task, BuildQuest quest) 
	{
		quest.tasks(taskNo, task);
	}
	private void rememberQuest(Quest quest) 
	{
		Storage.quests.put(quest.name().toLowerCase(), quest);
		if(!quest.invisible())
			Storage.visibleQuests.put(quest.name().toLowerCase(), quest);
	}
}