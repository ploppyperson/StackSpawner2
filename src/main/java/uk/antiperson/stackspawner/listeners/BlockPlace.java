package uk.antiperson.stackspawner.listeners;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.antiperson.stackspawner.SpawnerTools;
import uk.antiperson.stackspawner.StackSpawner;
import uk.antiperson.stackspawner.StackedSpawner;

public class BlockPlace implements Listener {

    private StackSpawner ss;
    public BlockPlace(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawnerBreak(BlockPlaceEvent event){
        if(event.getBlock().getState() instanceof CreatureSpawner){
            int searchRadius = ss.config.getConfig().getInt("search-radius");
            CreatureSpawner placedSpawner = (CreatureSpawner) event.getBlock().getState();
            CreatureSpawner nearbySpawner = ss.tools.getNearbySpawner(placedSpawner, searchRadius);
            if(nearbySpawner == null){
                return;
            }

            StackedSpawner stackedSpawner = SpawnerTools.getStackedSpawner(ss, nearbySpawner);
            if(stackedSpawner != null){
                ss.getServer().getScheduler().runTask(ss, () -> event.getBlock().setType(Material.AIR));
                stackedSpawner.setSize(stackedSpawner.getSize() + 1);
            }
        }
    }


}
