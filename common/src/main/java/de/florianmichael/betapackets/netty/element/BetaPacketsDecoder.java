package de.florianmichael.betapackets.netty.element;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class BetaPacketsDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final ByteBuf data = msg.copy();

        out.add(ctx.alloc().buffer().writeBytes(msg).retain());
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
