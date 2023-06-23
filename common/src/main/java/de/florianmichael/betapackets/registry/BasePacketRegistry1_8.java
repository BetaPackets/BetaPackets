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

package de.florianmichael.betapackets.registry;

import de.florianmichael.betapackets.base.registry.PacketRegistry;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.packet.login.c2s.EncryptionResponseC2SPacket;
import de.florianmichael.betapackets.packet.login.c2s.LoginStartC2SPacket;
import de.florianmichael.betapackets.packet.login.s2c.EncryptionRequestS2CPacket;
import de.florianmichael.betapackets.packet.login.s2c.LoginDisconnectS2CPacket;
import de.florianmichael.betapackets.packet.login.s2c.LoginSuccessS2CPacket;
import de.florianmichael.betapackets.packet.login.s2c.SetCompressionS2CPacket;

public class BasePacketRegistry1_8 {

    public static class PacketRegistryLogin1_8 extends PacketRegistry {

        public PacketRegistryLogin1_8() {
            super(NetworkState.LOGIN);
        }

        @Override
        public void init() {
            // S -> C
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x00, LoginDisconnectS2CPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x01, EncryptionRequestS2CPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x02, LoginSuccessS2CPacket.class);
            this.registerPacket(NetworkSide.CLIENTBOUND, 0x03, SetCompressionS2CPacket.class);

            // C -> S
            this.registerPacket(NetworkSide.SERVERBOUND, 0x00, LoginStartC2SPacket.class);
            this.registerPacket(NetworkSide.SERVERBOUND, 0x01, EncryptionResponseC2SPacket.class);
        }
    }
}
