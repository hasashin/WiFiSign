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
            Integer count=0;
            for(int y=0;y<e.getWorld().getMaxHeight();y++){
                for(int x=0;x<16;x++){
                    for(int z=0;z<16;z++){
                        Block block = e.getChunk().getBlock(x, y, z);
                        if(block.getType() == Material.OAK_WALL_SIGN){         
                            if(SignCreateListener.parseSign(block, null, ((Sign)block.getState()).getLines()));
                                count++;
                        }
                    }
                }
            }
            if(count>0)
                WiFiSign.plugin.getLogger().info(count+" signs loaded from chunk");
        }
    }
}