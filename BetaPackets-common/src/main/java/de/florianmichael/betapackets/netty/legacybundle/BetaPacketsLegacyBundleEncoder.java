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

package de.florianmichael.betapackets.netty.legacybundle;

import de.florianmichael.betapackets.connection.UserConnection;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;

public class BetaPacketsLegacyBundleEncoder extends MessageToByteEncoder<LegacyBundle> {

    private UserConnection connection;
    private final byte[] deflateBuffer = new byte[8192];
    private final Deflater deflater = new Deflater();

    public BetaPacketsLegacyBundleEncoder(UserConnection connection) {
        this.connection = connection;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LegacyBundle msg, ByteBuf out) throws Exception {
        // 1. compression
        for (FunctionalByteBuf packet : msg.getPackets()) {
            byte[] content = new byte[packet.readableBytes()];
            packet.readBytes(content);
            if (content.length < connection.getCompressionThreshold()) {
                packet.writeVarInt(0);
                packet.writeBytes(content);
            } else {
                packet.writeVarInt(content.length);
                deflater.setInput(content);
                deflater.finish();
                while (!deflater.finished()) {
                    int index = deflater.deflate(deflateBuffer);
                    packet.writeBytes(deflateBuffer, 0, index);
                }
                deflater.reset();
            }
        }

        // 2. frame
        for (FunctionalByteBuf packet : msg.getPackets()) {
            FunctionalByteBuf functionalByteBuf = new FunctionalByteBuf(out, connection);
            functionalByteBuf.writeVarInt(packet.readableBytes());
            functionalByteBuf.writeBytes(packet.getBuffer());
        }
    }
}
