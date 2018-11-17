package uk.antiperson.stackspawner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;

public class DataConversion {

    private StackSpawner ss;
    public DataConversion(StackSpawner ss){
        this.ss = ss;
    }

    public void convert(){
        String folder = ss.getDataFolder().getParent() + File.separator + "EpicSpawners";
        File file = new File(folder,"data.yml");
        if(!file.exists()){
            ss.getLogger().info("Nothing to convert.");
        }
        FileConfiguration fileCon = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = fileCon.getConfigurationSection("data.spawners");
        for(String key : section.getKeys(false)){
            String[] locs = section.getString(key + ".location").split(":");
            World world = Bukkit.getWorld(locs[0]);
            int xCord = Integer.valueOf(locs[1]);
            int yCord = Integer.valueOf(locs[2]);
            int zCord = Integer.valueOf(locs[3]);
            Location location = new Location(world, xCord, yCord, zCord);

            String[] sizes = section.getString(key + ".stacks").split(";");
            int stackSize = 0;
            for(String size : sizes){
                String[] sizes2 = size.split(":");
                stackSize = stackSize + Integer.valueOf(sizes2[1]);
            }

            Location oldTagLoc = location.clone().add(0.5, 1, 0.5);
            location.getWorld().getNearbyEntities(oldTagLoc, 0.1, 0.1, 0.1).forEach(Entity::remove);

            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
            StackedSpawner spawner = new StackedSpawner(ss, creatureSpawner);
            spawner.setSize(stackSize);
        }
    }
}
