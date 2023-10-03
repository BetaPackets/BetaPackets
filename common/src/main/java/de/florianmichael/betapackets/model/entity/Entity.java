package de.florianmichael.betapackets.model.entity;

import de.florianmichael.betapackets.model.entity.metadata.DataTracker;

import java.util.Map;
import java.util.Objects;

public class Entity {

    private final int entityId;
    private final int globalId;
    private final int livingId;
    private final EntityType type;
    private final DataTracker dataTracker;
    private final Map<EntityPose, EntityDimension> poseToDimension;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return entityId == entity.entityId && globalId == entity.globalId && livingId == entity.livingId && type == entity.type && Objects.equals(dataTracker, entity.dataTracker) && Objects.equals(poseToDimension, entity.poseToDimension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, globalId, livingId, type, dataTracker, poseToDimension);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityId=" + entityId +
                ", globalId=" + globalId +
                ", livingId=" + livingId +
                ", type=" + type +
                ", dataTracker=" + dataTracker +
                ", poseToDimension=" + poseToDimension +
                '}';
    }
}
