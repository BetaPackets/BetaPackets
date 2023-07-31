package de.florianmichael.betapackets.model.item;

import de.florianmichael.betapackets.model.block.Block;
import de.florianmichael.betapackets.model.block.BlockType;

public class Item {

    private int id;
    private ItemType type;
    private int maxStackSize;
    private Block blockType;
    private boolean edible;

    public Item(int id, ItemType type, int maxStackSize, Block blockType, boolean edible) {
        this.id = id;
        this.type = type;
        this.maxStackSize = maxStackSize;
        this.blockType = blockType;
        this.edible = edible;
    }

    public int getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public Block getBlockType() {
        return blockType;
    }

    public boolean isEdible() {
        return edible;
    }
}
