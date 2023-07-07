package de.florianmichael.betapackets.model.entity.metadata;

public class Metadata {

    private int index;
    private String name;
    private MetadataCodecType codecType;

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
}
