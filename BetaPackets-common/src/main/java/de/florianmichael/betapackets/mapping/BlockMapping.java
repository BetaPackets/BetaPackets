package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.florianmichael.betapackets.model.block.Block;
import de.florianmichael.betapackets.model.block.BlockType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BlockMapping {

    private final TIntObjectMap<Block> byId;
    private final Map<BlockType, Block> byType;

    BlockMapping(int size) {
        byId = new TIntObjectHashMap<>(size);
        byType = new HashMap<>(size);
    }

    public static BlockMapping loadJson(JsonArray array) {
        BlockMapping mapping = new BlockMapping(array.size());
        array.forEach(element -> {
            JsonObject blockObject = element.getAsJsonObject();
            int id = blockObject.get("id").getAsInt();
            BlockType type = BlockType.valueOf(blockObject.get("name").getAsString());
            List<String> properties = new LinkedList<>();
            blockObject.getAsJsonArray("properties").forEach(property -> {
                properties.add(property.getAsString());
            });
            mapping.add(id, new Block(id, type, properties));
        });
        return mapping;
    }

    public static BlockMapping read(DataInputStream in) throws IOException {
        String[] dictionary = new String[in.readUnsignedByte()];
        for (int i = 0; i < dictionary.length; i++) {
            byte[] name = new byte[in.readUnsignedByte()];
            in.readFully(name);
            dictionary[i] = new String(name, StandardCharsets.US_ASCII);
        }

        int blocks = in.readUnsignedShort();
        BlockMapping mapping = new BlockMapping(blocks);
        for (int i = 0; i < blocks; i++) {
            int id = in.readUnsignedShort();
            BlockType type = BlockType.values()[in.readUnsignedShort()];
            List<String> properties = new LinkedList<>();
            int propertyCount = in.readUnsignedByte();
            for (int j = 0; j < propertyCount; j++) {
                properties.add(dictionary[in.readUnsignedByte()]);
            }
            mapping.add(id, new Block(id, type, properties));
        }
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        List<String> dictionary = new LinkedList<>();
        for (Block block : byType.values()) {
            block.getProperties().forEach(s -> {
                if (!dictionary.contains(s))
                    dictionary.add(s);
            });
        }
        if (dictionary.size() > 0xFF) {
            throw new IllegalArgumentException("Dictionary size exceeds byte-limit");
        }
        out.writeByte(dictionary.size());
        for (String str : dictionary) {
            byte[] name = str.getBytes(StandardCharsets.US_ASCII);
            out.writeByte(name.length);
            out.write(name);
        }

        out.writeShort(byType.size());
        for (Block value : byType.values()) {
            out.writeShort(value.getId());
            out.writeShort(value.getType().ordinal());
            out.writeByte(value.getProperties().size());
            for (String property : value.getProperties()) {
                out.writeByte(dictionary.indexOf(property));
            }
        }
    }

    public void add(int id, Block block) {
        byId.put(id, block);
        byType.put(block.getType(), block);
    }

    public Block getById(int id) {
        return byId.get(id);
    }

    public Block getByType(BlockType type) {
        return byType.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockMapping that = (BlockMapping) o;
        return Objects.equals(byId, that.byId) && Objects.equals(byType, that.byType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byId, byType);
    }

    @Override
    public String toString() {
        return "BlockMapping{" +
                "size=" + byId.size() +
                '}';
    }
}
