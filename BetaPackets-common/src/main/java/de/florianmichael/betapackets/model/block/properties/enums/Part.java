package de.florianmichael.betapackets.model.block.properties.enums;

public enum Part {
    FOOT, HEAD;

    public static Part getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}
