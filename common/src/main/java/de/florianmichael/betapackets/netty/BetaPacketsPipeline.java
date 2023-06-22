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

import de.florianmichael.betapackets.base.api.UserConnection;
import de.florianmichael.betapackets.netty.element.BetaPacketsDecoder;
import de.florianmichael.betapackets.netty.element.BetaPacketsEncoder;
import de.florianmichael.betapackets.netty.event.ReorderPipelineEvent;
import io.netty.channel.*;

public abstract class BetaPacketsPipeline extends ChannelInboundHandlerAdapter {
    public final static String HANDLER_PACKET_DECODER_NAME = "betapackets-packet-decoder";
    public final static String HANDLER_PACKET_ENCODER_NAME = "betapackets-packet-encoder";

    private final UserConnection userConnection;

    public BetaPacketsPipeline(final UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final ChannelPipeline pipeline = ctx.channel().pipeline();

        pipeline.addBefore(getPacketDecoderName(), HANDLER_PACKET_DECODER_NAME, createBetaPacketsDecoder(userConnection));
        pipeline.addBefore(getPacketEncoderName(), HANDLER_PACKET_ENCODER_NAME, createBetaPacketsEncoder(userConnection));
    }

    public void addAutomaticallyReorderElement(final ChannelPipeline channelPipeline) {
        channelPipeline.addLast(new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (needsReorderPipeline(ctx.pipeline())) {
                    ctx.pipeline().fireUserEventTriggered(new ReorderPipelineEvent());
                }
                super.write(ctx, msg, promise);
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ReorderPipelineEvent) {
            final ReorderPipelineEvent event = (ReorderPipelineEvent) evt;
            final ChannelPipeline pipeline = ctx.channel().pipeline();

            final ChannelHandler decoder = pipeline.get(HANDLER_PACKET_DECODER_NAME);
            final ChannelHandler encoder = pipeline.get(HANDLER_PACKET_ENCODER_NAME);

            pipeline.remove(decoder);
            pipeline.remove(encoder);

            pipeline.addAfter(getPacketDecompressName(), event.targetDecoder, decoder);
            pipeline.addAfter(getPacketCompressName(), event.targetEncoder, encoder);
        }
        super.userEventTriggered(ctx, evt);
    }

    public boolean needsReorderPipeline(final ChannelPipeline pipeline) {
        final int decoderIndex = pipeline.names().indexOf(getPacketDecompressName());
        if (decoderIndex == -1) return false;

        return decoderIndex > pipeline.names().indexOf(HANDLER_PACKET_DECODER_NAME);
    }

    public abstract String getPacketDecompressName();
    public abstract String getPacketCompressName();

    public abstract String getPacketDecoderName();
    public abstract String getPacketEncoderName();

    public BetaPacketsDecoder createBetaPacketsDecoder(final UserConnection userConnection) {
        return new BetaPacketsDecoder(userConnection);
    }

    public BetaPacketsEncoder createBetaPacketsEncoder(final UserConnection userConnection) {
        return new BetaPacketsEncoder(userConnection);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
