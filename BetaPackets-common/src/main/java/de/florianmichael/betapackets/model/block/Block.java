package de.florianmichael.betapackets.model.block;

import java.util.Collections;
import java.util.List;

public class Block {

    public static final Block AIR = new Block(0, BlockType.AIR, Collections.EMPTY_LIST);

    private int id;
    private BlockType type;
    private List<String> properties;

    public Block(int id, BlockType type, List<String> properties) {
        this.id = id;
        this.type = type;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public List<String> getProperties() {
        return properties;
    }

    public BlockType getType() {
        return type;
    }
}
