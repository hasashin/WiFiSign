package pl.poznan.put;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        StringBuilder sb = new StringBuilder();
        for(String net : WiFiSign.Connections.keySet()){
            sb.append(net+", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        if(sender instanceof Player){
            ((Player)sender).sendMessage("Available networks: "+sb.toString());
        }
        else{
            WiFiSign.plugin.getLogger().info("Available networks: "+sb.toString());
        }
        return true;
    }

}

