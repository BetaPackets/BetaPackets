package de.florianmichael.betapackets.netty.element;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final ByteBuf data = msg.copy();

        out.add(ctx.alloc().buffer().writeBytes(msg).retain());
    }

    @Override
    public boolean isSharable() {
        return true;
    }
}
