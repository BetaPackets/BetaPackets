package de.florianmichael.betapackets.model.block.properties.enums;

public enum SculkSensorPhase {
    ACTIVE, COOLDOWN, INACTIVE;

    public static SculkSensorPhase getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}