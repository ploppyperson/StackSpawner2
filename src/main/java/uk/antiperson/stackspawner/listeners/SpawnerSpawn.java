package uk.antiperson.stackspawner.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import uk.antiperson.stackspawner.SpawnerStorage;
import uk.antiperson.stackspawner.StackSpawner;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnerSpawn implements Listener {

    private StackSpawner ss;
    public SpawnerSpawn(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event){
        if(!SpawnerStorage.isStackedSpawner(event.getSpawner())){
            return;
        }
        int oldSize = SpawnerStorage.getSize(event.getSpawner());
        if(oldSize > 1){
            for(int i = 0;  i < oldSize; i++){
                int randX = ThreadLocalRandom.current().nextInt(-5,5);
                int randZ = ThreadLocalRandom.current().nextInt(-5,5);
                Location loc =  event.getSpawner().getLocation().add(randX + 0.5, 0, randZ + 0.5);
                if(loc.getBlock().isEmpty()){
                    event.getSpawner().getWorld().spawnEntity(loc, event.getEntityType());
                }else{
                    i = i - 1;
                }
            }
        }
    }
}
