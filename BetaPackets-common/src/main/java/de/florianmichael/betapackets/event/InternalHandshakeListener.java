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

package de.florianmichael.betapackets.event;

import de.florianmichael.betapackets.BetaPacketsPlatform;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.packet.NetworkState;
import de.florianmichael.betapackets.packet.model.c2s.handshake.WrapperHandshakingClientHandshake;
import de.florianmichael.betapackets.packet.model.s2c.login.WrapperLoginServerLoginSuccess;
import de.florianmichael.betapackets.packet.model.s2c.login.WrapperLoginServerSetCompression;
import de.florianmichael.betapackets.packet.type.PacketType;
import io.netty.handler.codec.DecoderException;

import java.io.IOException;

public class InternalHandshakeListener extends PacketListener {

    public InternalHandshakeListener(BetaPacketsPlatform<?> platform) {
        super(platform.getPlugin(),
                PacketType.Handshaking.Client.HANDSHAKE,
                PacketType.Handshaking.Client.LEGACY_STATUS,
                PacketType.Login.Server.LOGIN_SUCCESS,
                PacketType.Play.Server.JOIN_GAME
        );
    }

    @Override
    public void onWrite(PacketEvent event) throws IOException {
        if (event.getType() == PacketType.Login.Server.LOGIN_SUCCESS) {
            WrapperLoginServerLoginSuccess loginSuccess = new WrapperLoginServerLoginSuccess(event);
            event.getConnection().setUuid(loginSuccess.getProfile().uuid);
            event.getConnection().setState(NetworkState.PLAY);
        } else if (event.getType() == PacketType.Login.Server.SET_COMPRESSION) {
            WrapperLoginServerSetCompression setCompression = new WrapperLoginServerSetCompression(event);
            event.getConnection().setCompressionThreshold(setCompression.getThreshold());
        } else if (event.getType() == PacketType.Play.Server.JOIN_GAME) {
            if (event.getConnection().getProtocolVersion().isOlderThan(ProtocolCollection.R1_19_4)) {
                event.getConnection().getPipeline().addLegacyBundlerSupport();
            }
        }
    }

    @Override
    public void onRead(PacketEvent event) throws IOException {
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
