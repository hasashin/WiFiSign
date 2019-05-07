package pl.poznan.put;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import pl.poznan.put.Peer.OperationMode;

public class SignCreateListener implements Listener {
    
    @EventHandler
    public static void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        parseSign(e.getBlock(), p, e.getLines());
        
    }
    
    public static void parseSign(Block signBlock, Player player,String[] lines){
        if(lines[0].equalsIgnoreCase("[wifi]")){
            if(lines[2].matches("(IN|OUT|IO)?")){
                if(!lines[1].isEmpty()){
                    Peer.OperationMode opmode;
                    ArrayList<Peer> list = WiFiSign.Connections.get(lines[1]);
                    switch(lines[2]){
                        case "IN":
                        opmode = OperationMode.IN;
                        break;
                        case "OUT":
                        opmode = OperationMode.OUT;
                        break;
                        case "IO":
                        default:
                        opmode = OperationMode.IO;
                        break;
                    }
                    if(list != null){
                        boolean blockExists = false;
                        for(Object obj : list.toArray()){
                            Peer peer = (Peer)obj;
                            if(peer.signBlock.equals(signBlock)){
                                blockExists = true;
                                return;
                            }
                        }
                        if(!blockExists){
                            list.add(new Peer(signBlock,opmode));
                            signBlock.getChunk().setForceLoaded(true);
                            if(player != null) player.sendMessage("Dodano element do sieci "+lines[1]);
                            else WiFiSign.plugin.getLogger().info("Dodano element do sieci "+lines[1]);
                        }
                    }
                    else{
                        list = new ArrayList<>();
                        list.add(new Peer(signBlock, opmode));
                        WiFiSign.Connections.put(lines[1],list);
                        signBlock.getChunk().setForceLoaded(true);
                        if(player != null) player.sendMessage("Stworzono nową sieć "+lines[1]);
                        else WiFiSign.plugin.getLogger().info("Stworzono nową sieć "+lines[1]);
                    }
                    
                }
            }
        }
    }
}