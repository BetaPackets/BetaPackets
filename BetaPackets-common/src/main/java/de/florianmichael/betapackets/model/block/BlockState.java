package de.florianmichael.betapackets.model.block;

import de.florianmichael.betapackets.model.position.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockState {

    public static BlockState AIR = new BlockState(0, BlockType.AIR, 0, new HashMap<>(), null);

    private int id;
    private BlockType type;
    private float hardness;
    private Map<String, Object> properties;
    private List<AABB> collisionShape;

    public BlockState(int id, BlockType type, float hardness, Map<String, Object> properties, List<AABB> collisionShape) {
        this.id = id;
        this.type = type;
        this.hardness = hardness;
        this.properties = properties;
        this.collisionShape = collisionShape;
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

    public List<AABB> getCollisionShape() {
        return collisionShape;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
