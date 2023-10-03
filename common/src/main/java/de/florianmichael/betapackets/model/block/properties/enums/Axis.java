package de.florianmichael.betapackets.model.block.properties.enums;

public enum Axis {
    X, Y, Z;

    public static Axis getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}