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
import de.florianmichael.betapackets.BetaPacketsAPI;
import de.florianmichael.betapackets.connection.UserConnection;
import io.netty.channel.*;

/**
 * This class bundles the whole Netty pipeline of BetaPackets into one HandlerAdapter
 * <p>
 * Intended implementation: This handler should always be the last one in the pipeline.
 */
public abstract class BetaPacketsPipeline extends ChannelInboundHandlerAdapter {

    /**
     * The name of the decoder and encoder handlers
     */
    public final static String HANDLER_PACKET_DECODER_NAME = "betapackets-packet-decoder";
    public final static String HANDLER_PACKET_ENCODER_NAME = "betapackets-packet-encoder";
    public final static String HANDLER_AUTO_REORDER_NAME = "betapackets-auto-reorder";

    private final UserConnection userConnection;

    /**
     * Creates a new BetaPacketsPipeline
     * @param userConnection The connection
     */
    public BetaPacketsPipeline(final UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    /**
     * Adds the BetaPacketsDecoder and BetaPacketsEncoder to the pipeline
     * @param ctx The ChannelHandlerContext
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final ChannelPipeline pipeline = ctx.channel().pipeline();

        pipeline.addBefore(getPacketDecoderName(), HANDLER_PACKET_DECODER_NAME, createBetaPacketsDecoder(userConnection));
        pipeline.addBefore(getPacketEncoderName(), HANDLER_PACKET_ENCODER_NAME, createBetaPacketsEncoder(userConnection));

        BetaPackets.getAPI().getConnections().addConnection(userConnection);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        BetaPackets.getAPI().getConnections().removeConnection(userConnection);
    }

    /**
     * In case the implementation doesn't have an event when it reorders the compression, or you can't access it, you
     * can use this method to add a dummy handler which will automatically reorder the pipeline when it has to
     * @param channelPipeline The ChannelPipeline
     */
    public void addAutomaticallyReorderElement(final ChannelPipeline channelPipeline) {
        channelPipeline.addLast(HANDLER_AUTO_REORDER_NAME, new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (needsReorderPipeline(ctx.pipeline())) {
                    ctx.pipeline().fireUserEventTriggered(new ReorderPipelineEvent());
                }
                super.write(ctx, msg, promise);
            }
        });
    }

    /**
     * Handles the {@link ReorderPipelineEvent} and reorders the pipeline
     */
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

    /**
     * Checks if the pipeline needs to be reordered
     * @param pipeline The ChannelPipeline
     * @return True if the pipeline needs to be reordered
     */
    public boolean needsReorderPipeline(final ChannelPipeline pipeline) {
        final int decompressIndex = pipeline.names().indexOf(getPacketDecompressName());
        if (decompressIndex == -1) return false;

        // betapackets has to be the first transformer after compression
        return decompressIndex + 1 != pipeline.names().indexOf(HANDLER_PACKET_DECODER_NAME);
    }

    /**
     * These methods are used to get the names of the handlers which are used to reorder the pipeline
     */

    public abstract String getPacketDecompressName();
    public abstract String getPacketCompressName();

    public abstract String getPacketDecoderName();
    public abstract String getPacketEncoderName();

    /**
     * These methods can be used in case the implementation want's to overwrite the original handlers
     */

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
        return this.userConnection != null;
    }
}
