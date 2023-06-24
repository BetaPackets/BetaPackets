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

import de.florianmichael.betapackets.PacketRegistryManager;
import de.florianmichael.betapackets.base.registry.PacketRegistry;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserConnection {
    public final TrackingData trackingData = new TrackingData();
    private final Channel channel;
    private final PacketRegistryManager packetRegistryManager;

    private NetworkState state;
    private ProtocolCollection protocolVersion;
    private PacketRegistry currentRegistry;

    private UUID player;
    private boolean loaded;

    public UserConnection(Channel channel, PacketRegistryManager packetRegistryManager) {
        this.channel = channel;
        this.packetRegistryManager = packetRegistryManager;
    }

    public void init(final NetworkState state, final ProtocolCollection protocolVersion) {
        this.protocolVersion = protocolVersion; // Version has to be valid for setState() detection
        this.setState(state);
        this.loaded = true;
    }

    public void setState(NetworkState state) {
        this.state = state;

        for (Map.Entry<ProtocolCollection, List<PacketRegistry>> listEntry : packetRegistryManager.getPacketRegistries().entrySet()) {
            if (this.protocolVersion.isNewerThanOrEqualTo(listEntry.getKey())) {
                for (PacketRegistry packetRegistry : listEntry.getValue()) {
                    if (packetRegistry.getNetworkState() == this.state) {
                        this.currentRegistry = packetRegistry;
                    }
                }
            }
        }
    }

    /**
     * Tracking data for the user connection, has to be updated by the implementation of the UserConnection
     */
    public static class TrackingData {
        public boolean isInOverWorld = false;
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

    public PacketRegistry getCurrentRegistry() {
        return currentRegistry;
    }

    public UUID getPlayer() {
        return player;
    }
}
