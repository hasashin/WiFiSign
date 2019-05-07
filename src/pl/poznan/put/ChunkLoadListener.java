package pl.poznan.put;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {
    
    @EventHandler
    public static void onChunkLoad(ChunkLoadEvent e) {
        if(!e.isNewChunk()){
            int x = 0;
            int z = 0;
            for(int y=0;y<e.getWorld().getMaxHeight();y++){
                x=0;
                for(;x<16;x++){
                    z=0;
                    for(;z<16;z++){
                        Block block = e.getChunk().getBlock(x, y, z);
                        if(block.getType() == Material.OAK_WALL_SIGN){
                            WiFiSign.plugin.getLogger().info("Znaleziono znak wifi");
                            SignCreateListener.parseSign(block, null, ((Sign)block.getState()).getLines());
                        }
                    }
                }
            }
        }
    }
}