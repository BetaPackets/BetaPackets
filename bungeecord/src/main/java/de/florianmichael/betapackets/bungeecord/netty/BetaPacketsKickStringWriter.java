package de.florianmichael.betapackets.bungeecord.netty;

import de.florianmichael.betapackets.api.UserConnection;
import io.netty.channel.ChannelHandlerContext;
import net.md_5.bungee.protocol.KickStringWriter;

public class BetaPacketsKickStringWriter extends KickStringWriter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addFirst(new BungeeCordBetaPacketsPipeline(new UserConnection(ctx.channel())));
    }
}
