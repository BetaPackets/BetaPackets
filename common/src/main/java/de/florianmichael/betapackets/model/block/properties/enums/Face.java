package de.florianmichael.betapackets.model.block.properties.enums;

public enum Face {
    CEILING, FLOOR, WALL;

    public static Face getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}