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

package de.florianmichael.betapackets.packet.registry;

import de.florianmichael.betapackets.base.packet.PacketRegistry;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.model.metadata.IMetadataType;
import de.florianmichael.betapackets.model.metadata.v1_8.MetadataType1_8;
import de.florianmichael.betapackets.packet.play.s2c.*;

public class PacketRegistry1_8 extends PacketRegistry {

    public PacketRegistry1_8() {
        super(NetworkState.PLAY);
    }

    @Override
    public void init() {
        // S -> C
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x00, KeepAliveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x01, JoinGameS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x02, ChatMessageS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x03, TimeUpdateS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x04, EntityEquipmentS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x05, SpawnPositionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x06, UpdateHealthS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x07, RespawnS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x08, PlayerPositionAndLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x09, HeldItemChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0A, UseBedS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0B, AnimationS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0C, SpawnPlayerS2CPacket.class);
    }

    @Override
    public IMetadataType getMetadataType() {
        return MetadataType1_8.DUMMY; // Just some dummy value
    }
}
