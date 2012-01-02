package me.quaz3l.qQuests.Util;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

public class Econ 
{
	public static Economy economy;
	
	public static boolean econChangeBalancePlayer(Player player, double amount) 
	{
        String name = player.getName();
        
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
