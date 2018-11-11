package uk.antiperson.stackspawner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    private StackSpawner ss;
    private final static String PLUGIN_TAG = ChatColor.AQUA + "StackSpawner " + ChatColor.GRAY + ">> ";
    public Commands(StackSpawner ss){
        this.ss = ss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args){
        if(sender.hasPermission("stackspawner.commands")){
            if(args.length == 0){
                sender.sendMessage(PLUGIN_TAG + ChatColor.GOLD + "Plugin Commands: ");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ss about " + ChatColor.GREEN + "Shows information about the plugin.");
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("about")){
                    sender.sendMessage(PLUGIN_TAG + ChatColor.GREEN + "StackSpawner v" + ss.getDescription().getVersion() + " by antiPerson");
                    sender.sendMessage(PLUGIN_TAG + ChatColor.GREEN + "Find out more at " + ss.getDescription().getWebsite());
                }
            }
        }
        return false;
    }
}
