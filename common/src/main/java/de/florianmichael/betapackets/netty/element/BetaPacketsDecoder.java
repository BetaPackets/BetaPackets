package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import de.florianmichael.betapackets.model.ProtocolCollection;
import de.florianmichael.betapackets.packet.handshake.HandshakeC2SPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class BetaPacketsDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsDecoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final FriendlyByteBuf data = new FriendlyByteBuf(msg.copy());
        final int packetId = data.readVarInt();

        Packet model;
        if (!userConnection.hasLoaded() && packetId == 0x00) {
            model = new HandshakeC2SPacket(data);
            userConnection.init(NetworkState.HANDSHAKE, ProtocolCollection.fromProtocolId(((HandshakeC2SPacket) model).getProtocolVersion()));
        } else {
            model = BetaPackets.getPacketRegistryManager().createModel(
                    userConnection.getProtocolVersion(),
                    userConnection.getState(),
                    NetworkSide.SERVERBOUND,
                    packetId,
                    data
            );
        }
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
