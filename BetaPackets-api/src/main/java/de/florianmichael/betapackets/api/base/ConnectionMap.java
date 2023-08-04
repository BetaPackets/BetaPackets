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

import de.florianmichael.betapackets.api.BetaPacketsAPI;
import de.florianmichael.betapackets.base.UserConnection;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * This class stores all UserConnection's and updates the TrackingData fields
 */
public class ConnectionMap {

    /**
     * Stores all UserConnection's
     */
    public final Map<Channel, UserConnection> userConnectionMap = new HashMap<>();

    /**
     * Creates a new ConnectionMap and registers all needed listeners to update the {@link UserConnection.TrackingData} fields
     * @param api The {@link BetaPacketsAPI} instance
     */
    public ConnectionMap(final BetaPacketsAPI api) {
    }

    /**
     * Adds a new UserConnection to the map
     * @param channel The channel of the connection
     * @param userConnection The UserConnection
     */
    public void addConnection(final Channel channel, final UserConnection userConnection) {
        userConnectionMap.put(channel, userConnection);
    }

    /**
     * Removes a UserConnection from the map (e.g. if the client disconnects)
     * @param channel The channel of the connection
     */
    public void removeConnection(final Channel channel) {
        userConnectionMap.remove(channel);
    }

    /**
     * @param channel The channel of the connection
     * @return if the channel has a tracking UserConnection
     */
    public boolean hasConnection(final Channel channel) {
        return userConnectionMap.containsKey(channel);
    }
}
