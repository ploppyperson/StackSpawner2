package uk.antiperson.stackspawner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import uk.antiperson.stackmob.StackMob;
import uk.antiperson.stackmob.api.EntityManager;

public class StackMobSupport {

    public static StackMob getStackMob(){
        Plugin plugin = Bukkit.getPluginManager().getPlugin("StackMob");
        return (StackMob) plugin;
    }

    public static void spawnEntities(Entity original, StackedSpawner spawner){
        int maxSize = getEntityManager().getStackedEntity(original).getMaxStackSize();
        double a = (double) spawner.getSize() / (double) maxSize;
        int floor = (int) Math.floor(a);
        int leftOver = (int) Math.round((a - floor) * maxSize);
        for(int i = 0; i <= floor; i++){
            Entity entity = SpawnerTools.attemptSpawn(spawner.getLocation(), original.getType());
            if(entity == null){
                i = i - 1;
                continue;
            }
            getEntityManager().addNewStack(entity, maxSize);
        }
        getEntityManager().addNewStack(original, leftOver);
    }

    public static boolean isEnabled(){
        return getStackMob() != null && getStackMob().isEnabled();
    }

    public static EntityManager getEntityManager(){
        return new EntityManager(getStackMob());
    }
}
