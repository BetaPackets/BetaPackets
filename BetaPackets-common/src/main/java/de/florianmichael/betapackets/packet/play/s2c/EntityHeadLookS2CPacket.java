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

package de.florianmichael.betapackets.packet.play.s2c;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class EntityHeadLookS2CPacket extends Packet {

    public int entityId;
    public byte yaw;

    public EntityHeadLookS2CPacket(FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.yaw = buf.readByte();
    }

    public EntityHeadLookS2CPacket(int entityId, byte yaw) {
        this.entityId = entityId;
        this.yaw = yaw;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        buf.writeByte(this.yaw);
    }

    @Override
    public String toString() {
        return "EntityHeadLookS2CPacket{" +
                "entityId=" + entityId +
                ", yaw=" + yaw +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityHeadLookS2CPacket that = (EntityHeadLookS2CPacket) o;

        if (entityId != that.entityId) return false;
        return yaw == that.yaw;
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (int) yaw;
        return result;
    }
}