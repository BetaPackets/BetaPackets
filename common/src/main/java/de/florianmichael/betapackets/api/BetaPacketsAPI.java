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

import de.florianmichael.betapackets.event.ClientboundPacketListener;
import de.florianmichael.betapackets.event.PlayerEarlyJoinListener;
import de.florianmichael.betapackets.event.ServerboundPacketListener;
import de.florianmichael.dietrichevents.DietrichEvents;

/**
 * This class represents the user API for BetaPackets, it's using <a href="https://github.com/FlorianMichael/DietrichEvents">DietrichEvents</a> as event backend
 */
public class BetaPacketsAPI {
    private final DietrichEvents eventProvider = DietrichEvents.createThreadSafe();

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener) {
        return registerIncomingPacketListener(listener, 0);
    }

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener, final int priority) {
        return eventProvider.subscribe(ServerboundPacketListener.class, listener, priority);
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener) {
        return registerOutgoingPacketListener(listener, 0);
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener, final int priority) {
        return eventProvider.subscribe(ClientboundPacketListener.class, listener, priority);
    }

    public PlayerEarlyJoinListener registerPlayerEarlyJoinListener(final PlayerEarlyJoinListener listener) {
        return registerPlayerEarlyJoinListener(listener, 0);
    }

    public PlayerEarlyJoinListener registerPlayerEarlyJoinListener(final PlayerEarlyJoinListener listener, final int priority) {
        return eventProvider.subscribe(PlayerEarlyJoinListener.class, listener, priority);
    }

    public void removeIncomingPacketListener(final ServerboundPacketListener listener) {
        eventProvider.unsubscribeInternal(ServerboundPacketListener.class, listener);
    }

    public void removeOutgoingPacketListener(final ClientboundPacketListener listener) {
        eventProvider.unsubscribeInternal(ClientboundPacketListener.class, listener);
    }

    public void removePlayerEarlyJoinListener(final PlayerEarlyJoinListener listener) {
        eventProvider.unsubscribeInternal(PlayerEarlyJoinListener.class, listener);
    }

    public DietrichEvents getEventProvider() {
        return eventProvider;
    }
}
