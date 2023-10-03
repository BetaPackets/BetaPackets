package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.florianmichael.betapackets.model.block.Block;
import de.florianmichael.betapackets.model.block.BlockType;
import de.florianmichael.betapackets.model.item.Item;
import de.florianmichael.betapackets.model.item.ItemType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ItemMapping {

    private final TIntObjectMap<Item> byId;

    ItemMapping(int size) {
        byId = new TIntObjectHashMap<>(size);
    }

    public static ItemMapping loadJson(BlockMapping blockMapping, JsonArray array) {
        ItemMapping mapping = new ItemMapping(array.size());
        array.forEach(element -> {
            JsonObject itemObject = element.getAsJsonObject();
            int id = itemObject.get("id").getAsInt();
            ItemType type = ItemType.valueOf(itemObject.get("name").getAsString());
            int maxStackSize = itemObject.get("max_stack_size").getAsInt();
            boolean edible = itemObject.get("edible").getAsBoolean();

            Block blockType = null;
            if (itemObject.has("block")) blockType = blockMapping.getById(id);

            mapping.add(id, new Item(id, type, maxStackSize, blockType, edible));
        });
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeShort(byId.size());
        for (Item item : byId.valueCollection()) {
            out.writeShort(item.getId());
            out.writeByte(item.getMaxStackSize());
            out.writeShort(item.getType().ordinal());
            out.writeBoolean(item.isEdible());
            if (item.getBlockType() != null) {
                out.writeBoolean(true);
                out.writeShort(item.getBlockType().getType().ordinal());
            } else {
                out.writeBoolean(false);
            }
        }
    }

    public static ItemMapping read(BlockMapping blockMapping, DataInputStream in) throws IOException {
        int size = in.readUnsignedShort();
        ItemMapping mapping = new ItemMapping(size);
        for (int i = 0; i < size; i++) {
            int id = in.readUnsignedShort();
            int maxStackSize = in.readUnsignedByte();
            ItemType type = ItemType.values()[in.readUnsignedShort()];
            boolean edible = in.readBoolean();
            BlockType blockType = null;
            if (in.readBoolean()) {
                blockType = BlockType.values()[in.readUnsignedShort()];
            }
            mapping.add(id, new Item(id, type, maxStackSize, blockMapping.getByType(blockType), edible));
        }
        return mapping;
    }

    public void add(int id, Item item) {
        byId.put(id, item);
    }

    public Item getById(int id) {
        return byId.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemMapping that = (ItemMapping) o;
        return Objects.equals(byId, that.byId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byId);
    }
}
