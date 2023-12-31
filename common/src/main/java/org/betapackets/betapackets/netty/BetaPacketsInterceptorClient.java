/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.netty;

import org.betapackets.betapackets.BetaPackets;
import org.betapackets.betapackets.connection.UserConnection;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.packet.CancelPacketException;
import org.betapackets.betapackets.packet.type.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * This decoder decodes all incoming packets and executes the BetaPacketsAPI in the process
 */
@ChannelHandler.Sharable
public class BetaPacketsInterceptorClient extends MessageToMessageDecoder<ByteBuf> {

    /**
     * The user connection of the player
     */
    private final UserConnection userConnection;

    public BetaPacketsInterceptorClient(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 1. read packet id
        final FunctionalByteBuf readBuffer = new FunctionalByteBuf(msg, userConnection);
        int packetId = readBuffer.readVarInt();

        // 2. validate packet id
        List<Packet> packets = userConnection.getC2SPackets();
        if (packetId >= packets.size()) {
            throw new DecoderException("Unknown packet-id " + packetId);
        }

        // 3. call internal event and check if packet is cancelled
        final PacketEvent event = BetaPackets.getAPI().fireReadEvent(
                new PacketEvent(packets.get(packetId), readBuffer, userConnection)
        );
        if (event.isCancelled()) {
            msg.clear();
            throw CancelPacketException.INSTANCE;
        }

        // 4. replace packet id with the event data if the packet was edited
        packetId = packets.indexOf(event.getType());
        if (packetId == -1) {
            throw new DecoderException("Unregistered packet-type " + event.getType());
        }

        // 5. write packet to the output
        final ByteBuf outBuf = ctx.alloc().buffer();
        final FunctionalByteBuf writeBuffer = new FunctionalByteBuf(outBuf, userConnection);
        writeBuffer.writeVarInt(packetId);

        if (event.getLastPacketWrapper() != null) { // packet was edited
            event.getLastPacketWrapper().write(event.getType(), writeBuffer);
        } else { // pass-through
            writeBuffer.writeBytes(msg);
        }
        out.add(outBuf);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
