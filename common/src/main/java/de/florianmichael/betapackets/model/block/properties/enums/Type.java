package de.florianmichael.betapackets.model.block.properties.enums;

public enum Type {
    BOTTOM, DOUBLE, LEFT, NORMAL, RIGHT, SINGLE, STICKY, TOP;

    public static Type getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}