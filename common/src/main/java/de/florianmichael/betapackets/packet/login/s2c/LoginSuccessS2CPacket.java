package de.florianmichael.betapackets.packet.login.s2c;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class LoginSuccessS2CPacket extends Packet {
    private final String uuid;
    private final String username;

    public LoginSuccessS2CPacket(final FriendlyByteBuf buf) {
        this(
                buf.readString(36),
                buf.readString(16)
        );
    }

    public LoginSuccessS2CPacket(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // S -> C only, not implemented
    }

    @Override
    public String toString() {
        return "LoginSuccessS2CPacket{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
