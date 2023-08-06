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

package de.florianmichael.betapackets.connection;

import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.packet.NetworkSide;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.ids.PacketIdBase1_7;
import de.florianmichael.betapackets.packet.ids.PacketIdList;
import de.florianmichael.betapackets.packet.type.Packet;
import io.netty.channel.Channel;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

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
    private TextComponentSerializer textComponentSerializer;


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

        if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_19_4)) {
            textComponentSerializer = TextComponentSerializer.V1_19_4;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_18_1)) {
            textComponentSerializer = TextComponentSerializer.V1_18;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_17)) {
            textComponentSerializer = TextComponentSerializer.V1_17;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_16)) {
            textComponentSerializer = TextComponentSerializer.V1_16;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_15)) {
            textComponentSerializer = TextComponentSerializer.V1_15;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_14)) {
            textComponentSerializer = TextComponentSerializer.V1_14;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_12)) {
            textComponentSerializer = TextComponentSerializer.V1_12;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            textComponentSerializer = TextComponentSerializer.V1_9;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_8)) {
            textComponentSerializer = TextComponentSerializer.V1_8;
        } else if (protocolVersion.isNewerThanOrEqualTo(ProtocolCollection.R1_7_5)) {
            textComponentSerializer = TextComponentSerializer.V1_7;
        }
        if (textComponentSerializer == null) throw new IllegalArgumentException("No text-serializer for " + protocolVersion);
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

    public TextComponentSerializer getTextComponentSerializer() {
        return textComponentSerializer;
    }
}
