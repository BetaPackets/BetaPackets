package de.florianmichael.betapackets.model.entity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class EntityMapping {

    private final Map<EntityType, Entity> typeToEntity = new HashMap<>();
    private Entity[] byId;

    public void createIDMap() {
        byId = new Entity[typeToEntity.size()];
        for (Entity entity : typeToEntity.values()) {
            byId[entity.getId()] = entity;
        }
    }

    public void addMapping(Entity entity) {
        typeToEntity.put(entity.getType(), entity);
    }

    public Entity getById(int id) {
        return byId[id];
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        data.writeByte(typeToEntity.size());
        for (Entity value : typeToEntity.values()) {
            data.writeByte(value.getType().ordinal());
            value.getDataTracker().write(data);
        }
    }
}
