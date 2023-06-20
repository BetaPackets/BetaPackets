package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.model.ProtocolCollection;
import de.florianmichael.betapackets.packet.registry.BasePacketRegistry1_7;
import de.florianmichael.betapackets.packet.registry.PacketRegistry1_8;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistryManager {
    private final Map<ProtocolCollection, PacketRegistry> packetRegistries = new HashMap<>();

    public PacketRegistryManager() {
        // Handshake, Status and Login definition since v1.7.0
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryHandshake1_7());
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryLogin1_7());

        // Play packets since v1.7.0
        registerPacketRegistry(ProtocolCollection.R1_8, new PacketRegistry1_8());
    }

    public void registerPacketRegistry(final ProtocolCollection version, final PacketRegistry packetRegistry) {
        packetRegistries.put(version, packetRegistry);
    }

    public Packet createModel(final ProtocolCollection version, final NetworkState state, final NetworkSide side, final int packetId, final FriendlyByteBuf buf) {
        for (Map.Entry<ProtocolCollection, PacketRegistry> packetRegistryEntry : packetRegistries.entrySet()) {
            final PacketRegistry packetRegistry = packetRegistryEntry.getValue();

            if (packetRegistryEntry.getKey().isOlderThan(version) || packetRegistry.getNetworkState() != state || !packetRegistry.getPackets(side).containsKey(packetId)) continue;

            try {
                final Class<? extends Packet> clazz = packetRegistry.getPackets(side).get(packetId);

                return clazz.getConstructor(FriendlyByteBuf.class).newInstance(buf);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                BetaPackets.getPlatform().getLogging().severe("Couldn't create packet " + packetId + " in " + packetRegistry.getClass().getSimpleName() + "!");
                e.printStackTrace();
            }
        }
        return null;
    }
}
