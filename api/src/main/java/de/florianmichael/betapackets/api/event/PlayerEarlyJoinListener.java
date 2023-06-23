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
import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

import java.util.UUID;

public interface PlayerEarlyJoinListener extends Listener {

    void onPlayerEarlyJoin(final PlayerEarlyJoinEvent event);

    class PlayerEarlyJoinEvent extends AbstractEvent<PlayerEarlyJoinListener> {
        private final EventExecutor<PlayerEarlyJoinListener> eventExecutor = listener -> listener.onPlayerEarlyJoin(this);

        public UUID uuid;
        public String username;
        public ProtocolCollection version;

        public PlayerEarlyJoinEvent(UUID uuid, String username, ProtocolCollection version) {
            this.uuid = uuid;
            this.username = username;
            this.version = version;
        }

        @Override
        public EventExecutor<PlayerEarlyJoinListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<PlayerEarlyJoinListener> getListenerType() {
            return PlayerEarlyJoinListener.class;
        }
    }
}
