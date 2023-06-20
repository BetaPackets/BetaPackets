package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.DebugMode;
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

    private HandshakeC2SPacket handleHandshake(final FriendlyByteBuf data) {
        final HandshakeC2SPacket handshakeC2SPacket = new HandshakeC2SPacket(data);

        userConnection.init(NetworkState.HANDSHAKE, ProtocolCollection.fromProtocolId(handshakeC2SPacket.getProtocolVersion()));
        userConnection.setState(handshakeC2SPacket.getState());

        return handshakeC2SPacket;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final FriendlyByteBuf data = new FriendlyByteBuf(msg.copy());
        final int packetId = data.readVarInt();

        Packet model;
        if (!userConnection.hasLoaded() && packetId == 0x00 /* C -> S, HANDSHAKE, HANDSHAKE */) {
            model = handleHandshake(data); // We need this to init the user connection and to track the next state
        } else {
            model = BetaPackets.getPacketRegistryManager().createModel(
                    userConnection.getProtocolVersion(),
                    userConnection.getState(),
                    NetworkSide.SERVERBOUND,
                    packetId, data
            );
        }
        DebugMode.printPacket(userConnection.getState(), NetworkSide.CLIENTBOUND, model);

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
