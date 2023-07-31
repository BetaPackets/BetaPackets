package de.florianmichael.betapackets.model.block;

import de.florianmichael.betapackets.model.block.shape.VoxelShape;
import de.florianmichael.betapackets.model.position.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BlockState {

    public static BlockState AIR = new BlockState(0, BlockType.AIR, 0, new HashMap<>(), null, null);

    private int id;
    private BlockType type;
    private float hardness;
    private Map<String, Object> properties;
    private VoxelShape collisionShape;
    private VoxelShape outlineShape;

    public BlockState(int id, BlockType type, float hardness, Map<String, Object> properties, VoxelShape collisionShape, VoxelShape outlineShape) {
        this.id = id;
        this.type = type;
        this.hardness = hardness;
        this.properties = properties;
        this.collisionShape = collisionShape;
        this.outlineShape = outlineShape;
    }

    public float getHardness() {
        return hardness;
    }

    public int getId() {
        return id;
    }

    public BlockType getType() {
        return type;
    }

    public VoxelShape getCollisionShape() {
        return collisionShape;
    }

    public VoxelShape getOutlineShape() {
        return outlineShape;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockState that = (BlockState) o;
        return id == that.id && Float.compare(that.hardness, hardness) == 0 && type == that.type && Objects.equals(properties, that.properties) && Objects.equals(collisionShape, that.collisionShape) && Objects.equals(outlineShape, that.outlineShape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, hardness, properties, collisionShape, outlineShape);
    }
}