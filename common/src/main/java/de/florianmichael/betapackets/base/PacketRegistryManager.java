package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.packet.registry.BasePacketRegistry1_7;
import de.florianmichael.betapackets.packet.registry.PacketRegistry1_8;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PacketRegistryManager {
    private final List<PacketRegistry> packetRegistries = new ArrayList<>();

    public void init() {
        // Handshake, Status and Login definition since v1.7.0
        registerPacketRegistry(new BasePacketRegistry1_7.PacketRegistryHandshake1_7());
        registerPacketRegistry(new BasePacketRegistry1_7.PacketRegistryLogin1_7());

        // Play packets since v1.7.0
        registerPacketRegistry(new PacketRegistry1_8());
    }

    public void registerPacketRegistry(final PacketRegistry packetRegistry) {
        packetRegistries.add(packetRegistry);
    }

    public Packet createModel(final NetworkState state, final NetworkSide side, final int packetId, final FriendlyByteBuf buf) {
        for (final PacketRegistry packetRegistry : packetRegistries) {
            if (packetRegistry.getNetworkState() == state) {
                if (!packetRegistry.getPackets(side).containsKey(packetId)) {
                    BetaPackets.getPlatform().getLogger().severe("Packet " + packetId + " doesn't exist in " + packetRegistry.getClass().getSimpleName() + "!");
                    return null;
                }
                try {
                    final Class<? extends Packet> clazz = packetRegistry.getPackets(side).get(packetId);

                    return clazz.getConstructor(FriendlyByteBuf.class).newInstance(buf);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    BetaPackets.getPlatform().getLogger().severe("Couldn't create packet " + packetId + " in " + packetRegistry.getClass().getSimpleName() + "!");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
