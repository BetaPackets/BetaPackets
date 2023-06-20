package de.florianmichael.betapackets.packet.login.c2s;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;

public class EncryptionResponseC2SPacket extends Packet {
    private final byte[] sharedSecret;
    private final byte[] verifyToken;

    public EncryptionResponseC2SPacket(final FriendlyByteBuf buf) {
        this(buf.readByteArray(), buf.readByteArray());
    }

    public EncryptionResponseC2SPacket(byte[] sharedSecret, byte[] verifyToken) {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // C -> S only, not implemented
    }

    @Override
    public String toString() {
        return "EncryptionResponseC2SPacket{" +
                "sharedSecret=" + Arrays.toString(sharedSecret) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }
}
