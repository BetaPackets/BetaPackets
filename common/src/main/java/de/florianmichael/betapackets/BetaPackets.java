package de.florianmichael.betapackets;

import de.florianmichael.betapackets.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.base.PacketRegistryManager;

public class BetaPackets {
    private static BetaPackets instance;
    private static BetaPacketsPlatform platform;
    private static PacketRegistryManager packetRegistryManager;

    public static void init(final BetaPacketsPlatform platform) {
        BetaPackets.platform = platform;
        BetaPackets.packetRegistryManager = new PacketRegistryManager();
    }

    public static BetaPacketsPlatform getPlatform() {
        return platform;
    }
}
