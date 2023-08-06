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

package de.florianmichael.betapackets.event;

import de.florianmichael.betapackets.connection.UserConnection;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

public class PacketEvent {

    private Packet type;
    private FunctionalByteBuf byteBuf;
    private PacketWrapper<?> lastPacketWrapper;
    private UserConnection connection;

    public FunctionalByteBuf getByteBuf() {
        return byteBuf;
    }

    public PacketEvent(Packet type, FunctionalByteBuf byteBuf, UserConnection connection) {
        this.type = type;
        this.byteBuf = byteBuf;
        this.connection = connection;
    }

    public UserConnection getConnection() {
        return connection;
    }

    public PacketWrapper<?> getLastPacketWrapper() {
        return lastPacketWrapper;
    }

    public Packet getType() {
        return type;
    }

    public void setLastPacketWrapper(PacketWrapper<?> lastPacketWrapper) {
        this.lastPacketWrapper = lastPacketWrapper;
    }
}
