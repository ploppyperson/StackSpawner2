package uk.antiperson.stackspawner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    private StackSpawner ss;
    private boolean confirmed = false;
    private final static String PLUGIN_TAG = ChatColor.AQUA + "StackSpawner " + ChatColor.GRAY + ">> ";
    public Commands(StackSpawner ss){
        this.ss = ss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args){
        if(sender.hasPermission("stackspawner.commands")){
            if(args.length == 0){
                sender.sendMessage(PLUGIN_TAG + ChatColor.GOLD + "Plugin Commands: ");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ss convert " + ChatColor.GREEN + "Converts stack data from other plugins.");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ss about " + ChatColor.GREEN + "Shows information about the plugin.");
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("about")){
                    sender.sendMessage(PLUGIN_TAG + ChatColor.GREEN + "StackSpawner v" + ss.getDescription().getVersion() + " by antiPerson");
                    sender.sendMessage(PLUGIN_TAG + ChatColor.GREEN + "Find out more at " + ss.getDescription().getWebsite());
                }else if(args[0].equalsIgnoreCase("convert")){
                    if(confirmed){
                        DataConversion conversion = new DataConversion(ss);
                        conversion.convert();
                        sender.sendMessage(PLUGIN_TAG + ChatColor.GREEN + "Converted stacked spawners!");
                    }else{
                        sender.sendMessage(PLUGIN_TAG + ChatColor.YELLOW + "Notice: Onmi spawners will loose their conbimed types, but not the combined value.");
                        sender.sendMessage(PLUGIN_TAG + ChatColor.YELLOW + "Notice: New name tags may not appear in unloaded chunks, breaking and replacing them solves this.");
                        sender.sendMessage(PLUGIN_TAG + ChatColor.YELLOW + "Type '/ss convert' again to continue.");
                        confirmed = true;
                    }
                }
            }
        }
        return false;
    }
}
