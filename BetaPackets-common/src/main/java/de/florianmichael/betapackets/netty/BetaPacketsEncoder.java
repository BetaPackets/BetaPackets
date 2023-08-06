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

package de.florianmichael.betapackets.netty;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.connection.UserConnection;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.type.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This encoder encodes all outgoing packets and executes the BetaPacketsAPI in the process
 */
public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection userConnection;
    private final String compress;
    private final byte[] deflateBuffer = new byte[8192];
    private final Inflater inflater = new Inflater();
    private final Deflater deflater = new Deflater();

    public BetaPacketsEncoder(String compress, UserConnection userConnection) {
        this.userConnection = userConnection;
        this.compress = compress;
    }

    private boolean isCompressed(ChannelPipeline pipeline) {
        return pipeline.names().indexOf(compress) > pipeline.names().indexOf(BetaPacketsPipeline.HANDLER_PACKET_ENCODER_NAME);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        boolean couldBeCompressed = isCompressed(ctx.pipeline());
        boolean compressed = false;

        ByteBuf messageToRead = msg;
        if (couldBeCompressed) {
            FunctionalByteBuf buf = new FunctionalByteBuf(messageToRead, userConnection);
            int uncompressedSize = buf.readVarInt();
            if (uncompressedSize == 0) {
            } else {
                byte[] bytes = new byte[messageToRead.readableBytes()];
                messageToRead.readBytes(bytes);
                inflater.setInput(bytes);
                byte[] uncompressed = new byte[uncompressedSize];
                inflater.inflate(uncompressed);
                messageToRead = Unpooled.wrappedBuffer(uncompressed);
                inflater.reset();
                compressed = true;
            }
        }

        FunctionalByteBuf readBuffer = new FunctionalByteBuf(messageToRead, userConnection);
        int packetId = readBuffer.readVarInt();

        List<Packet> packets = userConnection.getS2CPackets();
        if (packetId >= packets.size())
            throw new EncoderException("Unknown packet-id " + packetId);

        PacketEvent event = new PacketEvent(packets.get(packetId), readBuffer, userConnection);
        BetaPackets.getAPI().fireWriteEvent(event);

        packetId = packets.indexOf(event.getType());
        if (packetId == -1)
            throw new EncoderException("Unregistered packet-type " + event.getType());

        ByteBuf outBuf = ctx.alloc().buffer();
        FunctionalByteBuf writeBuffer = new FunctionalByteBuf(outBuf, userConnection);
        writeBuffer.writeVarInt(packetId);

        if (event.getLastPacketWrapper() != null) { // packet was edited
            event.getLastPacketWrapper().write(event.getType(), writeBuffer);
        } else { // pass-through
            writeBuffer.writeBytes(messageToRead);
        }

        if (couldBeCompressed) {
            FunctionalByteBuf buf = new FunctionalByteBuf(Unpooled.buffer(), userConnection);
            if (compressed) {
                buf.writeVarInt(outBuf.readableBytes());
                byte[] bytes = new byte[outBuf.readableBytes()];
                outBuf.readBytes(bytes);
                deflater.setInput(bytes);
                deflater.finish();
                while (!deflater.finished()) {
                    int index = deflater.deflate(deflateBuffer);
                    buf.writeBytes(deflateBuffer, 0, index);
                }
                deflater.reset();
            } else {
                buf.writeVarInt(0);
                buf.writeBytes(outBuf);
            }
            out.add(buf.getBuffer());
        } else {
            out.add(outBuf);
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
