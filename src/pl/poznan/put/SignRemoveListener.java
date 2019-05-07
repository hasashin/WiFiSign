package pl.poznan.put;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

class SignRemoveListener implements Listener{
    
    @EventHandler
    public static void onSignBreak(BlockBreakEvent e) {
        if(e.getBlock().getType() == Material.OAK_WALL_SIGN){
            for(String name : WiFiSign.Connections.keySet()){
                for(Object obj : WiFiSign.Connections.get(name).toArray()){
                    Peer peer = (Peer)obj;
                    boolean isChunkEmpty = true;
                    for(Object block : WiFiSign.Connections.get(name).toArray()){
                        Peer per = (Peer) block;
                        if (!per.signBlock.equals(e.getBlock()) && per.signBlock.getChunk().equals(e.getBlock().getChunk())){
                            isChunkEmpty = false;
                            return;
                        }
                    }
                    if(peer.signBlock.equals(e.getBlock())){
                        WiFiSign.Connections.get(name).remove(peer);
                        if(isChunkEmpty) e.getBlock().getChunk().setForceLoaded(false);
                    }
                }
                if(WiFiSign.Connections.get(name).size() == 0){
                    WiFiSign.Connections.remove(name);
                    e.getPlayer().sendMessage("Usunięto sieć "+name);
                }
                else
                e.getPlayer().sendMessage("Usunięto element sieci "+ name);
            }
        }
    }
}