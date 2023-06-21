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
import de.florianmichael.betapackets.event.ServerboundPacketListener;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.model.ProtocolCollection;
import de.florianmichael.betapackets.packet.handshake.HandshakeC2SPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class BetaPacketsDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsDecoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    private HandshakeC2SPacket handleHandshake(final PacketTransformer data) {
        final HandshakeC2SPacket handshakeC2SPacket = new HandshakeC2SPacket(data);

        userConnection.init(NetworkState.HANDSHAKE, ProtocolCollection.fromProtocolId(handshakeC2SPacket.getProtocolVersion()));
        userConnection.setState(handshakeC2SPacket.getState());
        System.out.println(userConnection.getState() + " " + userConnection.getCurrentRegistry());

        return handshakeC2SPacket;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final PacketTransformer data = new PacketTransformer(msg.copy(), userConnection);
        final int packetId = data.readVarInt();

        Packet model;
        if (!userConnection.hasLoaded() && packetId == 0x00 /* C -> S, HANDSHAKE, HANDSHAKE */) {
            model = handleHandshake(data); // We need this to init the user connection and to track the next state
        } else {
            model = userConnection.getCurrentRegistry().createModel(NetworkSide.SERVERBOUND, packetId, data);
        }
        final ServerboundPacketListener.ServerboundPacketEvent<?> event = BetaPackets.getPlatform().getEventProvider().postInternal(new ServerboundPacketListener.ServerboundPacketEvent<>(
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
