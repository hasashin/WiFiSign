package pl.poznan.put;

import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int signCount=0;
        WiFiSign.Connections = new Hashtable<>();
        for(World world : Bukkit.getWorlds()){
            for(Chunk chunk : world.getLoadedChunks()){
                signCount = checkChunk(chunk,signCount);
            }
        }
        if(signCount>0){
            if(sender instanceof Player){
                ((Player)sender).sendMessage("[WiFiSign] "+signCount + " signs reloaded");
            }
            else{
                WiFiSign.plugin.getLogger().info(signCount + " signs reloaded");
            }
        }
        return true;
    }
    
    private int checkChunk(Chunk chunk,Integer count){
        for(int y=0;y<chunk.getWorld().getMaxHeight();y++){
            for(int x=0;x<16;x++){
                for(int z=0;z<16;z++){
                    Block block = chunk.getBlock(x, y, z);
                    if(block.getType() == Material.OAK_WALL_SIGN){
                        if(SignCreateListener.parseSign(block, null, ((Sign)block.getState()).getLines()))
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    
}

