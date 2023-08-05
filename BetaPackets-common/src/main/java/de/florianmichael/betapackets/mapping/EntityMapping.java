package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.florianmichael.betapackets.model.entity.Entity;
import de.florianmichael.betapackets.model.entity.EntityDimension;
import de.florianmichael.betapackets.model.entity.EntityPose;
import de.florianmichael.betapackets.model.entity.EntityType;
import de.florianmichael.betapackets.model.entity.metadata.DataTracker;
import de.florianmichael.betapackets.model.entity.metadata.Metadata;
import de.florianmichael.betapackets.model.entity.metadata.MetadataCodecType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class EntityMapping {

    private TIntObjectMap<Entity> byEntityId = new TIntObjectHashMap<>();
    private TIntObjectMap<Entity> byGlobalId = new TIntObjectHashMap<>(1);
    private TIntObjectMap<Entity> byLivingId = new TIntObjectHashMap<>();

    private Map<EntityType, Entity> byType;

    EntityMapping(int size) {
        byType = new HashMap<>(size);
    }

    public static EntityMapping loadJson(MetadataCodecMapping metadataCodecMapping, JsonObject object) {
        JsonArray array = object.getAsJsonArray("array");
        EntityMapping mapping = new EntityMapping(array.size());
        if (!object.get("entityId").getAsBoolean()) mapping.byEntityId = null;
        if (!object.get("globalId").getAsBoolean()) mapping.byGlobalId = null;
        if (!object.get("livingId").getAsBoolean()) mapping.byLivingId = null;

        array.forEach(element -> {
            JsonObject entityObj = element.getAsJsonObject();
            EntityType type = EntityType.valueOf(entityObj.get("type").getAsString());

            Map<EntityPose, EntityDimension> poseToDimension = new LinkedHashMap<>();
            entityObj.getAsJsonArray("pose").forEach(poseElement -> {
                JsonObject poseObj = poseElement.getAsJsonObject();
                EntityPose pose = EntityPose.valueOf(poseObj.get("type").getAsString());
                float width = poseObj.get("width").getAsFloat();
                float height = poseObj.get("height").getAsFloat();
                float eyeHeight = poseObj.get("eye_height").getAsFloat();
                boolean fixed = poseObj.get("fixed").getAsBoolean();
                poseToDimension.put(pose, new EntityDimension(width, height, eyeHeight, fixed));
            });

            DataTracker dataTracker = new DataTracker();
            entityObj.getAsJsonArray("metadata").forEach(metadataElement -> {
                JsonObject metadataObject = metadataElement.getAsJsonObject();
                String name = metadataObject.get("name").getAsString();
                int index = metadataObject.get("index").getAsInt();
                MetadataCodecType codecType = metadataCodecMapping.getCodecType(metadataObject.get("type").getAsInt());
                dataTracker.register(new Metadata(index, name, codecType));
            });

            int entityId = -1;
            int globalId = -1;
            int livingId = -1;
            if (object.get("entityId").getAsBoolean()) entityId = entityObj.get("entityId").getAsInt();
            if (object.get("globalId").getAsBoolean()) globalId = entityObj.get("globalId").getAsInt();
            if (object.get("livingId").getAsBoolean()) livingId = entityObj.get("livingId").getAsInt();

            mapping.add(new Entity(entityId, globalId, livingId, type, dataTracker, poseToDimension));
        });

        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        byte entityIdFlags = 0; // 00000EGL
        if (byEntityId != null) entityIdFlags |= 1 << 2;
        if (byGlobalId != null) entityIdFlags |= 1 << 1;
        if (byLivingId != null) entityIdFlags |= 1;
        out.writeByte(entityIdFlags);

        List<Metadata> metadataDictionary = new ArrayList<>();
        List<Float> entityAxes = new ArrayList<>();
        List<EntityDimension> entityDimensionDictionary = new ArrayList<>();
        List<EntityPose> poseOrder = new LinkedList<>();

        for (Entity entity : byType.values()) {
            for (Metadata metadata : entity.getDataTracker().getMetadata()) {
                if (!metadataDictionary.contains(metadata))
                    metadataDictionary.add(metadata);
            }

            for (EntityDimension dimension : entity.getPoseToDimension().values()) {
                if (!entityAxes.contains(dimension.getWidth()))
                    entityAxes.add(dimension.getWidth());
                if (!entityAxes.contains(dimension.getHeight()))
                    entityAxes.add(dimension.getHeight());
                if (!entityDimensionDictionary.contains(dimension))
                    entityDimensionDictionary.add(dimension);
            }

            if (poseOrder.isEmpty()) {
                poseOrder.addAll(entity.getPoseToDimension().keySet());
            }
        }

        if (metadataDictionary.size() > 0xFF) throw new IllegalArgumentException("too big name dictionary");
        out.writeByte(metadataDictionary.size());
        for (Metadata metadata : metadataDictionary) {
            out.writeUTF(metadata.getName());
            out.writeByte(metadata.getIndex());
            out.writeByte(metadata.getCodecType().ordinal());
        }

        if (entityAxes.size() > 0xFF) throw new IllegalArgumentException("too big entity axis dictionary");
        out.writeByte(entityAxes.size());
        for (Float length : entityAxes) {
            out.writeFloat(length);
        }

        if (entityDimensionDictionary.size() > 0xFF) throw new IllegalArgumentException("too big dimension dictionary");
        out.writeByte(entityDimensionDictionary.size());
        for (EntityDimension entityDimension : entityDimensionDictionary) {
            out.writeByte(entityAxes.indexOf(entityDimension.getWidth()));
            out.writeByte(entityAxes.indexOf(entityDimension.getHeight()));
            out.writeFloat(entityDimension.getEyeHeight());
            out.writeBoolean(entityDimension.isFixed());
        }

        if (poseOrder.size() > 0xFF) throw new IllegalArgumentException("too many poses");
        out.writeByte(poseOrder.size());
        for (EntityPose entityPose : poseOrder) {
            out.writeByte(entityPose.ordinal());
        }

        out.writeByte(byType.size());
        for (Entity entity : byType.values()) {
            out.writeByte(entity.getType().ordinal());
            if (entity.getType() != EntityType.PLAYER) {
                if (byEntityId != null) out.writeByte(entity.getEntityId());
                if (byLivingId != null) out.writeByte(entity.getLivingId());
                if (byGlobalId != null) out.writeByte(entity.getGlobalId());
            }
            for (EntityDimension entityDimension : entity.getPoseToDimension().values()) {
                out.writeByte(entityDimensionDictionary.indexOf(entityDimension));
            }
            Collection<Metadata> metadataCollection = entity.getDataTracker().getMetadata();
            out.writeByte(metadataCollection.size());
            for (Metadata metadata : metadataCollection) {
                out.writeByte(metadataDictionary.indexOf(metadata));
            }
        }
    }

    public static EntityMapping read(DataInputStream in) throws IOException {
        byte entityIdFlags = in.readByte(); // 00000EGL
        boolean entityIds = (entityIdFlags >> 2 & 0b1) == 1;
        boolean globalIds = (entityIdFlags >> 1 & 0b1) == 1;
        boolean livingIds = (entityIdFlags >> 0 & 0b1) == 1;


        Metadata[] metadataDictionary = new Metadata[in.readUnsignedByte()];
        for (int i = 0; i < metadataDictionary.length; i++) {
            String name = in.readUTF();
            int index = in.readUnsignedByte();
            MetadataCodecType codecType = MetadataCodecType.values()[in.readUnsignedByte()];
            metadataDictionary[i] = new Metadata(index, name, codecType);
        }

        float[] entityAxes = new float[in.readUnsignedByte()];
        for (int i = 0; i < entityAxes.length; i++) {
            entityAxes[i] = in.readFloat();
        }

        EntityDimension[] entityDimensionDictionary = new EntityDimension[in.readUnsignedByte()];
        for (int i = 0; i < entityDimensionDictionary.length; i++) {
            float width = entityAxes[in.readUnsignedByte()];
            float height = entityAxes[in.readUnsignedByte()];
            float eyeHeight = in.readFloat();
            boolean fixed = in.readBoolean();
            entityDimensionDictionary[i] = new EntityDimension(width, height, eyeHeight, fixed);
        }

        EntityPose[] poseOrder = new EntityPose[in.readUnsignedByte()];
        for (int i = 0; i < poseOrder.length; i++) {
            poseOrder[i] = EntityPose.values()[in.readUnsignedByte()];
        }

        int size = in.readUnsignedByte();
        EntityMapping mapping = new EntityMapping(size);
        if (!entityIds) mapping.byEntityId = null;
        if (!globalIds) mapping.byGlobalId = null;
        if (!livingIds) mapping.byLivingId = null;
        for (int i = 0; i < size; i++) {
            EntityType type = EntityType.values()[in.readUnsignedByte()];
            int entityId = -1;
            int globalId = -1;
            int livingId = -1;
            if (type != EntityType.PLAYER) {
                entityId = entityIds ? in.readUnsignedByte() : -1;
                globalId = globalIds ? in.readUnsignedByte() : -1;
                livingId = livingIds ? in.readUnsignedByte() : -1;
            }
            Map<EntityPose, EntityDimension> poseToDimension = new LinkedHashMap<>();
            for (EntityPose pose : poseOrder) {
                poseToDimension.put(pose, entityDimensionDictionary[in.readUnsignedByte()]);
            }

            DataTracker dataTracker = new DataTracker();
            int metadataCount = in.readUnsignedByte();
            for (int j = 0; j < metadataCount; j++) {
                dataTracker.register(metadataDictionary[in.readUnsignedByte()]);
            }

            mapping.add(new Entity(entityId, globalId, livingId, type, dataTracker, poseToDimension));
        }
        return mapping;
    }

    public void add(Entity entity) {
        if (byEntityId != null && entity.getEntityId() != -1) byEntityId.put(entity.getEntityId(), entity);
        if (byGlobalId != null && entity.getGlobalId() != -1) byGlobalId.put(entity.getGlobalId(), entity);
        if (byLivingId != null && entity.getLivingId() != -1) byLivingId.put(entity.getLivingId(), entity);
        byType.put(entity.getType(), entity);
    }

    public Entity getByEntityId(int id) {
        return byEntityId.get(id);
    }

    public Entity getByType(EntityType type) {
        return byType.get(type);
    }

    public Map<EntityType, Entity> getByType() {
        return byType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMapping that = (EntityMapping) o;
        return Objects.equals(byType, that.byType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byEntityId, byGlobalId, byLivingId, byType);
    }

    @Override
    public String toString() {
        return "EntityMapping{" +
                "byEntityId=" + byEntityId.size() +
                ", byType=" + byType.size() +
                '}';
    }
}
