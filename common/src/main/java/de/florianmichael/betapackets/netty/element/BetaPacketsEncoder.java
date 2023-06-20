package de.florianmichael.betapackets.netty.element;

import de.florianmichael.betapackets.BetaPackets;
import de.florianmichael.betapackets.DebugMode;
import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.base.FriendlyByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.NetworkSide;
import de.florianmichael.betapackets.model.NetworkState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsEncoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    private void handleLoginSuccess() {
        if (userConnection.getState() == NetworkState.LOGIN) {
            userConnection.setState(NetworkState.PLAY);
        }
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final FriendlyByteBuf data = new FriendlyByteBuf(msg.copy());
        final int packetId = data.readVarInt();
        if (packetId == 0x02 /* S -> C, LOGIN_SUCCESS, LOGIN */) {
            handleLoginSuccess();
        }

        final Packet model = BetaPackets.getPacketRegistryManager().createModel(
                userConnection.getProtocolVersion(),
                userConnection.getState(),
                NetworkSide.CLIENTBOUND,
                packetId,
                data
        );
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
