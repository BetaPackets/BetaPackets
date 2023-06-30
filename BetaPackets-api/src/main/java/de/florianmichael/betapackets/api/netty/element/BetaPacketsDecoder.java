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

package de.florianmichael.betapackets.api.netty.element;

import de.florianmichael.betapackets.api.BetaPackets;
import de.florianmichael.betapackets.base.UserConnection;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.api.event.ServerboundPacketListener;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.packet.handshake.HandshakeC2SPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * This decoder decodes all incoming packets and executes the BetaPacketsAPI in the process
 */
public class BetaPacketsDecoder extends MessageToMessageDecoder<ByteBuf> {

    /**
     * The user connection of the player
     */
    private final UserConnection userConnection;

    public BetaPacketsDecoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    /**
     * This method is called when the handshake packet is received and initializes the user connection
     * @param data The data of the packet
     * @return The packet model
     */
    private HandshakeC2SPacket handleHandshake(final FunctionalByteBuf data) {
        final HandshakeC2SPacket handshakeC2SPacket = new HandshakeC2SPacket(data);

        userConnection.init(NetworkState.HANDSHAKE, ProtocolCollection.fromProtocolId(handshakeC2SPacket.getProtocolVersion()));
        userConnection.setState(handshakeC2SPacket.getState());

        return handshakeC2SPacket;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final FunctionalByteBuf data = new FunctionalByteBuf(msg, userConnection);
        try {
            final int packetId = data.readVarInt();

            if (packetId == 0x17) {
                out.add(ctx.alloc().buffer().writeBytes(msg).retain());
                return;
            }

            Packet model;
            if (!userConnection.hasLoaded() && packetId == 0x00 /* C -> S, HANDSHAKE, HANDSHAKE */) {
                model = handleHandshake(data); // We need this to init the user connection and to track the next state
            } else {
                model = userConnection.getCurrentRegistry().createModel(NetworkSide.SERVERBOUND, packetId, data);
            }
            final var event = new ServerboundPacketListener.ServerboundPacketEvent<>(
                    userConnection,
                    userConnection.getState(),
                    model,
                    BetaPackets.getPlatform().getPlayer(userConnection.getPlayer())
            );

            BetaPackets.getAPI().getEventProvider().postInternal(ServerboundPacketListener.ServerboundPacketEvent.ID, event);
            if (event.isCancelled()) {
                return;
            }
            BetaPackets.getPlatform().getLogging().info("SERVERBOUND -> " + userConnection.getState() + ": " + model);

            final FunctionalByteBuf output = new FunctionalByteBuf(ctx.alloc().buffer(), userConnection);
            output.writeVarInt(packetId);
            model.write(output);
            out.add(output.getBuffer().retain());
        } catch (Exception e) {
            // In case reading failed
            ctx.fireExceptionCaught(e);
            BetaPackets.getPlatform().getLogging().severe("Error while reading packet from " + userConnection.getPlayer() + " (SERVERBOUND)");
            e.printStackTrace();
            return;
        }
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
