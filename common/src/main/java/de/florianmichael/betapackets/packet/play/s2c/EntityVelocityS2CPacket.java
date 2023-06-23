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

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class EntityVelocityS2CPacket extends Packet {

    public int entityId;

    public int velocityX;
    public int velocityY;
    public int velocityZ;

    public EntityVelocityS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readVarInt(), buf.readShort(), buf.readShort(), buf.readShort());
    }

    public EntityVelocityS2CPacket(int entityId, int velocityX, int velocityY, int velocityZ) {
        this.entityId = entityId;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);

        buf.writeShort(velocityX);
        buf.writeShort(velocityY);
        buf.writeShort(velocityZ);
    }

    @Override
    public String toString() {
        return "EntityVelocityS2CPacket{" +
                "entityId=" + entityId +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityVelocityS2CPacket that = (EntityVelocityS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (velocityX != that.velocityX) return false;
        if (velocityY != that.velocityY) return false;
        return velocityZ == that.velocityZ;
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + velocityX;
        result = 31 * result + velocityY;
        result = 31 * result + velocityZ;
        return result;
    }
}
