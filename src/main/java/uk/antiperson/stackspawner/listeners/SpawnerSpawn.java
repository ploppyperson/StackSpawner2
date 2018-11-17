package uk.antiperson.stackspawner.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import uk.antiperson.stackspawner.SpawnerTools;
import uk.antiperson.stackspawner.StackMobSupport;
import uk.antiperson.stackspawner.StackSpawner;
import uk.antiperson.stackspawner.StackedSpawner;

public class SpawnerSpawn implements Listener {

    private StackSpawner ss;
    public SpawnerSpawn(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event){
        StackedSpawner stackedSpawner = SpawnerTools.getStackedSpawner(ss, event.getSpawner());
        if(stackedSpawner == null){
            return;
        }
        if(stackedSpawner.getSize() > 1){
            if(StackMobSupport.isEnabled()){
                StackMobSupport.spawnEntities(event.getEntity(), stackedSpawner);
                return;
            }
            for(int i = 0;  i < stackedSpawner.getSize(); i++){
                if(SpawnerTools.attemptSpawn(event.getSpawner().getLocation(), event.getEntityType()) == null){
                    i = i - 1;
                }
            }
        }
    }
}
