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

import de.florianmichael.betapackets.base.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.event.ClientboundPacketListener;
import de.florianmichael.betapackets.event.ServerboundPacketListener;

public class BetaPacketsAPI {
    private final BetaPacketsPlatform platform;

    public BetaPacketsAPI(BetaPacketsPlatform platform) {
        this.platform = platform;
    }

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener) {
        return registerIncomingPacketListener(listener, 0);
    }

    public ServerboundPacketListener registerIncomingPacketListener(final ServerboundPacketListener listener, final int priority) {
        return platform.getEventProvider().subscribe(ServerboundPacketListener.class, listener, priority);
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener) {
        return registerOutgoingPacketListener(listener, 0);
    }

    public ClientboundPacketListener registerOutgoingPacketListener(final ClientboundPacketListener listener, final int priority) {
        return platform.getEventProvider().subscribe(ClientboundPacketListener.class, listener, priority);
    }
}
