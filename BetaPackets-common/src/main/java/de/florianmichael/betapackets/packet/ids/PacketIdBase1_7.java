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

package de.florianmichael.betapackets.packet.ids;

import de.florianmichael.betapackets.packet.NetworkSide;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.type.PacketType;

public class PacketIdBase1_7 extends PacketIdList {

    public static final PacketIdBase1_7 INSTANCE = new PacketIdBase1_7();

    public PacketIdBase1_7() {
        // Handshake
        register(NetworkSide.C2S, NetworkState.HANDSHAKE, 0x00, PacketType.Handshaking.Client.HANDSHAKE);

        // Status
        // s -> c
        register(NetworkSide.S2C, NetworkState.STATUS, 0x00, PacketType.Status.Server.STATUS_RESPONSE);
        register(NetworkSide.S2C, NetworkState.STATUS, 0x01, PacketType.Status.Server.PING_RESPONSE);

        // c -> s
        register(NetworkSide.C2S, NetworkState.STATUS, 0x00, PacketType.Status.Client.STATUS_REQUEST);
        register(NetworkSide.C2S, NetworkState.STATUS, 0x01, PacketType.Status.Client.PING_REQUEST);

        // Login
        // s -> c
        register(NetworkSide.S2C, NetworkState.LOGIN, 0x00, PacketType.Login.Server.DISCONNECT);
        register(NetworkSide.S2C, NetworkState.LOGIN, 0x01, PacketType.Login.Server.ENCRYPTION_REQUEST);
        register(NetworkSide.S2C, NetworkState.LOGIN, 0x02, PacketType.Login.Server.LOGIN_SUCCESS);
        register(NetworkSide.S2C, NetworkState.LOGIN, 0x03, PacketType.Login.Server.SET_COMPRESSION);
        register(NetworkSide.S2C, NetworkState.LOGIN, 0x04, PacketType.Login.Server.PLUGIN_REQUEST);

        // c -> s
        register(NetworkSide.C2S, NetworkState.LOGIN, 0x00, PacketType.Login.Client.LOGIN_START);
        register(NetworkSide.C2S, NetworkState.LOGIN, 0x01, PacketType.Login.Client.ENCRYPTION_RESPONSE);
        register(NetworkSide.C2S, NetworkState.LOGIN, 0x02, PacketType.Login.Client.PLUGIN_RESPONSE);
    }
}
