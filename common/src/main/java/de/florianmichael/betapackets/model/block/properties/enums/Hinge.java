package de.florianmichael.betapackets.model.block.properties.enums;

public enum Hinge {
    LEFT, RIGHT;

    public static Hinge getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}