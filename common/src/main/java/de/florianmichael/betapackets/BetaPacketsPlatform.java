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

import net.lenni0451.mcstructs.core.TextFormatting;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * This class represents a BetaPackets platform implementation
 *
 * @param <T> The player class of the platform
 */
public interface BetaPacketsPlatform<T> {

    /**
     * Loads the platform into BetaPackets
     */
    default void loadPlatform() {
        BetaPackets.init(this);

        final Logger logger = getLogging();
        logger.info("Injecting BetaPackets platform into " + getPlatformName());

        kickAll(TextFormatting.GREEN.toLegacy() + "Reload complete, please rejoin");
    }

    /**
     * Unloads the platform from BetaPackets
     */
    default void unloadPlatform() {
        kickAll(TextFormatting.GREEN.toLegacy() + "Reload complete, please rejoin");
    }

    /**
     * @return The (logger) of the platform
     */
    Logger getLogging();

    /**
     * @return The name of the platform
     */
    String getPlatformName();

    /**
     * @param uuid The uuid of the player
     * @return The player object
     */
    T getPlayer(final UUID uuid);

    /**
     * @param path The path of the resource
     * @return The resource as an input stream
     */
    InputStream getResource(String path);

    /**
     * @return The plugin object
     */
    Object getPlugin();

    /**
     * @param name The name of the plugin
     * @return If the plugin is loaded
     */
    boolean isPluginLoaded(String name);

    /**
     * @param message The message to kick the players with
     */
    void kickAll(String message);
}
