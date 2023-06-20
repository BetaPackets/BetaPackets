package de.florianmichael.betapackets.model;

public enum NetworkState {

    HANDSHAKE, PLAY, STATUS, LOGIN;

    public int getId() {
        return ordinal() - 1;
    }

    public static NetworkState fromId(int id) {
        return values()[id + 1];
    }
}
