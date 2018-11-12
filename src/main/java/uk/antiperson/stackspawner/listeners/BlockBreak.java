package uk.antiperson.stackspawner.listeners;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import uk.antiperson.stackspawner.SpawnerTools;
import uk.antiperson.stackspawner.StackSpawner;
import uk.antiperson.stackspawner.StackedSpawner;

public class BlockBreak implements Listener {

    private StackSpawner ss;
    public BlockBreak(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawnerBreak(BlockBreakEvent event){
        if(event.getBlock().getState() instanceof CreatureSpawner){
            CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();
            StackedSpawner stackedSpawner = SpawnerTools.getStackedSpawner(ss, creatureSpawner);
            if(stackedSpawner == null){
                return;
            }

            if(stackedSpawner.getSize() > 1) {
                event.setCancelled(true);
                stackedSpawner.setSize(stackedSpawner.getSize() - 1);
            }
        }
    }
}
