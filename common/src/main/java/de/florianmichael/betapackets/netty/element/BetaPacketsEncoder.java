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

package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.DebugMode;
import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.base.PacketTransformer;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.event.ClientboundPacketListener;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.packet.login.s2c.LoginSuccessS2CPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsEncoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    private LoginSuccessS2CPacket handleLoginSuccess(final PacketTransformer data) {
        final LoginSuccessS2CPacket loginSuccessS2CPacket = new LoginSuccessS2CPacket(data);

        userConnection.setState(NetworkState.PLAY);
        userConnection.setPlayer(loginSuccessS2CPacket.uuid);

        return loginSuccessS2CPacket;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final PacketTransformer data = new PacketTransformer(msg.copy(), userConnection);
        final int packetId = data.readVarInt();

        final Packet model;
        if (userConnection.getState() == NetworkState.LOGIN && packetId == 0x02 /* S -> C, LOGIN_SUCCESS, LOGIN */) {
            model = handleLoginSuccess(data);
        } else {
            model = userConnection.getCurrentRegistry().createModel(NetworkSide.CLIENTBOUND, packetId, data);
        }
        final ClientboundPacketListener.ClientboundPacketEvent<?> event = BetaPackets.getPlatform().getEventProvider().postInternal(new ClientboundPacketListener.ClientboundPacketEvent<>(
                userConnection,
                userConnection.getState(),
                model,
                BetaPackets.getPlatform().getAPIBase().get(userConnection.getPlayer())
        ));
        if (event.isCancelled()) {
            return;
        }
        DebugMode.printPacket(userConnection.getState(), NetworkSide.CLIENTBOUND, model);

        out.add(ctx.alloc().buffer().writeBytes(msg).retain());
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
