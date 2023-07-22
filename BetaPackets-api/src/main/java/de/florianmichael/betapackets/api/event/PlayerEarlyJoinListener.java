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

package de.florianmichael.betapackets.api.event;

import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.dietrichevents2.AbstractEvent;

import java.util.UUID;

/**
 * This event is fired when a player joins the server before the protocol version is known.
 */
public interface PlayerEarlyJoinListener {

    /**
     * Called when a player joins the server before the protocol version is known.
     * @param event The event.
     */
    void onPlayerEarlyJoin(final PlayerEarlyJoinEvent event);

    /**
     * This class represents the event that is called when a player joins the server before the protocol version is known.
     */
    class PlayerEarlyJoinEvent extends AbstractEvent<PlayerEarlyJoinListener> {
        public final static int ID = 2;

        public UUID uuid;
        public String username;
        public ProtocolCollection version;

        public PlayerEarlyJoinEvent(UUID uuid, String username, ProtocolCollection version) {
            this.uuid = uuid;
            this.username = username;
            this.version = version;
        }

        @Override
        public void call(PlayerEarlyJoinListener listener) {
            listener.onPlayerEarlyJoin(this);
        }
    }
}
