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

import de.florianmichael.betapackets.api.base.BetaPacketsPlatform;
import de.florianmichael.betapackets.api.base.ConnectionMap;
import de.florianmichael.betapackets.api.base.InternalHandshakeListener;
import de.florianmichael.betapackets.mapping.MappingLoader;

import java.io.IOException;

/**
 * The main class of BetaPackets, it has all instances in it.
 */
public class BetaPackets {

    /**
     * The platform instance
     */
    private static BetaPacketsPlatform<?> platform;

    private static BetaPacketsAPI api;
    private static ConnectionMap connectionMap;

    /**
     * Called by {@link BetaPacketsPlatform#loadPlatform()} to initialize all instances and the platform
     * @param platform The platform instance
     */
    public static void init(final BetaPacketsPlatform<?> platform) {
        BetaPackets.platform = platform;

        BetaPackets.api = new BetaPacketsAPI();
        BetaPackets.connectionMap = new ConnectionMap(BetaPackets.api);

        BetaPackets.api.registerListener(new InternalHandshakeListener(platform));

        try {
            MappingLoader.load(platform.getResource("mappings.bin"));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO Logger here
        }
    }

    public static BetaPacketsPlatform<?> getPlatform() {
        return platform;
    }

    public static BetaPacketsAPI getAPI() {
        return api;
    }

    public static ConnectionMap getConnectionMap() {
        return connectionMap;
    }
}
