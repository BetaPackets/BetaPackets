package de.florianmichael.betapackets.base.packet;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;

import java.util.HashMap;
import java.util.Map;

public abstract class PacketRegistry {
    private final Map<Integer, Class<? extends Packet>> clientboundPackets = new HashMap<>();
    private final Map<Integer, Class<? extends Packet>> serverboundPackets = new HashMap<>();

    private final NetworkState networkState;

    public PacketRegistry(NetworkState networkState) {
        this.networkState = networkState;
    }

    public abstract void init();

    public void registerPacket(final NetworkSide side, final int id, final Class<? extends Packet> packet) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (packets.containsKey(id)) {
            BetaPackets.getPlatform().getLogger().warning("Packet " + id + "already registered in " + getClass().getSimpleName() + ", overwriting current packet!");
        }
        packets.put(id, packet);
    }

    public void changePacketID(final NetworkSide side, final int id, final int newId) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (!packets.containsKey(id)) {
            BetaPackets.getPlatform().getLogger().severe("Packet " + id + " doesn't exist, ignoring redirect to " + newId + "!");
            return;
        }
        packets.put(newId, packets.get(id));
    }

    public Map<Integer, Class<? extends Packet>> getPackets(final NetworkSide side) {
        return side == NetworkSide.CLIENTBOUND ? clientboundPackets : serverboundPackets;
    }

    public NetworkState getNetworkState() {
        return networkState;
    }
}
