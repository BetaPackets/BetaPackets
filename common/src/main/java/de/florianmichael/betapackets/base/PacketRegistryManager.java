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

package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.ProtocolCollection;
import de.florianmichael.betapackets.packet.registry.BasePacketRegistry1_7;
import de.florianmichael.betapackets.packet.registry.PacketRegistry1_8;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistryManager {
    private final Map<ProtocolCollection, PacketRegistry> packetRegistries = new HashMap<>();

    public PacketRegistryManager() {
        // Handshake, Status and Login definition since v1.7.0
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryHandshake1_7());
        registerPacketRegistry(ProtocolCollection.R1_7_5, new BasePacketRegistry1_7.PacketRegistryLogin1_7());

        // Play packets since v1.7.0
        registerPacketRegistry(ProtocolCollection.R1_8, new PacketRegistry1_8());

        packetRegistries.forEach((protocolCollection, packetRegistry) -> packetRegistry.init());
    }

    public void registerPacketRegistry(final ProtocolCollection version, final PacketRegistry packetRegistry) {
        packetRegistries.put(version, packetRegistry);
    }

    public Map<ProtocolCollection, PacketRegistry> getPacketRegistries() {
        return packetRegistries;
    }
}
