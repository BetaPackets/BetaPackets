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

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.registry.model.IGameStateType;
import de.florianmichael.betapackets.base.registry.model.IParticleType;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.base.registry.model.IPotionEffectType;
import de.florianmichael.betapackets.base.registry.model.IMetadataType;

import java.lang.reflect.InvocationTargetException;
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

    public abstract IMetadataType getMetadataType();
    public abstract IPotionEffectType getPotionEffectType();
    public abstract IParticleType getParticleType();
    public abstract IGameStateType getGameStateType();

    public void registerPacket(final NetworkSide side, final int id, final Class<? extends Packet> packet) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (packets.containsKey(id)) {
            BetaPackets.getPlatform().getLogging().warning("Packet " + id + "already registered in " + getClass().getSimpleName() + ", overwriting current packet!");
        }
        packets.put(id, packet);
    }

    public void changePacketID(final NetworkSide side, final int id, final int newId) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (!packets.containsKey(id)) {
            BetaPackets.getPlatform().getLogging().severe("Packet " + id + " doesn't exist, ignoring redirect to " + newId + "!");
            return;
        }
        packets.put(newId, packets.get(id));
    }

    public Map<Integer, Class<? extends Packet>> getPackets(final NetworkSide side) {
        return side == NetworkSide.CLIENTBOUND ? clientboundPackets : serverboundPackets;
    }

    public Packet createModel(final NetworkSide side, final int packetId, final FunctionalByteBuf buf) {
        final Map<Integer, Class<? extends Packet>> packets = getPackets(side);

        if (!packets.containsKey(packetId)) return null;

        try {
            final Class<? extends Packet> clazz = packets.get(packetId);

            return clazz.getConstructor(FunctionalByteBuf.class).newInstance(buf);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            BetaPackets.getPlatform().getLogging().severe("Couldn't create packet " + packetId + " in " + getClass().getSimpleName() + "!");
            e.printStackTrace();
        }
        return null;
    }

    public NetworkState getNetworkState() {
        return networkState;
    }
}
