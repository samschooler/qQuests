package me.quaz3l.qQuests.Util;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class MobType {
	public static String isEntityType(Entity entity)
    {
        if (entity instanceof Player) return "Player";
        if(entity instanceof Sheep) return "Sheep";
        if(entity instanceof Cow) return "Cow";
        if(entity instanceof Pig) return "Pig";
        if(entity instanceof Creeper) return "Creeper";
        if(entity instanceof PigZombie) return "PigZombie";
        if(entity instanceof Skeleton)return "Skeleton";
        if(entity instanceof Spider) return "Spider";
        if(entity instanceof Squid) return "Squid";
        if(entity instanceof Zombie) return "Zombie";
        if(entity instanceof Ghast) return "Ghast";
        if(entity instanceof Slime) return "Slime";
        if(entity instanceof Giant) return "Giant";
        if(entity instanceof Blaze) return "Blaze";
        if(entity instanceof CaveSpider) return "CaveSpider";
        if(entity instanceof Chicken) return "Chicken";
        if(entity instanceof Enderman) return "Enderman";
        if(entity instanceof MagmaCube) return "MagmaCube";
        if(entity instanceof MushroomCow) return "MushroomCow";
        if(entity instanceof Snowman) return "Snowman";
        if(entity instanceof Wolf) return "Wolf";
        return null;
    }
}
