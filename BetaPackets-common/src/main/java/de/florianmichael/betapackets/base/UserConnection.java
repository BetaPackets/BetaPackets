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

package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.packet.NetworkSide;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.ids.PacketIdBase1_7;
import de.florianmichael.betapackets.packet.ids.PacketIdList;
import de.florianmichael.betapackets.packet.type.Packet;
import io.netty.channel.Channel;

import java.util.List;
import java.util.UUID;

/**
 * This class reflects a connection of a client, in it all important data for the decoder/encoder as well as the packet models are stored.
 */
public class UserConnection {

    private final Channel channel;

    private NetworkState state;
    private List<Packet> s2cPackets;
    private List<Packet> c2sPackets;
    private ProtocolCollection protocolVersion;
    private PacketIdList packetIdList;


    /**
     * The UUID of the player
     */
    private UUID player;

    /**
     * If the connection is fully loaded
     */
    private boolean loaded;

    /**
     * Creates a new {@link UserConnection}
     *
     * @param channel The channel of the connection
     */
    public UserConnection(Channel channel) {
        this.channel = channel;
        this.packetIdList = PacketIdBase1_7.INSTANCE;
        setState(NetworkState.HANDSHAKE);
    }

    /**
     * Initializes the connection
     *
     * @param state           The {@link NetworkState} of the connection
     * @param protocolVersion The {@link ProtocolCollection} of the connection
     */
    public void init(final NetworkState state, final ProtocolCollection protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.packetIdList = protocolVersion.getPacketIdList();
        this.setState(state);
        this.loaded = true;
    }

    public void setState(NetworkState state) {
        this.state = state;
        this.s2cPackets = this.packetIdList.getPacketIds(NetworkSide.S2C, state);
        this.c2sPackets = this.packetIdList.getPacketIds(NetworkSide.C2S, state);
    }

    public List<Packet> getC2SPackets() {
        return c2sPackets;
    }

    public List<Packet> getS2CPackets() {
        return s2cPackets;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public boolean hasLoaded() {
        return loaded;
    }

    public Channel getChannel() {
        return channel;
    }

    public NetworkState getState() {
        return state;
    }

    public ProtocolCollection getProtocolVersion() {
        return protocolVersion;
    }

    public PacketIdList getPacketIdList() {
        return packetIdList;
    }

    public UUID getPlayer() {
        return player;
    }
}
