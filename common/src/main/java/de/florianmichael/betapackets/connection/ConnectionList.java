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

import de.florianmichael.betapackets.model.game.PlayerDiggingStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionList {

    private final List<UserConnection> connections = new ArrayList<>();

    public void addConnection(UserConnection connection) {
        connections.add(connection);
    }

    public void removeConnection(UserConnection connection) {
        connections.remove(connection);
    }

    public UserConnection getConnectionByPlayer(Object player) {
        for (UserConnection connection : connections) {
            if (connection.getPlayer() == player)
                return connection;
        }
        return null;
    }

    public UserConnection getConnectionByUUID(UUID uuid) {
        for (UserConnection connection : connections) {
            if (connection.getUuid() != null && connection.getUuid().equals(uuid))
                return connection;
        }
        return null;
    }

    public List<UserConnection> getConnections() {
        return connections;
    }
}
