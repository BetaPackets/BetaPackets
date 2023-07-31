package de.florianmichael.betapackets.model.block;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return id == block.id && type == block.type && Objects.equals(properties, block.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, properties);
    }
}
