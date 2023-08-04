package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.florianmichael.betapackets.model.particle.Particle;
import de.florianmichael.betapackets.model.particle.ParticleParameterType;
import de.florianmichael.betapackets.model.particle.ParticleType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ParticleMapping {

    private final TIntObjectMap<Particle> byId;
    private final Map<ParticleType, Particle> byType;

    public static ParticleMapping loadJson(JsonArray array) {
        ParticleMapping mapping = new ParticleMapping(array.size());
        array.forEach(element -> {
            JsonObject particleObject = element.getAsJsonObject();

            int id = particleObject.get("id").getAsInt();
            ParticleType type = ParticleType.valueOf(particleObject.get("name").getAsString());
            Map<String, ParticleParameterType> parameters = new LinkedHashMap<>();
            particleObject.getAsJsonArray("parameters").forEach(parameterElement -> {
                JsonObject parameterObject = parameterElement.getAsJsonObject();
                parameters.put(parameterObject.get("name").getAsString(), ParticleParameterType.valueOf(parameterObject.get("type").getAsString()));
            });

            mapping.add(id, new Particle(id, type, parameters));
        });
        return mapping;
    }

    public static ParticleMapping read(DataInputStream in) throws IOException {
        int length = in.readUnsignedByte();
        ParticleMapping mapping = new ParticleMapping(length);
        for (int i = 0; i < length; i++) {
            int id = in.readUnsignedByte();
            ParticleType type = ParticleType.values()[in.readUnsignedByte()];
            int parameterCount = in.readUnsignedByte();
            Map<String, ParticleParameterType> parameters = new LinkedHashMap<>(parameterCount);
            for (int j = 0; j < parameterCount; j++) {
                byte[] keyBytes = new byte[in.readUnsignedByte()];
                in.readFully(keyBytes);
                parameters.put(new String(keyBytes, StandardCharsets.US_ASCII), ParticleParameterType.values()[in.readUnsignedByte()]);
            }
            mapping.add(id, new Particle(id, type, parameters));
        }
        return mapping;
    }

    ParticleMapping(int size) {
        byId = new TIntObjectHashMap<>(size);
        byType = new HashMap<>(size);
    }

    public void write(DataOutputStream out) throws IOException{
        out.writeByte(byType.size());
        for (Particle value : byType.values()) {
            out.writeByte(value.getId());
            out.writeByte(value.getType().ordinal());
            out.writeByte(value.getFactory().size());
            for (Map.Entry<String, ParticleParameterType> parameter : value.getFactory().entrySet()) {
                byte[] key = parameter.getKey().getBytes(StandardCharsets.US_ASCII);
                out.writeByte(key.length);
                out.write(key);
                out.writeByte(parameter.getValue().ordinal());
            }
        }
    }

    public void add(int i, Particle particle) {
        byId.put(i, particle);
        byType.put(particle.getType(), particle);
    }

    public Particle getById(int id) {
        return byId.get(id);
    }

    public Particle getByType(ParticleType type) {
        return byType.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleMapping that = (ParticleMapping) o;
        return Objects.equals(byId, that.byId) && Objects.equals(byType, that.byType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byId, byType);
    }
}
