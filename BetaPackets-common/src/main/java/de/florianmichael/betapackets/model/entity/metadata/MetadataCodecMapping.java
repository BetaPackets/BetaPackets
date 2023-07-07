package de.florianmichael.betapackets.model.entity.metadata;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MetadataCodecMapping {

    private final Map<Integer, MetadataCodecType> idToCodec = new HashMap<>();

    public void addCodec(int id, MetadataCodecType codec) {
        idToCodec.put(id, codec);
    }

    public MetadataCodecType getById(int id) {
        return idToCodec.get(id);
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        data.writeByte(idToCodec.size());
        for (Map.Entry<Integer, MetadataCodecType> entry : idToCodec.entrySet()) {
            data.writeByte(entry.getKey());
            data.writeByte(entry.getValue().ordinal());
        }
    }

}
