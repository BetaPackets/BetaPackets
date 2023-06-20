package de.florianmichael.betapackets.packet.registry;

import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.packet.handshake.HandshakeC2SPacket;

public class PacketRegistryLogin1_7 {

    public static class PacketRegistryHandshake1_7 extends PacketRegistry {

        public PacketRegistryHandshake1_7() {
            super(NetworkState.HANDSHAKE);
        }

        @Override
        public void init() {
            this.registerPacket(NetworkSide.SERVERBOUND, 0x00, HandshakeC2SPacket.class);
        }
    }
}
