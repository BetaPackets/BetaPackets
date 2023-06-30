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
import de.florianmichael.betapackets.model.base.ProtocolCollection;

public class EntityTeleportS2CPacket extends Packet {

    public int entityId;
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public boolean onGround;

    public EntityTeleportS2CPacket(FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        } else {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();
        }
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
        this.onGround = buf.readBoolean();
    }

    public EntityTeleportS2CPacket(int entityId, double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
        this.entityId = entityId;

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;

        this.onGround = onGround;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        } else {
            buf.writeInt((int) x);
            buf.writeInt((int) y);
            buf.writeInt((int) z);
        }
        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeBoolean(onGround);
    }

    @Override
    public String toString() {
        return "EntityTeleportS2CPacket{" +
                "entityId=" + entityId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTeleportS2CPacket that = (EntityTeleportS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        if (yaw != that.yaw) return false;
        if (pitch != that.pitch) return false;
        return onGround == that.onGround;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) yaw;
        result = 31 * result + (int) pitch;
        result = 31 * result + (onGround ? 1 : 0);
        return result;
    }
}
