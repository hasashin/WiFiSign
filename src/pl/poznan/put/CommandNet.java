package pl.poznan.put;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandNet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(args.length != 1){
            if(sender instanceof Player){
                ((Player)sender).sendMessage("Type the network name");
            }
            else{
                WiFiSign.plugin.getLogger().info("Type the network name");
            }
            return false;
        }
        ArrayList<Peer> con = WiFiSign.Connections.get(args[0]);
        if(sender instanceof Player){
            ((Player)sender).sendMessage("[WiFiSign] "+con.size() + " elements in network "+args[0]);
        }
        else{
            WiFiSign.plugin.getLogger().info(con.size() + " elements in network "+args[0]);
        }
        return true;
    }

}

