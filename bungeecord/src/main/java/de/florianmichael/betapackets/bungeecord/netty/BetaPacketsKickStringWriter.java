package de.florianmichael.betapackets.bungeecord.netty;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.api.UserConnection;
import io.netty.channel.ChannelHandlerContext;
import net.md_5.bungee.protocol.KickStringWriter;

public class BetaPacketsKickStringWriter extends KickStringWriter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addFirst(new BungeeCordBetaPacketsPipeline(new UserConnection(ctx.channel())));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        BetaPackets.getTrackingConnections().disconnect(ctx.channel());
    }
}
