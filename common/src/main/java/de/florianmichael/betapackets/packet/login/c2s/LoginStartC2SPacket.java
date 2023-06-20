package de.florianmichael.betapackets.packet.login.c2s;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class LoginStartC2SPacket extends Packet {
    private final String name;

    public LoginStartC2SPacket(final FriendlyByteBuf buf) {
        this(buf.readString(16));
    }

    public LoginStartC2SPacket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // C -> S only, not implemented
    }

    @Override
    public String toString() {
        return "LoginStartC2SPacket{" +
                "name='" + name + '\'' +
                '}';
    }
}
