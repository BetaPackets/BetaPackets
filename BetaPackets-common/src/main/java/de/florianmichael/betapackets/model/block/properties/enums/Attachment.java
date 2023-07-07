package de.florianmichael.betapackets.model.block.properties.enums;

public enum Attachment {
    CEILING, DOUBLE_WALL, FLOOR, SINGLE_WALL;

    public static Attachment getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}