package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.api.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class BetaPacketsCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private final BetaPacketsDecoder decoder;
    private final BetaPacketsEncoder encoder;

    private final UserConnection userConnection;

    public BetaPacketsCodec(UserConnection userConnection) {
        this.decoder = new BetaPacketsDecoder(userConnection);
        this.encoder = new BetaPacketsEncoder(userConnection);

        this.userConnection = userConnection;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        encoder.encode(ctx, msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        decoder.decode(ctx, msg, out);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
