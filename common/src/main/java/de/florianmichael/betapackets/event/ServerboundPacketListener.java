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

package de.florianmichael.betapackets.event;

import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;

public interface ServerboundPacketListener extends Listener {

    void onClientBoundPacket(ServerboundPacketEvent<?> event);

    class ServerboundPacketEvent<T> extends CancellableEvent<ServerboundPacketListener> {
        private final EventExecutor<ServerboundPacketListener> eventExecutor = listener -> listener.onClientBoundPacket(this);

        public UserConnection userConnection;
        public NetworkState networkState;
        public Packet packet;
        public T player;

        public ServerboundPacketEvent(UserConnection userConnection, NetworkState networkState, Packet packet, T player) {
            this.userConnection = userConnection;
            this.networkState = networkState;
            this.packet = packet;
            this.player = player;
        }

        @Override
        public EventExecutor<ServerboundPacketListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<ServerboundPacketListener> getListenerType() {
            return ServerboundPacketListener.class;
        }
    }
}
