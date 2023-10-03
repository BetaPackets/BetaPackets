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

package de.florianmichael.betapackets.packet.ids;

import de.florianmichael.betapackets.packet.NetworkSide;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.type.Packet;
import de.florianmichael.betapackets.packet.type.PacketType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PacketIdList {

    private final Map<NetworkState, List<Packet>> client = new HashMap<>();
    private final Map<NetworkState, List<Packet>> server = new HashMap<>();

    public void register(NetworkSide side, NetworkState state, int index, Packet type) {
        getSide(side).computeIfAbsent(state, k -> new LinkedList<>()).add(index, type);
    }

    public void register(NetworkSide side, NetworkState state, Packet type) {
        getSide(side).computeIfAbsent(state, k -> new LinkedList<>()).add(type);
    }

    public void registerPlayPacket(PacketType.Play.Client clientPacket) {
        register(NetworkSide.C2S, NetworkState.PLAY, clientPacket);
    }

    public void registerPlayPacket(PacketType.Play.Server serverPacket) {
        register(NetworkSide.S2C, NetworkState.PLAY, serverPacket);
    }

    public void registerPlayPacket(int id, PacketType.Play.Client clientPacket) {
        register(NetworkSide.C2S, NetworkState.PLAY, id, clientPacket);
    }

    public void registerPlayPacket(int id, PacketType.Play.Server serverPacket) {
        register(NetworkSide.S2C, NetworkState.PLAY, id, serverPacket);
    }

    public List<Packet> getPacketIds(NetworkSide side, NetworkState state) {
        return getSide(side).get(state);
    }

    public Map<NetworkState, List<Packet>> getSide(NetworkSide side) {
        Map<NetworkState, List<Packet>> map;
        if (side == NetworkSide.S2C) {
            map = server;
        } else if (side == NetworkSide.C2S) {
            map = client;
        } else { // just in case side==null or smth weird
            throw new IllegalStateException("Unknown side: " + side);
        }
        return map;
    }
}
