package de.florianmichael.betapackets.model.block.properties.enums;

public enum Instrument {
    BANJO,
    BASEDRUM,
    BASS,
    BELL,
    BIT,
    CHIME,
    COW_BELL,
    DIDGERIDOO,
    FLUTE,
    GUITAR,
    HARP,
    HAT,
    IRON_XYLOPHONE,
    PLING,
    SNARE,
    XYLOPHONE,
    ZOMBIE,
    SKELETON,
    CREEPER,
    DRAGON,
    WITHER_SKELETON,
    PIGLIN,
    CUSTOM_HEAD;

    public static Instrument getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}