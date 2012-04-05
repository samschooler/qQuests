package me.quaz3l.qQuests.Plugins;

import me.quaz3l.qQuests.qQuests;
import me.quaz3l.qQuests.API.QuestModels.Quest;
import me.quaz3l.qQuests.Util.Chat;
import me.quaz3l.qQuests.Util.Storage;
import me.quaz3l.qQuests.Util.Texts;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.tal.redstonechips.circuit.Circuit;

public class QQCircuit extends Circuit
{
	  private qQuests plugin = null;
	  private Location center;
	  private int radius = 10;
	  private String questId;
	  private String task = null;

	  public void inputChange(int inIdx, boolean state)
	  {
		  int result= -1;
	    if (state)
	    {
	      for (Player p : this.world.getPlayers()) {
	        Location l = p.getLocation();

	        if (!isInRadius(this.center, l, this.radius))
	        	continue;
	        if(!this.plugin.qAPI.hasActiveQuest(p))
	        	continue;
	        
	        Quest q = this.plugin.qAPI.getActiveQuest(p);
	        if(this.plugin.qAPI.getActiveQuest(p).name() == this.questId)
	        {
	        	
	        }
	        
	        if (this.task.equals("detect"))
	        	continue;
	        if (this.task.equals("done"))
	        {
	        	
	        } else if (this.task.equals("give"))
	        {
	        	result = qQuests.plugin.qAPI.giveQuest(p, this.questId, true);
	        	if(result == 0)
	        	{
	        		Storage.wayCurrentQuestsWereGiven.put(p, "Circuit");
	        		Chat.message(p, qQuests.plugin.qAPI.getActiveQuest(p).onJoin().message());
	        	}
	        	else if(result == 1)
	        		Chat.error(p, Texts.NOT_VALID_QUEST);
	        	else
	        		Chat.error(p, Chat.errorCode(result, "Circuit"));
	        }
	        else if (this.task.equals("drop"))
	        {
	        	result = qQuests.plugin.qAPI.dropQuest(p);
	        	if(result == 0)
	        		Chat.message(p, q.onDrop().message());
	        	else
	        		Chat.error(p, Chat.errorCode(result, "Circuit"));
	        } else if (this.task.equals("info"))
	        {
	        	
	        } else if (this.task.equals("tasks"))
	        {
	        	
	        } else 
	        {
	        	
	        }
	      }
	      
	      if(result == 0)
	    	  sendOutput(0, true);
	      else
	    	  sendOutput(0, false);
	    }
	  }

	  protected boolean init(CommandSender sender, String[] args)
	  {
	    if (this.interfaceBlocks.length != 1) {
	    	Chat.error(sender, "Expecting 1 interface block.");
	      return false;
	    }

	    if (this.inputs.length != 1) {
	    	Chat.error(sender, "Expecting 1 clock input pin.");
	      return false;
	    }

	    if (this.outputs.length != 1) {
	    	Chat.error(sender, "Expecting 1 signal output.");
	      return false;
	    }
	    switch (args.length)
	    {
	    case 0:
	    	Chat.error(sender, "No quest name given as input for arg 1.");
	      break;
	    case 1:
	      Chat.error(sender, "No circuit type was given for arg 2('detect','give','drop','completed','finish','clear') a quest).");
	    case 3:
	      try {
	        this.radius = Integer.decode(args[1]).intValue();
	      }
	      catch (NumberFormatException ne) {
	    	  Chat.error(sender, "Incorrect radius: " + args[1]);
	        return false;
	      }
	    case 2:
	      this.task = args[1];
	      this.questId = args[0];
	      if(!qQuests.plugin.qAPI.getQuests().containsKey(questId))
	      {
	    	  Chat.error(sender, "This quest is not a valid quest!");
	    	  return false;
	      }

	    }

	    error(sender, "Too many arguments.");

	    Location i = this.interfaceBlocks[0].getLocation();
	    this.center = i;

	    this.plugin = ((qQuests)Bukkit.getServer().getPluginManager().getPlugin("qQuests"));
	    if (this.plugin == null)
	    	Chat.error(sender, "qQuests not a registered plugin.");
	    return true;
	  }

	  private static boolean isInRadius(Location loc1, Location loc2, double radius) {
	    return (loc1.getX() - loc2.getX()) * (loc1.getX() - loc2.getX()) + (loc1.getY() - loc2.getY()) * (loc1.getY() - loc2.getY()) + (loc1.getZ() - loc2.getZ()) * (loc1.getZ() - loc2.getZ()) <= radius * radius;
	  }
	}