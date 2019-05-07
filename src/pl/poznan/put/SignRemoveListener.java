package pl.poznan.put;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

class SignRemoveListener implements Listener{
    
    @EventHandler
    public static void onSignBreak(BlockBreakEvent e) {
        if(e.getBlock().getType() == Material.OAK_WALL_SIGN){
            destroySign(e.getBlock(),e.getPlayer());
        }
        else{
            BlockFace[] faces = {BlockFace.NORTH,BlockFace.WEST,BlockFace.EAST,BlockFace.SOUTH};
            for(BlockFace face : faces){
                if(e.getBlock().getRelative(face).getType() == Material.OAK_WALL_SIGN){
                    destroySign(e.getBlock().getRelative(face),e.getPlayer());
                }
            }
        }
        cleanup();
    }
    
    private static void destroySign(Block sign,Player player){
        System.out.println("DUP");
        for(Object nameObj : WiFiSign.Connections.keySet().toArray()){
            boolean deletedSomething = false;
            String name = (String) nameObj;
            for(Object obj : WiFiSign.Connections.get(name).toArray()){
                Peer peer = (Peer)obj;
                boolean isChunkEmpty = true;
                for(Object block : WiFiSign.Connections.get(name).toArray()){
                    Peer per = (Peer) block;
                    if (!per.signBlock.equals(sign) && per.signBlock.getChunk().equals(sign.getChunk())){
                        isChunkEmpty = false;
                        break;
                    }
                }
                if(peer.signBlock.equals(sign)){
                    WiFiSign.Connections.get(name).remove(peer);
                    if(isChunkEmpty) sign.getChunk().setForceLoaded(false);
                    deletedSomething = true;
                }
            }
            if(deletedSomething){
                if(WiFiSign.Connections.get(name).size() == 0){
                    WiFiSign.Connections.remove(name);
                    if(player != null) player.sendMessage("Usunięto sieć "+name);
                    else WiFiSign.plugin.getLogger().info("Usunięto sieć "+name);
                }
                else{
                    if(player != null) player.sendMessage("Usunięto element sieci "+ name);
                    else WiFiSign.plugin.getLogger().info("Usunięto element sieci "+ name);
                }
            }
        }
    }
    
    private static void cleanup(){
        for(Object nameObj : WiFiSign.Connections.keySet().toArray()){
            String name = (String)nameObj;
            for(Object peerObj : WiFiSign.Connections.get(name).toArray()){
                Peer peer = (Peer)peerObj;
                if(peer.signBlock.getLocation() == null){
                    WiFiSign.Connections.get(name).remove(peer);
                }
            }
        }
    }
}