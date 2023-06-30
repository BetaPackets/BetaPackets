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

package de.florianmichael.betapackets.packet.play.c2s.v1_9;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class TeleportConfirmC2SPacket extends Packet {

    public int teleportId;

    public TeleportConfirmC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readVarInt());
    }

    public TeleportConfirmC2SPacket(int teleportId) {
        this.teleportId = teleportId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.teleportId);
    }

    @Override
    public String toString() {
        return "TeleportConfirmC2SPacket{" +
                "teleportId=" + teleportId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeleportConfirmC2SPacket that = (TeleportConfirmC2SPacket) o;

        return teleportId == that.teleportId;
    }

    @Override
    public int hashCode() {
        return teleportId;
    }
}
