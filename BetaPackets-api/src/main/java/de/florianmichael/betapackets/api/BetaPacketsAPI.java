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

package de.florianmichael.betapackets.api;

import de.florianmichael.betapackets.api.event.ClientboundPacketListener;
import de.florianmichael.betapackets.api.event.PlayerEarlyJoinListener;
import de.florianmichael.betapackets.api.event.ServerboundPacketListener;
import de.florianmichael.dietrichevents2.DietrichEvents2;

/**
 * This class represents the user API for BetaPackets, it's using <a href="https://github.com/FlorianMichael/DietrichEvents">DietrichEvents</a> as event backend
 */
public class BetaPacketsAPI {
    private final DietrichEvents2 eventProvider = new DietrichEvents2(Throwable::printStackTrace);

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener) {
        return registerIncomingPacketListener(listener, 0);
    }

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener, final int priority) {
        eventProvider.subscribe(ServerboundPacketListener.ServerboundPacketEvent.ID, listener, priority);
        return listener;
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener) {
        return registerOutgoingPacketListener(listener, 0);
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener, final int priority) {
        eventProvider.subscribe(ClientboundPacketListener.ClientboundPacketEvent.ID, listener, priority);
        return listener;
    }

    public PlayerEarlyJoinListener registerPlayerEarlyJoinListener(final PlayerEarlyJoinListener listener) {
        return registerPlayerEarlyJoinListener(listener, 0);
    }

    public PlayerEarlyJoinListener registerPlayerEarlyJoinListener(final PlayerEarlyJoinListener listener, final int priority) {
        eventProvider.subscribe(PlayerEarlyJoinListener.PlayerEarlyJoinEvent.ID, listener, priority);
        return listener;
    }

    public void removeIncomingPacketListener(final ServerboundPacketListener listener) {
        eventProvider.unsubscribe(ServerboundPacketListener.ServerboundPacketEvent.ID, listener);
    }

    public void removeOutgoingPacketListener(final ClientboundPacketListener listener) {
        eventProvider.unsubscribe(ClientboundPacketListener.ClientboundPacketEvent.ID, listener);
    }

    public void removePlayerEarlyJoinListener(final PlayerEarlyJoinListener listener) {
        eventProvider.unsubscribe(PlayerEarlyJoinListener.PlayerEarlyJoinEvent.ID, listener);
    }

    public DietrichEvents2 getEventProvider() {
        return eventProvider;
    }
}
