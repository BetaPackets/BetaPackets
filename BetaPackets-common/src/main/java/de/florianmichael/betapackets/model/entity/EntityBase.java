package de.florianmichael.betapackets.model.entity;

public enum EntityBase {

    PLAYER(true),
    PAINTING(false),
    LIVING(true),
    EXPERIENCE_ORB(false),
    BASE(false);

    private final boolean living;

    EntityBase(boolean living) {
        this.living = living;
    }

    public boolean isLiving() {
        return living;
    }
}
