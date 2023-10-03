package de.florianmichael.betapackets.model.block.properties.enums;

public enum Thickness {
    BASE, FRUSTUM, MIDDLE, TIP, TIP_MERGE;

    public static Thickness getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}