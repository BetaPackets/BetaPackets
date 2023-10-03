/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.packet.model.c2s.handshake;

import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.base.ProtocolCollection;
import org.betapackets.betapackets.packet.NetworkState;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;
import io.netty.handler.codec.DecoderException;

import java.io.IOException;

public class WrapperHandshakingClientHandshake extends PacketWrapper<WrapperHandshakingClientHandshake> {

    private ProtocolCollection version;
    private String hostName;
    private int port;
    private NetworkState intendedState;

    public WrapperHandshakingClientHandshake(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperHandshakingClientHandshake(ProtocolCollection version, String hostName, int port, NetworkState intendedState) {
        super(PacketType.Handshaking.Client.HANDSHAKE);
        this.version = version;
        this.hostName = hostName;
        this.port = port;
        this.intendedState = intendedState;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) {
        buf.writeVarInt(version.getProtocolId());
        buf.writeString(hostName);
        buf.writeShort(port);
        buf.writeVarInt(intendedState.getId());
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) {
        int protocolId = buf.readVarInt();
        version = ProtocolCollection.fromProtocolId(protocolId);
        if (version == null) throw new DecoderException("Unknown protocol-id " + protocolId);

        hostName = buf.readString(255);
        port = buf.readShort();

        int stateId = buf.readVarInt();
        intendedState = NetworkState.fromId(version, stateId);
        if (intendedState != NetworkState.STATUS && intendedState != NetworkState.LOGIN) throw new DecoderException("Unknown state-id " + stateId + " in " + version.getProtocolName());
    }

    @Override
    public void copyFrom(WrapperHandshakingClientHandshake base) {
        version = base.version;
        hostName = base.hostName;
        port = base.port;
        intendedState = base.intendedState;
    }

    public ProtocolCollection getVersion() {
        return version;
    }

    public void setVersion(ProtocolCollection version) {
        this.version = version;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NetworkState getIntendedState() {
        return intendedState;
    }

    public void setIntendedState(NetworkState intendedState) {
        this.intendedState = intendedState;
    }

    @Override
    public String toString() {
        return "WrapperHandshakingClientHandshake{" +
                "version=" + version +
                ", hostName='" + hostName + '\'' +
                ", port=" + port +
                ", intendedState=" + intendedState +
                '}';
    }
}
