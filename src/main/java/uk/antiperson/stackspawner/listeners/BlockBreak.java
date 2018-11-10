package uk.antiperson.stackspawner.listeners;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import uk.antiperson.stackspawner.SpawnerStorage;
import uk.antiperson.stackspawner.SpawnerTools;
import uk.antiperson.stackspawner.StackSpawner;

public class BlockBreak implements Listener {

    private StackSpawner ss;
    public BlockBreak(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawnerBreak(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof CreatureSpawner){
            CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();
            if(!SpawnerStorage.isStackedSpawner(creatureSpawner)){
                return;
            }
            int size = SpawnerStorage.getSize(creatureSpawner);
            if(size > 1) {
                event.setCancelled(true);
                ArmorStand as = SpawnerTools.getArmorStand(creatureSpawner.getLocation());
                int newSize = size - 1;
                if(newSize > 1) {
                    SpawnerStorage.setSize(creatureSpawner, newSize);
                    ss.tools.updateTag(creatureSpawner, as);
                }else{
                    SpawnerStorage.removeStatus(creatureSpawner);
                }

                if(ss.config.getConfig().getInt("tag.remove-at") == newSize){
                    as.remove();
                }
            }
        }
    }
}
