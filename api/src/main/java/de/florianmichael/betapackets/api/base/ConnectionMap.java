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

package de.florianmichael.betapackets.api.base;

import de.florianmichael.betapackets.api.BetaPackets;
import de.florianmichael.betapackets.api.BetaPacketsAPI;
import de.florianmichael.betapackets.base.UserConnection;
import de.florianmichael.betapackets.packet.play.s2c.JoinGameS2CPacket;
import de.florianmichael.betapackets.packet.play.s2c.RespawnS2CPacket;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * This class stores all UserConnection's and updates the TrackingData fields
 */
public class ConnectionMap {

    public final Map<Channel, UserConnection> userConnectionMap = new HashMap<>();

    public ConnectionMap(final BetaPacketsAPI api) {
        api.registerOutgoingPacketListener(event -> {
            if (event.packet instanceof RespawnS2CPacket || event.packet instanceof JoinGameS2CPacket) {
                userConnectionMap.forEach((channel, userConnection) -> {
                    System.out.println(userConnection.getPlayer() + " " + event.userConnection.getPlayer());
                    if (userConnection.getPlayer().equals(event.userConnection.getPlayer())) {
                        final UserConnection.TrackingData data = userConnection.trackingData;

                        if (event.packet instanceof RespawnS2CPacket) {
                            data.isInOverWorld = ((RespawnS2CPacket) event.packet).dimension == 0;
                        } else {
                            data.isInOverWorld = ((JoinGameS2CPacket) event.packet).dimension == 0;
                        }
                    }
                });
            }
        });
    }

    public void addConnection(final Channel channel, final UserConnection userConnection) {
        userConnectionMap.put(channel, userConnection);
    }

    public void removeConnection(final Channel channel) {
        userConnectionMap.remove(channel);
    }

    public boolean hasConnection(final Channel channel) {
        return userConnectionMap.containsKey(channel);
    }
}
