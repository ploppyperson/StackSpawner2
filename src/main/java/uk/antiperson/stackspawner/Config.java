package uk.antiperson.stackspawner;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {

    private StackSpawner t;
    private File file;
    private FileConfiguration fileConfiguration;
    public Config(StackSpawner t){
        this.t = t;
        file = new File(t.getDataFolder(), "config.yml");
    }

    public void load(){
        if(!file.exists()){
            t.saveResource("config.yml", false);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return fileConfiguration;
    }

}
