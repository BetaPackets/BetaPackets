package de.florianmichael.betapackets.netty;

import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.netty.element.BetaPacketsDecoder;
import de.florianmichael.betapackets.netty.element.BetaPacketsEncoder;
import de.florianmichael.betapackets.netty.event.ReorderPipelineEvent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ReorderPipelineEvent) {
            final ReorderPipelineEvent event = (ReorderPipelineEvent) evt;
            final ChannelPipeline pipeline = ctx.channel().pipeline();

            if (needsReorderPipeline(pipeline)) {
                final ChannelHandler decoder = pipeline.get(HANDLER_PACKET_DECODER_NAME);
                final ChannelHandler encoder = pipeline.get(HANDLER_PACKET_ENCODER_NAME);

                pipeline.remove(decoder);
                pipeline.remove(encoder);

                pipeline.addAfter(getPacketDecompressName(), event.targetDecoder, decoder);
                pipeline.addAfter(getPacketCompressName(), event.targetEncoder, encoder);
            }
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
