package uk.antiperson.stackspawner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    private StackSpawner ss;
    public Commands(StackSpawner ss){
        this.ss = ss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args){
        if(sender.hasPermission("stackspawner.commands")){
            sender.sendMessage(ChatColor.GREEN + "StackSpawner " + ss.getDescription().getVersion() + " by antiPerson");
            sender.sendMessage(ChatColor.GREEN + "Find out more at " + ss.getDescription().getWebsite());
        }
        return false;
    }
}
