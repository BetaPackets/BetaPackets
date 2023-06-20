package de.florianmichael.betapackets;

import de.florianmichael.betapackets.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.base.PacketRegistryManager;
import de.florianmichael.betapackets.base.TrackingConnections;

public class BetaPackets {
    private static BetaPacketsPlatform platform;

    private static PacketRegistryManager packetRegistryManager;
    private static TrackingConnections trackingConnections;

    public static void init(final BetaPacketsPlatform platform) {
        BetaPackets.platform = platform;

        BetaPackets.packetRegistryManager = new PacketRegistryManager();
        BetaPackets.trackingConnections = new TrackingConnections();
    }

    public static BetaPacketsPlatform getPlatform() {
        return platform;
    }

    public static PacketRegistryManager getPacketRegistryManager() {
        return packetRegistryManager;
    }

    public static TrackingConnections getTrackingConnections() {
        return trackingConnections;
    }
}
