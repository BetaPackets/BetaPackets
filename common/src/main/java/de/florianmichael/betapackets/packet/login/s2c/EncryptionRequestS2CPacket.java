package de.florianmichael.betapackets.packet.login.s2c;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;

public class EncryptionRequestS2CPacket extends Packet {
    private final String serverId;
    private final byte[] key;
    private final byte[] verifyToken;

    public EncryptionRequestS2CPacket(final FriendlyByteBuf buf) {
        this(
                buf.readString(20),
                buf.readByteArray(buf.readVarInt()),
                buf.readByteArray(buf.readVarInt())
        );
    }

    public EncryptionRequestS2CPacket(String serverId, byte[] key, byte[] verifyToken) {
        this.serverId = serverId;
        this.key = key;
        this.verifyToken = verifyToken;
    }

    public String getServerId() {
        return serverId;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // S -> C only, not implemented
    }

    @Override
    public String toString() {
        return "EncryptionRequestS2CPacket{" +
                "serverId='" + serverId + '\'' +
                ", key=" + Arrays.toString(key) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }
}
