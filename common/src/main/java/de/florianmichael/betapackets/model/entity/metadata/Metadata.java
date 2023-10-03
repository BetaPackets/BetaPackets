package de.florianmichael.betapackets.model.entity.metadata;

import java.util.Objects;

public class Metadata {

    private final int index;
    private final String name;
    private final MetadataCodecType codecType;

    public Metadata(int index, String name, MetadataCodecType codecType) {
        this.index = index;
        this.name = name;
        this.codecType = codecType;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public MetadataCodecType getCodecType() {
        return codecType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return index == metadata.index && Objects.equals(name, metadata.name) && codecType == metadata.codecType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name, codecType);
    }

    @Override
    public String toString() {
        return index + ":" + name + ":" + codecType.name().toLowerCase();
    }
}
