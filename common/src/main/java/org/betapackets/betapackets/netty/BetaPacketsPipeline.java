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
import org.betapackets.betapackets.netty.event.ReorderPipelineEvent;
import org.betapackets.betapackets.netty.legacybundle.BetaPacketsLegacyBundleEncoder;
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
    public final static String HANDLER_INTERCEPTOR_CLIENT_NAME = "betapackets-interceptor-client";
    public final static String HANDLER_INTERCEPTOR_SERVER_NAME = "betapackets-interceptor-server";
    public final static String HANDLER_LEGACY_BUNDLER_NAME = "betapackets-legacy-bundler";
    public final static String HANDLER_AUTO_REORDER_NAME = "betapackets-auto-reorder";

    private final UserConnection userConnection;

    private boolean legacyBundleSupported;

    /**
     * Creates a new BetaPacketsPipeline
     *
     * @param userConnection The connection
     */
    public BetaPacketsPipeline(final UserConnection userConnection) {
        this.userConnection = userConnection;
        userConnection.setPipeline(this);
    }

    /**
     * Adds the BetaPacketsDecoder and BetaPacketsEncoder to the pipeline
     *
     * @param ctx The ChannelHandlerContext
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final ChannelPipeline pipeline = ctx.channel().pipeline();
        addHandlers(pipeline, createBetaPacketsInterceptorClient(userConnection), createBetaPacketsInterceptorServer(userConnection));
        BetaPackets.getAPI().getConnections().addConnection(userConnection);
    }

    public void addLegacyBundlerSupport() {
        final ChannelPipeline pipeline = userConnection.getChannel().pipeline();

        if (pipeline.get(HANDLER_LEGACY_BUNDLER_NAME) == null) {
            pipeline.addBefore(getFrameEncoderName(), HANDLER_LEGACY_BUNDLER_NAME, createLegacyBundleEncoder(userConnection));
            legacyBundleSupported = true;
        }
    }

    public boolean isLegacyBundleSupported() {
        return legacyBundleSupported;
    }

    /**
     * Adds the BetaPacketsInterceptorClient and BetaPacketsInterceptorServer to the pipeline in the correct order
     *
     * @param pipeline The ChannelPipeline instance
     * @param interceptorClient The BetaPacketsInterceptorClient instance
     * @param interceptorServer The BetaPacketsInterceptorServer instance
     */
    private void addHandlers(ChannelPipeline pipeline, ChannelHandler interceptorClient, ChannelHandler interceptorServer) {
        pipeline.addBefore(getPacketDecoderName(), HANDLER_INTERCEPTOR_CLIENT_NAME, interceptorClient);
        pipeline.addBefore(getPacketEncoderName(), HANDLER_INTERCEPTOR_SERVER_NAME, interceptorServer);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        BetaPackets.getAPI().getConnections().removeConnection(userConnection);
    }

    /**
     * In case the implementation doesn't have an event when it reorders the compression, or you can't access it, you
     * can use this method to add a dummy handler which will automatically reorder the pipeline when it has to
     *
     * @param channelPipeline The ChannelPipeline
     */
    public void addAutomaticallyReorderElement(final ChannelPipeline channelPipeline) {
        channelPipeline.addLast(HANDLER_AUTO_REORDER_NAME, new ChannelOutboundHandlerAdapter() {

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (needsReorderPipeline(ctx.pipeline())) {
                    ctx.pipeline().fireUserEventTriggered(new ReorderPipelineEvent());
                    ctx.pipeline().write(msg);
                    return;
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
            final ChannelPipeline pipeline = ctx.channel().pipeline();

            final ChannelHandler interceptorClient = pipeline.get(HANDLER_INTERCEPTOR_CLIENT_NAME);
            final ChannelHandler interceptorServer = pipeline.get(HANDLER_INTERCEPTOR_SERVER_NAME);

            pipeline.remove(interceptorClient);
            pipeline.remove(interceptorServer);

            addHandlers(pipeline, interceptorClient, interceptorServer);
            pipeline.remove(HANDLER_AUTO_REORDER_NAME); // Remove the dummy handler
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * Checks if the pipeline needs to be reordered
     *
     * @param pipeline The ChannelPipeline
     * @return True if the pipeline needs to be reordered
     */
    public boolean needsReorderPipeline(final ChannelPipeline pipeline) {
        return pipeline.names().indexOf(getPacketEncoderName()) - 1 != pipeline.names().indexOf(HANDLER_INTERCEPTOR_SERVER_NAME);
    }

    /**
     * These methods are used to get the names of the handlers which are used to reorder the pipeline
     */

    public abstract String getPacketCompressName();
    public abstract String getFrameEncoderName();

    public abstract String getPacketDecoderName();
    public abstract String getPacketEncoderName();

    /**
     * These methods can be used in case the implementation want's to overwrite the original handlers
     */

    public BetaPacketsInterceptorClient createBetaPacketsInterceptorClient(final UserConnection userConnection) {
        return new BetaPacketsInterceptorClient(userConnection);
    }

    public BetaPacketsInterceptorServer createBetaPacketsInterceptorServer(final UserConnection userConnection) {
        return new BetaPacketsInterceptorServer(getPacketCompressName(), userConnection);
    }

    public BetaPacketsLegacyBundleEncoder createLegacyBundleEncoder(final UserConnection userConnection) {
        return new BetaPacketsLegacyBundleEncoder(userConnection);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
