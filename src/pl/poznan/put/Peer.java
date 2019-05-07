package pl.poznan.put;

import org.bukkit.block.Block;

public class Peer{
    enum OperationMode{
        IN,
        OUT,
        IO
    }
    Block signBlock;
    OperationMode mode;
    boolean powered;

    public Peer(Block signBlock, OperationMode mode){
        this.signBlock = signBlock;
        this.mode = mode;
        this.powered = false;
    }
}