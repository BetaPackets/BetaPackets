package de.florianmichael.betapackets.model.block.properties.enums;

public enum Tilt {
    FULL, NONE, PARTIAL, UNSTABLE;

    public static Tilt getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}