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
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.event.PacketSendEvent;
import org.betapackets.betapackets.model.base.VersionEnum;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.netty.legacybundle.LegacyBundle;
import org.betapackets.betapackets.packet.CancelPacketException;
import org.betapackets.betapackets.packet.model.s2c.play.WrapperPlayServerBundleDelimiter;
import org.betapackets.betapackets.packet.type.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This encoder encodes all outgoing packets and executes the BetaPacketsAPI in the process
 */
@ChannelHandler.Sharable
public class BetaPacketsInterceptorServer extends MessageToMessageEncoder<ByteBuf> {

    private final Inflater inflater = new Inflater();
    private final Deflater deflater = new Deflater();
    private final byte[] deflateBuffer = new byte[8192];

    private final UserConnection userConnection;

    public BetaPacketsInterceptorServer(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Throwable lastCause = cause;
        while (lastCause != null) {
            if (lastCause instanceof CancelPacketException) return; // Cancel packets if cause stack contains CancelPacketException
            lastCause = lastCause.getCause();
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final boolean couldBeCompressed = userConnection.getPipeline().isCompressed(ctx.pipeline()); // Check if compression is enabled
        boolean compressed = false;

        ByteBuf messageToRead = msg;
        // 1. Detect if packet is really compressed and decompress it
        //  -> Uncompressed when compressed: we are in front of ViaVersion but behind the compress handler. ViaVersion reorders, we reorder.
        //  -> Happens after CompressionThreshold + LoginSuccess
        if (couldBeCompressed) {
            FunctionalByteBuf buf = new FunctionalByteBuf(messageToRead, userConnection);
            int uncompressedSize = buf.readVarInt();
            if (uncompressedSize != 0) {
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

        // 2. read packet id
        final FunctionalByteBuf readBuffer = new FunctionalByteBuf(messageToRead, userConnection);
        int packetId = readBuffer.readVarInt();

        // 3. validate packet id
        final List<Packet> packets = userConnection.getS2CPackets();
        if (packetId >= packets.size()) {
            throw new EncoderException("Unknown packet-id " + packetId);
        }

        // 4. call internal event and check if packet is cancelled
        final PacketSendEvent event = BetaPackets.getAPI().fireWriteEvent(
                new PacketSendEvent(packets.get(packetId), readBuffer, userConnection)
        );
        if (event.isCancelled()) {
            msg.clear();
            throw CancelPacketException.INSTANCE;
        }

        // 5. check if we have a bundle and send it
        if (event.getBundle().size() > 1) {
            final FunctionalByteBuf[] bundle = new FunctionalByteBuf[event.getBundle().size()];
            for (int i = 0; i < event.getBundle().size(); i++) {
                final PacketEvent packetEvent = event.getBundle().get(i);
                ByteBuf outBuf = ctx.alloc().buffer();

                packetId = packets.indexOf(packetEvent.getType());
                if (packetId == -1) { // should never happen
                    throw new EncoderException("Unregistered packet-type " + packetEvent.getType());
                }

                // write packet data into buffer and add it to the bundle array
                FunctionalByteBuf writeBuffer = new FunctionalByteBuf(outBuf, userConnection);
                writeBuffer.writeVarInt(packetId);
                if (packetEvent == event && packetEvent.getLastPacketWrapper() == null) {
                    writeBuffer.writeBytes(messageToRead);
                } else if (packetEvent.getLastPacketWrapper() != null) {
                    packetEvent.getLastPacketWrapper().write(packetEvent.getType(), writeBuffer);
                } else {
                    throw new IllegalArgumentException("Cannot add PacketEvent without wrapper");
                }
                bundle[i] = writeBuffer;
            }

            // send bundle:
            // 1.19.4+: send bundle delimiter + bundle + bundle delimiter
            // 1.19.4-: send legacy bundle (if supported) or send all packets individually (if not supported)
            if (userConnection.getProtocolVersion().isNewerThanOrEqualTo(VersionEnum.R1_19_4)) {
                ctx.writeAndFlush(userConnection.getPacket(WrapperPlayServerBundleDelimiter.INSTANCE));
                for (FunctionalByteBuf byteBuf : bundle) {
                    ctx.writeAndFlush(byteBuf);
                }
                ctx.writeAndFlush(userConnection.getPacket(WrapperPlayServerBundleDelimiter.INSTANCE));
            } else if (userConnection.getPipeline().isLegacyBundleSupported()) {
                ctx.writeAndFlush(new LegacyBundle(bundle));
            } else {
                // send all packets individually if we don't have legacy bundle support
                for (FunctionalByteBuf buf : bundle) {
                    ctx.writeAndFlush(buf);
                }
            }

            // cancel the original packet
            msg.clear();
            throw CancelPacketException.INSTANCE;
        }

        // 6. replace packet id with the event data if the packet was edited
        packetId = packets.indexOf(event.getType());
        if (packetId == -1) {
            throw new EncoderException("Unregistered packet-type " + event.getType());
        }

        // 7. write packet to the output
        ByteBuf outBuf = ctx.alloc().buffer();
        FunctionalByteBuf writeBuffer = new FunctionalByteBuf(outBuf, userConnection);
        writeBuffer.writeVarInt(packetId);

        if (event.getLastPacketWrapper() != null) { // packet was edited
            event.getLastPacketWrapper().write(event.getType(), writeBuffer);
        } else { // pass-through
            writeBuffer.writeBytes(messageToRead);
        }

        // 8. compress packet if compression is enabled
        if (couldBeCompressed) {
            final FunctionalByteBuf buf = new FunctionalByteBuf(Unpooled.buffer(), userConnection);
            if (compressed) {
                // compressed
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
                // uncompressed if original packet was uncompressed
                buf.writeVarInt(0);
                buf.writeBytes(outBuf);
            }
            out.add(buf.getBuffer());
        } else {
            // Pass-through output buffer/data if compression is disabled
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
