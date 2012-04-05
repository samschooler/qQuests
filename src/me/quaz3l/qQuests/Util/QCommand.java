package me.quaz3l.qQuests.Util;

import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class QCommand extends Command
{
  protected final CommandExecutor owner;

  public QCommand(String[] aliases, String desc, String usage, CommandExecutor owner)
  {
    super(aliases[0], desc, usage, Arrays.asList(aliases));
    this.owner = owner;
  }

  public boolean execute(CommandSender sender, String label, String[] args)
  {
    return this.owner.onCommand(sender, this, label, args);
  }
}
