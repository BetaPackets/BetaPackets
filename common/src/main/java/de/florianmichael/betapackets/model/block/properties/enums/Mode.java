package de.florianmichael.betapackets.model.block.properties.enums;

public enum Mode {
    CORNER, COMPARE, DATA, LOAD, SAVE, SUBTRACT;

    public static Mode getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}