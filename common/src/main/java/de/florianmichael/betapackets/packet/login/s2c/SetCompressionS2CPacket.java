package de.florianmichael.betapackets.packet.login.s2c;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class SetCompressionS2CPacket extends Packet {
    private final int threshold;

    public SetCompressionS2CPacket(final FriendlyByteBuf buf) {
        this(buf.readVarInt());
    }

    public SetCompressionS2CPacket(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // S -> C only, not implemented
    }

    @Override
    public String toString() {
        return "SetCompressionS2CPacket{" +
                "threshold=" + threshold +
                '}';
    }
}
