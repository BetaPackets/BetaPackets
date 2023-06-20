package de.florianmichael.betapackets.packet.registry;

import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.packet.handshake.HandshakeC2SPacket;
import de.florianmichael.betapackets.packet.login.c2s.EncryptionResponseC2SPacket;
import de.florianmichael.betapackets.packet.login.c2s.LoginStartC2SPacket;
import de.florianmichael.betapackets.packet.login.s2c.LoginDisconnectS2CPacket;
import de.florianmichael.betapackets.packet.login.s2c.LoginSuccessS2CPacket;
import de.florianmichael.betapackets.packet.login.s2c.SetCompressionS2CPacket;

public class BasePacketRegistry1_7 {

    public static class PacketRegistryHandshake1_7 extends PacketRegistry {

        public PacketRegistryHandshake1_7() {
            super(NetworkState.HANDSHAKE);
        }

        @Override
        public void init() {
            this.registerPacket(NetworkSide.SERVERBOUND, 0x00, HandshakeC2SPacket.class);
        }
    }

    public static class PacketRegistryLogin1_7 extends PacketRegistry {

        public PacketRegistryLogin1_7() {
            super(NetworkState.LOGIN);
        }

        @Override
        public void init() {
            // C -> S
            this.registerPacket(NetworkSide.SERVERBOUND, 0x00, LoginStartC2SPacket.class);
            this.registerPacket(NetworkSide.SERVERBOUND, 0x01, EncryptionResponseC2SPacket.class);

            // S -> C
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x00, LoginDisconnectS2CPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x01, EncryptionResponseC2SPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x02, LoginSuccessS2CPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x03, SetCompressionS2CPacket.class);
        }
    }
}
