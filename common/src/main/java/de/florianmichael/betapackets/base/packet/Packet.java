package de.florianmichael.betapackets.base.packet;

import de.florianmichael.betapackets.base.FriendlyByteBuf;

public abstract class Packet {

    public abstract void write(final FriendlyByteBuf buf);
}
