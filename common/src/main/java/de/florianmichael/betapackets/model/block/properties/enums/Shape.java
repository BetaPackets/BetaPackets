package de.florianmichael.betapackets.model.block.properties.enums;

public enum Shape {
    ASCENDING_EAST, ASCENDING_NORTH, ASCENDING_SOUTH, ASCENDING_WEST, EAST_WEST, INNER_LEFT, INNER_RIGHT, NORTH_EAST,
    NORTH_SOUTH, NORTH_WEST, OUTER_LEFT, OUTER_RIGHT, SOUTH_EAST, SOUTH_WEST, STRAIGHT;

    public static Shape getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}