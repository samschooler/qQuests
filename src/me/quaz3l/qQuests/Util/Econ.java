package me.quaz3l.qQuests.Util;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

public class Econ 
{
	public static Economy economy;
	
	public static boolean econChangeBalancePlayer(Player player, double amount) 
	{
        String name = player.getName();
        //String economyScale = qQuests.plugin.getConfig().getString("economyScale");
        //try {
        //	String[] size = economyScale.split("X");
        //	String amountString = amount + "";
        //	amount = Double.parseDouble(amountString + size[1]);
        //}
        //catch(Exception e)
        //{
        //	qQuests.plugin.logger.warning("Your economyScale is not formatted in the corecct format is 00X.00 and move the X to where you want the money values from the quest.yml config to be put!");
        //	return false;
        //}
        
        if (economy.bankBalance(name) != null) 
        {
        	if(amount < 0) 
        	{
        		economy.withdrawPlayer(name, amount * -1);
        	}
        	economy.depositPlayer(name, amount);
            return true;
        }
        
        return false;
    }
	
    public static String format(double amount) 
    {
        return economy.format(amount).replace(".00", "");
    }
}
