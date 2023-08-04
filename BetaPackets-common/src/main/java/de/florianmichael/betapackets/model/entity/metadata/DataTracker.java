package de.florianmichael.betapackets.model.entity.metadata;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DataTracker {

    private Map<String, Metadata> typeToData = new HashMap<>();
    private Map<Integer, Metadata> indexToData = new HashMap<>();

    public void register(Metadata data) {
        if (typeToData.containsKey(data.getName())) throw new IllegalArgumentException("Duplicate entry (name): " + typeToData.get(data.getName()) + " -> " + data);
        typeToData.put(data.getName(), data);

        if (indexToData.containsKey(data.getIndex())) throw new IllegalArgumentException("Duplicate entry (index): " + indexToData.get(data.getIndex()) + " -> " + data);
        indexToData.put(data.getIndex(), data);
    }

    public Collection<Metadata> getMetadata() {
        return typeToData.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTracker that = (DataTracker) o;
        return Objects.equals(typeToData, that.typeToData) && Objects.equals(indexToData, that.indexToData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeToData, indexToData);
    }

    @Override
    public String toString() {
        return "DataTracker{" +
                "typeToData=" + typeToData +
                ", indexToData=" + indexToData +
                '}';
    }
}
