package de.florianmichael.betapackets.model.block;

import de.florianmichael.betapackets.model.block.properties.StateProperty;
import de.florianmichael.betapackets.model.position.AABB;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BlockMapping {

    private final Map<BlockType, Block> typeToInstance = new HashMap<>();
    private final Map<Integer, Block> idToBlock = new HashMap<>();

    private final List<BlockState> blockStates = new ArrayList<>();
    private final Map<Integer, BlockState> blockStateById = new HashMap<>();

    public void addBlock(Block block) {
        typeToInstance.put(block.getType(), block);
        idToBlock.put(block.getId(), block);
    }

    public void addBlockState(BlockState state) {
        blockStates.add(state);
        blockStateById.put(state.getId(), state);
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        Collection<Block> values = typeToInstance.values();

        data.writeShort(values.size());
        for (Block value : values) {
            int idMask = (value.getId() & 0b00000000_00000000_00000011_11111111) << 10;
            idMask |= value.getType().ordinal() & 0b00000000_00000000_00000011_11111111;
            data.writeByte(idMask & 0b00000000_00000000_00000000_11111111);
            data.writeByte((idMask >> 8) & 0b00000000_00000000_00000000_11111111);
            data.writeByte((idMask >> 16) & 0b00000000_00000000_00000000_11111111);

            data.writeByte(value.getProperties().size());
            for (String property : value.getProperties()) {
                byte[] keyName = property.getBytes(StandardCharsets.US_ASCII);
                data.writeByte(keyName.length);
                data.write(keyName);
            }
        }

        data.writeShort(blockStates.size());
        for (BlockState state : blockStates) {
            data.writeShort(state.getId());
            data.writeShort(state.getType().ordinal());

            Set<Map.Entry<String, Object>> entries = state.getProperties().entrySet();
            float hardness = state.getHardness();
            short compressedHardness = hardness == -1 ? 1023 : (short) (hardness * 20);

            short mask = (short) ((compressedHardness & 0b00000011_11111111) << 6);
            mask |= (short) ((entries.size() & 0b00000000_00111111));

            data.writeShort(mask);
            for (Map.Entry<String, Object> entry : entries) {
                byte[] value = ((StateProperty<Object>) StateProperty.getStateProperty(entry.getKey())).getDeserializer().apply(entry.getValue()).getBytes(StandardCharsets.US_ASCII);
                data.writeByte(value.length);
                data.write(value);
            }
            List<AABB> collisionShape = state.getCollisionShape();
            if (collisionShape == null || collisionShape.isEmpty())
                data.writeByte(0);
            else {
                data.writeByte(collisionShape.size());
                for (AABB aabb : collisionShape) {
                    data.writeByte((int) (aabb.getMinX() * 32));
                    data.writeByte((int) (aabb.getMinY() * 32));
                    data.writeByte((int) (aabb.getMinZ() * 32));
                    data.writeByte((int) (aabb.getMaxX() * 32));
                    data.writeByte((int) (aabb.getMaxY() * 32));
                    data.writeByte((int) (aabb.getMaxZ() * 32));
                }
            }
        }
    }

    public BlockState getStateById(int id) {
        BlockState state = blockStateById.get(id);
        if (state == null) return BlockState.AIR;
        return state;
    }

    public Block getBlockById(int id) {
        Block block = idToBlock.get(id);
        if (block == null) return Block.AIR;
        return block;
    }
}
