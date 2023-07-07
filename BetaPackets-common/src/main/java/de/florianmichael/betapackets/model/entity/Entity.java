package de.florianmichael.betapackets.model.entity;

import de.florianmichael.betapackets.model.entity.metadata.DataTracker;

public class Entity {

    private int id;
    private EntityType type;
    private DataTracker dataTracker;

    public Entity(int id, EntityType type, DataTracker dataTracker) {
        this.id = id;
        this.type = type;
        this.dataTracker = dataTracker;
    }

    public int getId() {
        return id;
    }

    public EntityType getType() {
        return type;
    }

    public DataTracker getDataTracker() {
        return dataTracker;
    }
}
