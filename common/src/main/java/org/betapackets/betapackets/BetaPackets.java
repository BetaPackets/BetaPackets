/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets;

import org.betapackets.betapackets.event.InternalHandshakeListener;
import org.betapackets.betapackets.mapping.MappingLoader;

import java.io.IOException;
import java.util.logging.Level;

/**
 * The main class of BetaPackets, it has all instances in it.
 */
public class BetaPackets {

    private static BetaPacketsPlatform<?> platform;
    private static BetaPacketsAPI api;

    /**
     * Called by {@link BetaPacketsPlatform#loadPlatform()} to initialize all instances and the platform
     *
     * @param platform The platform instance
     */
    public static void init(final BetaPacketsPlatform<?> platform) {
        if (BetaPackets.platform != null) throw new IllegalStateException("BetaPackets is already initialized");

        BetaPackets.platform = platform;

        BetaPackets.api = new BetaPacketsAPI();
        BetaPackets.api.registerListener(new InternalHandshakeListener(platform));

        try {
            MappingLoader.load(platform.getResource("mappings.bin"));
        } catch (IOException e) {
            platform.getLogging().log(Level.SEVERE, "Failed to load mappings, quitting process.", e);
            System.exit(1);
        }
    }

    public static BetaPacketsPlatform<?> getPlatform() {
        return platform;
    }

    public static BetaPacketsAPI getAPI() {
        return api;
    }
}
