package de.florianmichael.betapackets.model.block.properties.enums;

public enum Half {
    BOTTOM, LOWER, TOP, UPPER;

    public static Half getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}