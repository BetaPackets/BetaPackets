package de.florianmichael.betapackets.bungeecord.netty;

import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.netty.BetaPacketsPipeline;
import de.florianmichael.betapackets.netty.event.ReorderPipelineEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class BungeeCordBetaPacketsPipeline extends BetaPacketsPipeline {

    public BungeeCordBetaPacketsPipeline(UserConnection userConnection) {
        super(userConnection);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        ctx.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (needsReorderPipeline(ctx.pipeline())) {
                    System.out.println("NEEDS REORDER");
                    ctx.pipeline().fireUserEventTriggered(new ReorderPipelineEvent());
                }
                super.write(ctx, msg, promise);
            }
        });
    }

    @Override
    public String getPacketDecompressName() {
        return "decompress";
    }

    @Override
    public String getPacketCompressName() {
        return "compress";
    }

    @Override
    public String getPacketDecoderName() {
        return "packet-decoder";
    }

    @Override
    public String getPacketEncoderName() {
        return "packet-encoder";
    }
}
