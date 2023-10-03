package de.florianmichael.betapackets.model.block.properties.enums;

public enum Leaves {
    LARGE, NONE, SMALL;

    public static Leaves getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}