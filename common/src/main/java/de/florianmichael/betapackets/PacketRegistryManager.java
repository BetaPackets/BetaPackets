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

import de.florianmichael.betapackets.base.registry.PacketRegistry;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.registry.BasePacketRegistry1_7;
import de.florianmichael.betapackets.registry.BasePacketRegistry1_8;
import de.florianmichael.betapackets.registry.PacketRegistry1_8;
import de.florianmichael.betapackets.registry.PacketRegistry1_9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all {@link PacketRegistry}s
 */
public class PacketRegistryManager {

    /**
     * A map of all {@link PacketRegistry}s
     */
    private final Map<ProtocolCollection, List<PacketRegistry>> packetRegistries = new HashMap<>();

    /**
     * Creates a new {@link PacketRegistryManager} and registers all {@link PacketRegistry}s
     */
    public PacketRegistryManager() {
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryHandshake1_7());
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryStatus1_7());

        registerPacketRegistry(ProtocolCollection.R1_8, new BasePacketRegistry1_8.PacketRegistryLogin1_8());
        registerPacketRegistry(ProtocolCollection.R1_8, new PacketRegistry1_8());
        registerPacketRegistry(ProtocolCollection.R1_9, new PacketRegistry1_9());

        packetRegistries.forEach((protocolCollection, packetRegistry) -> packetRegistry.forEach(PacketRegistry::init));
    }

    /**
     * Registers a {@link PacketRegistry} for a specific version
     * @param version The version
     * @param packetRegistry The {@link PacketRegistry}
     */
    public void registerPacketRegistry(final ProtocolCollection version, final PacketRegistry packetRegistry) {
        packetRegistries.computeIfAbsent(version, k -> new ArrayList<>()).add(packetRegistry);
    }

    /**
     * Returns all {@link PacketRegistry}s for a specific version
     * @return The {@link PacketRegistry}s
     */
    public Map<ProtocolCollection, List<PacketRegistry>> getPacketRegistries() {
        return packetRegistries;
    }
}
