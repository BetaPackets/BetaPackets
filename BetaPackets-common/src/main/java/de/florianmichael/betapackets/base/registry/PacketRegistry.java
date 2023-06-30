/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.betapackets.base.registry;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all clientbound and serverbound packets of one protocol and {@link NetworkState}.
 */
public abstract class PacketRegistry {

    /**
     * The clientbound packets.
     */
    private final Map<Integer, Class<? extends Packet>> clientboundPackets = new HashMap<>();

    /**
     * The serverbound packets.
     */
    private final Map<Integer, Class<? extends Packet>> serverboundPackets = new HashMap<>();

    /**
     * The {@link NetworkState} of this registry.
     */
    private final NetworkState networkState;

    public PacketRegistry(NetworkState networkState) {
        this.networkState = networkState;
    }

    /**
     * Supposed to be used to register all packets.
     */
    public abstract void init();

    /**
     * Registers a packet.
     * @param side the {@link NetworkSide} of the packet
     * @param id the id of the packet
     * @param packet the packet class
     */
    public void registerPacket(final NetworkSide side, final int id, final Class<? extends Packet> packet) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        packets.put(id, packet);
    }

    /**
     * Returns the packets of the given {@link NetworkSide}.
     * @param side the {@link NetworkSide}
     * @return the packets
     */
    public Map<Integer, Class<? extends Packet>> getPackets(final NetworkSide side) {
        return side == NetworkSide.CLIENTBOUND ? clientboundPackets : serverboundPackets;
    }

    /**
     * Creates a model of the given packet.
     * @param side the {@link NetworkSide}
     * @param packetId the id of the packet
     * @param buf the {@link FunctionalByteBuf} to read from
     * @return the model
     * @throws Exception if an error occurs
     */
    public Packet createModel(final NetworkSide side, final int packetId, final FunctionalByteBuf buf) throws Exception {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (!packets.containsKey(packetId)) return null;
        return packets.get(packetId).getConstructor(FunctionalByteBuf.class).newInstance(buf);
    }

    public NetworkState getNetworkState() {
        return networkState;
    }
}
