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

package de.florianmichael.betapackets.api.base;

import de.florianmichael.betapackets.api.PacketListener;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.model.handshake.WrapperHandshakingClientHandshake;
import de.florianmichael.betapackets.packet.type.PacketType;
import io.netty.handler.codec.DecoderException;

public class InternalHandshakeListener extends PacketListener {

    public InternalHandshakeListener(BetaPacketsPlatform<?> platform) {
        super(platform.getPlugin(),
                PacketType.Handshaking.Client.HANDSHAKE,
                PacketType.Handshaking.Client.LEGACY_STATUS,
                PacketType.Login.Server.LOGIN_SUCCESS
        );
    }

    @Override
    public void onWrite(PacketEvent event) {
        if (event.getType() == PacketType.Login.Server.LOGIN_SUCCESS) {
            event.getConnection().setState(NetworkState.PLAY);
        }
    }

    @Override
    public void onRead(PacketEvent event) {
        if (event.getType() == PacketType.Handshaking.Client.HANDSHAKE) {
            WrapperHandshakingClientHandshake handshake = new WrapperHandshakingClientHandshake(event);
            if (!event.getConnection().hasLoaded()) {
                if (handshake.getVersion().getPacketIdList() == null) {
                    throw new DecoderException("Unimplemented version: " + handshake.getVersion().getProtocolName());
                }
                event.getConnection().init(handshake.getIntendedState(), handshake.getVersion());
            } else {
                throw new DecoderException("Cannot handshake twice");
            }
        }
    }
}
