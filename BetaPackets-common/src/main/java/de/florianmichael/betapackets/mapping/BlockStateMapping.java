package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.florianmichael.betapackets.model.block.Block;
import de.florianmichael.betapackets.model.block.BlockState;
import de.florianmichael.betapackets.model.block.BlockType;
import de.florianmichael.betapackets.model.block.properties.StateProperty;
import de.florianmichael.betapackets.model.block.shape.VoxelSet;
import de.florianmichael.betapackets.model.block.shape.VoxelShape;
import de.florianmichael.betapackets.model.block.shape.set.BitVoxelSet;
import de.florianmichael.betapackets.model.block.shape.shape.ArrayVoxelShape;
import de.florianmichael.betapackets.model.block.shape.shape.SimpleVoxelShape;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BlockStateMapping {

    private final TIntObjectMap<BlockState> byId;

    BlockStateMapping(int size) {
        byId = new TIntObjectHashMap<>(size);
    }

    private static VoxelShape readShape(JsonObject object) {
        VoxelSet set;
        JsonObject setObject = object.getAsJsonObject("set");

        if (!setObject.get("type").getAsString().equals("bitset"))
            throw new IllegalArgumentException("Unknown set type " + setObject.get("type").getAsString());

        set = new BitVoxelSet(
                setObject.get("xmin").getAsInt(),
                setObject.get("ymin").getAsInt(),
                setObject.get("zmin").getAsInt(),
                setObject.get("xmax").getAsInt(),
                setObject.get("ymax").getAsInt(),
                setObject.get("zmax").getAsInt()
        );

        switch (object.get("type").getAsString()) {

            case "simple":
                return new SimpleVoxelShape(set);

            case "array":
                JsonArray xpoints = object.getAsJsonArray("pointsX");
                JsonArray ypoints = object.getAsJsonArray("pointsY");
                JsonArray zpoints = object.getAsJsonArray("pointsZ");
                double[] pointsX = new double[xpoints.size()];
                for (int i = 0; i < pointsX.length; i++) {
                    pointsX[i] = xpoints.get(i).getAsDouble();
                }
                double[] pointsY = new double[ypoints.size()];
                for (int i = 0; i < pointsY.length; i++) {
                    pointsY[i] = ypoints.get(i).getAsDouble();
                }
                double[] pointsZ = new double[zpoints.size()];
                for (int i = 0; i < pointsZ.length; i++) {
                    pointsZ[i] = zpoints.get(i).getAsDouble();
                }
                return new ArrayVoxelShape(set, pointsX, pointsY, pointsZ);

            default:
                throw new IllegalArgumentException("Unknown shape type " + object.get("type"));
        }
    }

    public static BlockStateMapping loadJson(BlockMapping blockMapping, JsonArray array) {
        BlockStateMapping mapping = new BlockStateMapping(array.size());
        array.forEach(element -> {
            JsonObject blockStateObject = element.getAsJsonObject();
            int id = blockStateObject.get("id").getAsInt();
            BlockType block = BlockType.valueOf(blockStateObject.get("block").getAsString());
            float hardness = blockStateObject.get("hardness").getAsFloat();
            VoxelShape collisionShape = null;
            VoxelShape outlineShape = null;
            if (blockStateObject.has("collision")) {
                collisionShape = readShape(blockStateObject.getAsJsonObject("collision"));
            }
            if (blockStateObject.has("outline")) {
                outlineShape = readShape(blockStateObject.getAsJsonObject("outline"));
            }
            JsonArray propertiesArray = blockStateObject.getAsJsonArray("properties");
            Map<String, Object> properties = new LinkedHashMap<>();
            for (int i = 0; i < propertiesArray.size(); i++) {
                String key = blockMapping.getByType(block).getProperties().get(i);
                properties.put(key, StateProperty.getStateProperty(key).getSerializer().apply(propertiesArray.get(i).getAsString())); // parse logic here
            }
            mapping.add(id, new BlockState(id, block, hardness, properties, collisionShape, outlineShape));
        });
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        List<String> propertyDictionary = new LinkedList<>();
        List<BitVoxelSet> bitVoxelSets = new LinkedList<>();
        List<ArrayVoxelShape> arrayVoxelShapes = new LinkedList<>();
        for (BlockState blockState : byId.valueCollection()) {
            for (Object value : blockState.getProperties().values()) {
                String valueStr = String.valueOf(value);
                if (!propertyDictionary.contains(valueStr))
                    propertyDictionary.add(valueStr);
            }
            if (blockState.getCollisionShape() != null) {
                if (blockState.getCollisionShape().getVoxelSet() instanceof BitVoxelSet bit) {
                    if (!bitVoxelSets.contains(bit))
                        bitVoxelSets.add(bit);
                } else {
                    throw new IllegalArgumentException(blockState.getCollisionShape().getVoxelSet().getClass().getSimpleName());
                }
                if (blockState.getCollisionShape() instanceof ArrayVoxelShape arrayVoxelShape) {
                    if (!arrayVoxelShapes.contains(arrayVoxelShape))
                        arrayVoxelShapes.add(arrayVoxelShape);
                }
            }
            if (blockState.getOutlineShape() != null) {
                if (blockState.getOutlineShape().getVoxelSet() instanceof BitVoxelSet bit) {
                    if (!bitVoxelSets.contains(bit))
                        bitVoxelSets.add(bit);
                } else {
                    throw new IllegalArgumentException(blockState.getOutlineShape().getVoxelSet().getClass().getSimpleName());
                }
                if (blockState.getOutlineShape() instanceof ArrayVoxelShape arrayVoxelShape) {
                    if (!arrayVoxelShapes.contains(arrayVoxelShape))
                        arrayVoxelShapes.add(arrayVoxelShape);
                }
            }
        }
        if (propertyDictionary.size() > 0xFF) {
            throw new IllegalArgumentException("Properties exceed byte-limit.");
        }

        out.writeByte(propertyDictionary.size());
        for (String s : propertyDictionary) {
            byte[] propertyValue = s.getBytes(StandardCharsets.US_ASCII);
            out.writeByte(propertyValue.length);
            out.write(propertyValue);
        }

        out.writeShort(bitVoxelSets.size());
        for (BitVoxelSet bitVoxelSet : bitVoxelSets) {
            out.writeByte(bitVoxelSet.getMinX());
            out.writeByte(bitVoxelSet.getMinY());
            out.writeByte(bitVoxelSet.getMinZ());
            out.writeByte(bitVoxelSet.getMaxX());
            out.writeByte(bitVoxelSet.getMaxY());
            out.writeByte(bitVoxelSet.getMaxZ());
        }

        out.writeShort(arrayVoxelShapes.size());
        for (ArrayVoxelShape arrayVoxelShape : arrayVoxelShapes) {
            out.writeByte(arrayVoxelShape.getxPoints().length);
            for (double v : arrayVoxelShape.getxPoints()) {
                writePoint(v, out);
            }
            out.writeByte(arrayVoxelShape.getyPoints().length);
            for (double v : arrayVoxelShape.getyPoints()) {
                writePoint(v, out);
            }
            out.writeByte(arrayVoxelShape.getzPoints().length);
            for (double v : arrayVoxelShape.getzPoints()) {
                writePoint(v, out);
            }
        }

        out.writeShort(byId.size());
        for (int id : byId.keys()) {
            BlockState state = byId.get(id);
            out.writeShort(id);
            out.writeShort(state.getType().ordinal());
            out.writeFloat(state.getHardness());
            for (Object value : state.getProperties().values()) {
                out.writeByte(propertyDictionary.indexOf(String.valueOf(value)));
            }
            boolean equalShape = Objects.equals(state.getCollisionShape(), state.getOutlineShape());
            out.writeBoolean(equalShape);
            writeShape(arrayVoxelShapes, bitVoxelSets, state.getCollisionShape(), out);
            if (!equalShape) {
                writeShape(arrayVoxelShapes, bitVoxelSets, state.getOutlineShape(), out);
            }
        }
    }

    private void writeShape(List<ArrayVoxelShape> arrayVoxelShapes, List<BitVoxelSet> bitVoxelSets, VoxelShape shape, DataOutputStream out) throws IOException {
        if (shape == null) {
            out.writeByte(0);
        } else {
            if (shape instanceof SimpleVoxelShape) {
                out.writeByte(1);
            } else if (shape instanceof ArrayVoxelShape arrayVoxelShape) {
                out.writeByte(2);
                out.writeShort(arrayVoxelShapes.indexOf(arrayVoxelShape));
            } else {
                throw new IllegalArgumentException(shape.getClass().getSimpleName());
            }
            if (shape.getVoxelSet() instanceof BitVoxelSet bit) {
                out.writeShort(bitVoxelSets.indexOf(bit));
            } else {
                throw new IllegalArgumentException(shape.getVoxelSet().getClass().getSimpleName());
            }
        }
    }

    private void writePoint(double v, DataOutputStream out) throws IOException {
        out.writeDouble(v);
    }

    public static BlockStateMapping read(BlockMapping blockMapping, DataInputStream in) throws IOException {
        String[] propertyDictionary = new String[in.readUnsignedByte()];
        for (int i = 0; i < propertyDictionary.length; i++) {
            byte[] name = new byte[in.readUnsignedByte()];
            in.readFully(name);
            propertyDictionary[i] = new String(name, StandardCharsets.US_ASCII);
        }

        BitVoxelSet[] bitVoxelSets = new BitVoxelSet[in.readUnsignedShort()];
        for (int i = 0; i < bitVoxelSets.length; i++) {
            bitVoxelSets[i] = new BitVoxelSet(in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte());
        }

        double[][][] arrayVoxelShapes = new double[in.readUnsignedShort()][3][];
        for (int i = 0; i < arrayVoxelShapes.length; i++) {
            double[] xpoints = new double[in.readUnsignedByte()];
            for (int j = 0; j < xpoints.length; j++) xpoints[j] = in.readDouble();

            double[] ypoints = new double[in.readUnsignedByte()];
            for (int j = 0; j < ypoints.length; j++) ypoints[j] = in.readDouble();

            double[] zpoints = new double[in.readUnsignedByte()];
            for (int j = 0; j < zpoints.length; j++) zpoints[j] = in.readDouble();

            arrayVoxelShapes[i][0] = xpoints;
            arrayVoxelShapes[i][1] = ypoints;
            arrayVoxelShapes[i][2] = zpoints;
        }

        int size = in.readUnsignedShort();
        BlockStateMapping mapping = new BlockStateMapping(size);
        for (int i = 0; i < size; i++) {
            int id = in.readUnsignedShort();
            BlockType type = BlockType.values()[in.readUnsignedShort()];
            Block block = blockMapping.getByType(type);
            float hardness = in.readFloat();
            Map<String, Object> properties = new LinkedHashMap<>(block.getProperties().size());
            for (String key : block.getProperties()) {
                properties.put(key, StateProperty.getStateProperty(key).getSerializer().apply(propertyDictionary[in.readUnsignedByte()]));
            }
            boolean equalShape = in.readBoolean();
            VoxelShape collisionShape = readShape(arrayVoxelShapes, bitVoxelSets, in);
            VoxelShape outlineShape = equalShape ? collisionShape : readShape(arrayVoxelShapes, bitVoxelSets, in);
            mapping.add(id, new BlockState(id, type, hardness, properties, collisionShape, outlineShape));
        }
        return mapping;
    }

    private static VoxelShape readShape(double[][][] arrayVoxelShapes, BitVoxelSet[] bitVoxelSets, DataInputStream in) throws IOException {
        int type = in.readUnsignedByte();
        if (type == 0) return null;

        int index = type == 2 ? in.readUnsignedShort() : -1;
        BitVoxelSet voxelSet = bitVoxelSets[in.readUnsignedShort()];

        if (type == 1) return new SimpleVoxelShape(voxelSet);
        else if (type == 2) {
            double[][] points = arrayVoxelShapes[index];
            return new ArrayVoxelShape(voxelSet, points[0], points[1], points[2]);
        }
        throw new IllegalArgumentException();
    }

    public void add(int id, BlockState state) {
        byId.put(id, state);
    }

    public BlockState getById(int id) {
        return byId.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockStateMapping that = (BlockStateMapping) o;
        return Objects.equals(byId, that.byId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byId);
    }

    @Override
    public String toString() {
        return "BlockStateMapping{size=" + byId.size() + "}";
    }
}
