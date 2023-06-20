package de.florianmichael.betapackets.netty;

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

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final ChannelPipeline pipeline = ctx.channel().pipeline();

        pipeline.addBefore(getPacketDecoderName(), HANDLER_PACKET_DECODER_NAME, createBetaPacketsDecoder());
        pipeline.addBefore(getPacketEncoderName(), HANDLER_PACKET_ENCODER_NAME, createBetaPacketsEncoder());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ReorderPipelineEvent) {
            final ReorderPipelineEvent event = (ReorderPipelineEvent) evt;
            final int decoderIndex = ctx.pipeline().names().indexOf(getPacketDecompressName());
            if (decoderIndex == -1) return;

            if (decoderIndex > ctx.pipeline().names().indexOf(HANDLER_PACKET_DECODER_NAME)) {
                final ChannelHandler decoder = ctx.pipeline().get(HANDLER_PACKET_DECODER_NAME);
                final ChannelHandler encoder = ctx.pipeline().get(HANDLER_PACKET_ENCODER_NAME);

                ctx.pipeline().remove(decoder);
                ctx.pipeline().remove(encoder);

                ctx.pipeline().addAfter(getPacketDecompressName(), event.targetDecoder, decoder);
                ctx.pipeline().addAfter(getPacketCompressName(), event.targetEncoder, encoder);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    public abstract String getPacketDecompressName();
    public abstract String getPacketCompressName();

    public abstract String getPacketDecoderName();
    public abstract String getPacketEncoderName();

    public BetaPacketsDecoder createBetaPacketsDecoder() {
        return new BetaPacketsDecoder();
    }

    public BetaPacketsEncoder createBetaPacketsEncoder() {
        return new BetaPacketsEncoder();
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
