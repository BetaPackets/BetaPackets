package de.florianmichael.betapackets.model.block.properties.enums;

public enum VerticalDirection {
    UP, DOWN;

    public static VerticalDirection getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}