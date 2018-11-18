package uk.antiperson.stackspawner;

import org.bstats.bukkit.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;
import uk.antiperson.stackspawner.listeners.BlockBreak;
import uk.antiperson.stackspawner.listeners.BlockPlace;
import uk.antiperson.stackspawner.listeners.SpawnerSpawn;

public class StackSpawner extends JavaPlugin{

    public Config config = new Config(this);
    public SpawnerTools tools = new SpawnerTools(this);
    private SpawnerStorage storage = new SpawnerStorage(this);

    @Override
    public void onEnable(){
        getLogger().info("StackSpawner v" + getDescription().getVersion() + " by antiPerson");
        getLogger().info("Documentation can be found at " + getDescription().getWebsite());

        config.load();
        getLogger().info("Loading stacks from storage...");
        storage.load();

        getLogger().info("Registering listeners, tasks and commands...");
        registerEvents();
        new MetricsLite(this);
    }

    @Override
    public void onDisable(){
        getServer().getScheduler().cancelTasks(this);
        storage.save();
    }

    public void registerEvents(){
        // register listeners
        getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new SpawnerSpawn(this), this);
        getCommand("stackspawner").setExecutor(new Commands(this));
    }
}
