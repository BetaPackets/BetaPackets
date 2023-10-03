package de.florianmichael.betapackets.model.block.properties.enums;

public enum Orientation {
    DOWN_EAST,
    DOWN_NORTH,
    DOWN_SOUTH,
    DOWN_WEST,
    EAST_UP,
    NORTH_UP,
    SOUTH_UP,
    UP_EAST,
    UP_NORTH,
    UP_SOUTH,
    UP_WEST,
    WEST_UP;

    public static Orientation getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}