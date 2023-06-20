package de.florianmichael.betapackets.packet.login.s2c;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class LoginDisconnectS2CPacket extends Packet {
    private final String reason;

    public LoginDisconnectS2CPacket(final FriendlyByteBuf buf) {
        this(buf.readString(32767));
    }

    public LoginDisconnectS2CPacket(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // S -> C only, not implemented
    }

    @Override
    public String toString() {
        return "LoginDisconnectS2CPacket{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
