package uk.antiperson.stackspawner;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnerTools {

    private StackSpawner ss;
    public SpawnerTools(StackSpawner ss){
        this.ss = ss;
    }

    public CreatureSpawner getNearbySpawner(CreatureSpawner newSpawner, int searchRadius){
        Location loc = newSpawner.getLocation();
        for(int x = -(searchRadius); x <= searchRadius; x++ ){
            for(int y = -(searchRadius); y <= searchRadius; y++ ){
                for(int z = -(searchRadius); z <= searchRadius; z++ ){
                    Block block = loc.getBlock().getRelative(x, y, z);
                    if(!(block.getState() instanceof CreatureSpawner)){
                        continue;
                    }
                    if(block.getLocation().equals(newSpawner.getLocation())){
                        continue;
                    }
                    CreatureSpawner nearby = (CreatureSpawner) block.getState();
                    if(newSpawner.getSpawnedType() != nearby.getSpawnedType()){
                        continue;
                    }
                    if(!SpawnerStorage.isStackedSpawner(nearby)){
                        SpawnerStorage.setSize(nearby, 1);
                    }
                    int size = SpawnerStorage.getSize(nearby);
                    if(size == ss.config.getConfig().getInt("max-size")){
                        continue;
                    }
                    return nearby;
                }
            }
        }
        return null;
    }

    public static ArmorStand getArmorStand(Location location){
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        for(Entity entity :  location.getWorld().getNearbyEntities(location, 0.1, 0.1, 0.1)){
            if(entity instanceof ArmorStand){
                return (ArmorStand) entity;
            }
        }
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        return armorStand;
    }

    public void updateTag(CreatureSpawner spawner, ArmorStand armorStand){
        String original = ss.config.getConfig().getString("tag.format");
        String replace1 = original.replace("%size%", SpawnerStorage.getSize(spawner) + "");
        String replace2 = replace1.replace("%type%", WordUtils.capitalizeFully(spawner.getSpawnedType().toString()));
        String replace3 = replace2.replace("%bukkit_type%", spawner.getSpawnedType().toString());
        String replace4 = ChatColor.translateAlternateColorCodes('&', replace3);
        armorStand.setCustomName(replace4);
        armorStand.setCustomNameVisible(true);
    }
}
