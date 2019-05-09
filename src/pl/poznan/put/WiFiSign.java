package pl.poznan.put;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class WiFiSign extends JavaPlugin {
    
    protected static final String Lever = null;
    
    static Plugin plugin;
    
    static Hashtable<String, ArrayList<Peer>> Connections;
    
    @Override
    public void onEnable() {
        Connections = new Hashtable<>();
        plugin = this;
        registerListeners();
        Bukkit.getPluginCommand("wifireload").setExecutor(new CommandReload());
        Bukkit.getPluginCommand("wifinets").setExecutor(new CommandList());
        Bukkit.getPluginCommand("wifinet").setExecutor(new CommandNet());

        runCheck();
        Integer signCount = 0;
        for(World world : Bukkit.getWorlds()){
            for(Chunk chunk : world.getLoadedChunks()){
                signCount = checkChunk(chunk,signCount);
            }
        }
        if(signCount>0){
            plugin.getLogger().info(signCount+" signs loaded");
        }
        
        plugin.getLogger().log(Level.INFO, "WiFiSign Loaded Sucessfully!");
    }
    
    @Override
    public void onDisable() {
    }
    
    private void registerListeners() {
        
        Bukkit.getPluginManager().registerEvents(new SignCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignRemoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoadListener(), this);
    }
    
    private void runCheck(){
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(String name : WiFiSign.Connections.keySet()){
                    boolean powered=false;
                    ArrayList<Peer> list = WiFiSign.Connections.get(name);
                    for(Peer peer : list){
                        if(peer.mode != Peer.OperationMode.OUT){
                            if(peer.signBlock.isBlockPowered())
                            powered = true;
                        }
                    }
                    for(Peer peer : list){
                        peer.powered = powered;
                        if(peer.mode != Peer.OperationMode.IN){
                            BlockFace dir = BlockFace.SELF;
                            if(peer.signBlock.getBlockData() instanceof WallSign){
                                Directional sign = (WallSign) peer.signBlock.getBlockData();
                                dir = sign.getFacing().getOppositeFace();
                            }
                            else{
                                continue;
                            }
                            BlockState leverBlock = peer.signBlock.getRelative(dir).getRelative(BlockFace.UP).getState();
                            
                            if(leverBlock.getBlock().getBlockData() instanceof Switch) {
                                Switch lever = (Switch)leverBlock.getBlock().getBlockData();
                                lever.setPowered(peer.powered);
                                leverBlock.setBlockData(lever);
                                leverBlock.update();
                            }
                            else
                                continue;
                            
                        }
                    }
                }
            }
        }, 0,1);
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