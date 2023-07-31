package de.florianmichael.betapackets.model.entity;

import de.florianmichael.betapackets.model.entity.metadata.DataTracker;

import java.util.Map;

public class Entity {

    private int entityId;
    private int globalId;
    private int livingId;
    private EntityType type;
    private DataTracker dataTracker;
    private Map<EntityPose, EntityDimension> poseToDimension;

    public Entity(int entityId, int globalId, int livingId, EntityType type, DataTracker dataTracker, Map<EntityPose, EntityDimension> poseToDimension) {
        this.entityId = entityId;
        this.globalId = globalId;
        this.livingId = livingId;
        this.type = type;
        this.dataTracker = dataTracker;
        this.poseToDimension = poseToDimension;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getGlobalId() {
        return globalId;
    }

    public int getLivingId() {
        return livingId;
    }

    public EntityType getType() {
        return type;
    }

    public DataTracker getDataTracker() {
        return dataTracker;
    }

    public Map<EntityPose, EntityDimension> getPoseToDimension() {
        return poseToDimension;
    }
}
