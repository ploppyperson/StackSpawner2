package uk.antiperson.stackspawner;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;

public class StackedSpawner {

    private ArmorStand armorStand;
    private CreatureSpawner spawner;
    private StackSpawner ss;
    public StackedSpawner(StackSpawner ss, CreatureSpawner spawner){
        this.ss = ss;
        this.spawner = spawner;
        armorStand = SpawnerTools.getArmorStand(spawner.getLocation());
    }

    public CreatureSpawner getSpawner() {
        return spawner;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Location getLocation(){
        return spawner.getLocation();
    }

    public int getSize(){
        return SpawnerStorage.getSize(spawner);
    }

    public void setSize(int newSize){
        if(newSize == 1){
            SpawnerStorage.removeStatus(spawner);
        }
        SpawnerStorage.setSize(spawner, newSize);
        if(ss.config.getConfig().getInt("tag.remove-at") >= newSize){
            if(armorStand != null){
                armorStand.remove();
                armorStand = null;
            }
            return;
        }
        if(armorStand == null){
            armorStand = SpawnerTools.spawnStand(getLocation());
        }
        ss.tools.updateTag(this);
    }
}
