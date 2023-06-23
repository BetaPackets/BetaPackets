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

package de.florianmichael.betapackets.packet.handshake;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.NetworkState;

import java.util.Objects;

public class HandshakeC2SPacket extends Packet {
    private final int protocolVersion;
    private final String address;
    private final short port;
    private final NetworkState state;

    public HandshakeC2SPacket(final FunctionalByteBuf buf) {
        this(
                buf.readVarInt(),
                buf.readString(255),
                buf.readShort(),
                NetworkState.fromId(buf.readVarInt())
        );
    }

    public HandshakeC2SPacket(int protocolVersion, String address, short port, NetworkState state) {
        this.protocolVersion = protocolVersion;
        this.address = address;
        this.port = port;
        this.state = state;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeVarInt(protocolVersion);
        buf.writeString(address);
        buf.writeShort(port);
        buf.writeVarInt(state.getId());
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public short getPort() {
        return port;
    }

    public NetworkState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "HandshakeC2SPacket{" +
                "protocolVersion=" + protocolVersion +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandshakeC2SPacket that = (HandshakeC2SPacket) o;

        if (protocolVersion != that.protocolVersion) return false;
        if (port != that.port) return false;
        if (!Objects.equals(address, that.address)) return false;
        return state == that.state;
    }

    @Override
    public int hashCode() {
        int result = protocolVersion;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) port;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
