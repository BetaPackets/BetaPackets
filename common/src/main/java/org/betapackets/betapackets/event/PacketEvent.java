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

package org.betapackets.betapackets.event;

import org.betapackets.betapackets.connection.UserConnection;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;

public class PacketEvent {

    private final Packet type;
    private final FunctionalByteBuf byteBuf;
    private PacketWrapper<?> lastPacketWrapper;
    private final UserConnection connection;
    private final Object player;

    private boolean cancelled;
    private boolean abort;

    public FunctionalByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    public boolean isAbort() {
        return abort;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public PacketEvent(Packet type, FunctionalByteBuf byteBuf, UserConnection connection) {
        this.type = type;
        this.byteBuf = byteBuf;
        this.connection = connection;
        this.player = connection.getPlayer();
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

    public Object getPlayer() {
        return player;
    }

    public void setLastPacketWrapper(PacketWrapper<?> lastPacketWrapper) {
        this.lastPacketWrapper = lastPacketWrapper;
    }
}
