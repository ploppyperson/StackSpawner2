package uk.antiperson.stackspawner;

import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpawnerStorage {

    private static HashMap<String, Integer> spawnerSize = new HashMap<>();
    private File file;
    private FileConfiguration fileConfiguration;
    public SpawnerStorage(StackSpawner ss){
        file = new File(ss.getDataFolder(), "store.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static int getSize(CreatureSpawner spawner){
        return spawnerSize.get(locToString(spawner.getLocation()));
    }

    public static void setSize(CreatureSpawner spawner, int newSize){
        spawnerSize.put(locToString(spawner.getLocation()), newSize);
    }

    public static boolean isStackedSpawner(CreatureSpawner spawner){
        return spawnerSize.containsKey(locToString(spawner.getLocation()));
    }

    public static void removeStatus(CreatureSpawner spawner){
        spawnerSize.remove(locToString(spawner.getLocation()));
    }

    public static void putDirectly(String loc, int size){
        spawnerSize.put(loc, size);
    }

    public void load(){
        for(String a : fileConfiguration.getKeys(false)){
            spawnerSize.put(a, fileConfiguration.getInt(a));
        }
        file.delete();
    }

    public void save(){
        for(Map.Entry<String, Integer> entry : spawnerSize.entrySet()){
            fileConfiguration.set(entry.getKey(), entry.getValue());
        }
        try {
            fileConfiguration.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String locToString(Location location){
        return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }
}
