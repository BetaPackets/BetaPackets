package de.florianmichael.betapackets.netty.element;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class BetaPacketsCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private final BetaPacketsDecoder decoder = new BetaPacketsDecoder();
    private final BetaPacketsEncoder encoder = new BetaPacketsEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        encoder.encode(ctx, msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        decoder.decode(ctx, msg, out);
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
