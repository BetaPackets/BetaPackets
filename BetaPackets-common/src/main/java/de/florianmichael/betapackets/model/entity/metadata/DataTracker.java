package de.florianmichael.betapackets.model.entity.metadata;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DataTracker {

    private Map<String, Metadata> typeToData = new HashMap<>();
    private Map<Integer, Metadata> indexToData = new HashMap<>();

    public void register(String type, Metadata data) {
        typeToData.put(type, data);
        indexToData.put(data.getIndex(), data);
    }

    public void write(DataOutputStream data) throws IOException {
        data.writeByte(typeToData.size());
        for (Metadata value : typeToData.values()) {
            data.writeByte(value.getIndex());
            data.writeByte(value.getCodecType().ordinal());
            byte[] name = value.getName().getBytes(StandardCharsets.US_ASCII);
            data.writeByte(name.length);
            data.write(name);
        }
    }

}
