package de.florianmichael.betapackets.packet.handshake;

import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkState;

public class HandshakeC2SPacket extends Packet {
    private final int protocolVersion;
    private final String address;
    private final short port;
    private final NetworkState state;

    public HandshakeC2SPacket(final FriendlyByteBuf buf) {
        this(
                buf.readVarInt(),
                buf.readString(255),
                buf.readShort(),
                NetworkState.fromId(buf.readVarInt())
        );
    }

    public HandshakeC2SPacket(int protocolVersion, String address, short port, NetworkState state) {
        this.protocolVersion = protocolVersion;
        this.address = address;
        this.port = port;
        this.state = state;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // C -> S only, not implemented
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public short getPort() {
        return port;
    }

    public NetworkState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "HandshakeC2SPacket{" +
                "protocolVersion=" + protocolVersion +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", state=" + state +
                '}';
    }
}
