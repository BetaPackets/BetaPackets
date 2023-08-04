package de.florianmichael.betapackets.mapping;

import com.google.gson.JsonArray;
import de.florianmichael.betapackets.model.entity.metadata.MetadataCodecType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MetadataCodecMapping {

    private final List<MetadataCodecType> types = new LinkedList<>();

    public static MetadataCodecMapping read(DataInputStream in) throws IOException {
        MetadataCodecMapping mapping = new MetadataCodecMapping();
        int length = in.readUnsignedByte();
        for (int i = 0; i < length; i++) {
            mapping.add(MetadataCodecType.values()[in.readUnsignedByte()]);
        }
        return mapping;
    }

    public static MetadataCodecMapping loadJson(JsonArray array) {
        MetadataCodecMapping mapping = new MetadataCodecMapping();
        array.forEach(element -> {
            mapping.add(MetadataCodecType.valueOf(element.getAsString()));
        });
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(types.size());
        for (MetadataCodecType type : types) {
            out.writeByte(type.ordinal());
        }
    }

    public void add(MetadataCodecType type) {
        types.add(type);
    }

    public MetadataCodecType getCodecType(int id) {
        return types.get(id);
    }

    public int getId(MetadataCodecType type) {
        return types.indexOf(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataCodecMapping that = (MetadataCodecMapping) o;
        return Objects.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types);
    }
}
