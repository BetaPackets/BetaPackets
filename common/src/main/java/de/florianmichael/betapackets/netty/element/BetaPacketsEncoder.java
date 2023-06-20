package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkSide;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsEncoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final FriendlyByteBuf data = new FriendlyByteBuf(msg.copy());
        final int packetId = data.readVarInt();

        final Packet model = BetaPackets.getPacketRegistryManager().createModel(
                    userConnection.getProtocolVersion(),
                    userConnection.getState(),
                    NetworkSide.SERVERBOUND,
                    packetId,
                    data);

        if (model != null) {
            System.out.println(model.toString());
        }
        out.add(ctx.alloc().buffer().writeBytes(msg).retain());
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
