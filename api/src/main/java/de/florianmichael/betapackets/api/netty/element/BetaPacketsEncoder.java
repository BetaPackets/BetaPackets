/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.betapackets.api.netty.element;

import de.florianmichael.betapackets.api.BetaPackets;
import de.florianmichael.betapackets.base.UserConnection;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.api.event.ClientboundPacketListener;
import de.florianmichael.betapackets.api.event.PlayerEarlyJoinListener;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.packet.login.s2c.LoginSuccessS2CPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * This encoder encodes all outgoing packets and executes the BetaPacketsAPI in the process
 */
public class BetaPacketsEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection userConnection;

    public BetaPacketsEncoder(UserConnection userConnection) {
        this.userConnection = userConnection;
    }

    /**
     * This method is called when the login success packet is sent, and it updates the user connection and fires the PlayerEarlyJoinEvent
     * @param data The data of the packet
     * @return The packet model
     */
    private LoginSuccessS2CPacket handleLoginSuccess(final FunctionalByteBuf data) {
        final LoginSuccessS2CPacket loginSuccessS2CPacket = new LoginSuccessS2CPacket(data);

        userConnection.setState(NetworkState.PLAY);
        userConnection.setPlayer(loginSuccessS2CPacket.uuid);
        BetaPackets.getAPI().getEventProvider().postInternal(new PlayerEarlyJoinListener.PlayerEarlyJoinEvent(
                loginSuccessS2CPacket.uuid,
                loginSuccessS2CPacket.username,
                data.getProtocolVersion()
        ));

        return loginSuccessS2CPacket;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        final FunctionalByteBuf data = new FunctionalByteBuf(msg, userConnection);
        try {
            final int packetId = data.readVarInt();

            final Packet model;
            if (userConnection.getState() == NetworkState.LOGIN && packetId == 0x02 /* S -> C, LOGIN_SUCCESS, LOGIN */) {
                model = handleLoginSuccess(data);
            } else {
                model = userConnection.getCurrentRegistry().createModel(NetworkSide.CLIENTBOUND, packetId, data);
            }
            final ClientboundPacketListener.ClientboundPacketEvent<?> event = BetaPackets.getAPI().getEventProvider().postInternal(new ClientboundPacketListener.ClientboundPacketEvent<>(
                    userConnection,
                    userConnection.getState(),
                    model,
                    BetaPackets.getPlatform().getPlayer(userConnection.getPlayer())
            ));
            if (event.isCancelled()) {
                return;
            }

            final FunctionalByteBuf output = new FunctionalByteBuf(ctx.alloc().buffer(), userConnection);
            output.writeVarInt(packetId);
            model.write(output);
            out.add(output.getBuffer().retain());
            BetaPackets.getPlatform().getLogging().info("CLIENTBOUND -> " + userConnection.getState() + ": " + model);
        } catch (Exception e) {
            // In case reading failed
            ctx.fireExceptionCaught(e);
            BetaPackets.getPlatform().getLogging().severe("Error while reading packet from " + userConnection.getPlayer() + " (CLIENTBOUND)");
            e.printStackTrace();
            return;
        }
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    @Override
    public boolean isSharable() {
        return this.userConnection != null;
    }
}
