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

package de.florianmichael.betapackets;

import de.florianmichael.betapackets.api.BetaPacketsAPI;
import de.florianmichael.betapackets.base.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.base.PacketRegistryManager;

public class BetaPackets {
    private static BetaPacketsPlatform<?> platform;

    private static BetaPacketsAPI api;
    private static PacketRegistryManager packetRegistryManager;

    public static void init(final BetaPacketsPlatform<?> platform) {
        BetaPackets.platform = platform;

        BetaPackets.api = new BetaPacketsAPI();
        BetaPackets.packetRegistryManager = new PacketRegistryManager();
    }

    public static BetaPacketsPlatform<?> getPlatform() {
        return platform;
    }

    public static BetaPacketsAPI getAPI() {
        return api;
    }

    public static PacketRegistryManager getPacketRegistryManager() {
        return packetRegistryManager;
    }
}
