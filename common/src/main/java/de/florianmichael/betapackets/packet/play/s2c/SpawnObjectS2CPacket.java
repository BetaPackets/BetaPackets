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
import de.florianmichael.betapackets.base.Packet;

public class SpawnObjectS2CPacket extends Packet {

    public int entityId;
    public int type;

    public int x;
    public int y;
    public int z;

    public int pitch;
    public int yaw;

    public int data;

    public int speedX;
    public int speedY;
    public int speedZ;

    public SpawnObjectS2CPacket(final FunctionalByteBuf transformer) {
        this.entityId = transformer.readVarInt();
        this.type = transformer.readByte();

        this.x = transformer.readInt();
        this.y = transformer.readInt();
        this.z = transformer.readInt();

        this.pitch = transformer.readByte();
        this.yaw = transformer.readByte();

        this.data = transformer.readInt();
        if (this.data > 0) {
            this.speedX = transformer.readShort();
            this.speedY = transformer.readShort();
            this.speedZ = transformer.readShort();
        }
    }

    public SpawnObjectS2CPacket(int entityId, int type, int x, int y, int z, int pitch, int yaw, int data, int speedX, int speedY, int speedZ) {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.data = data;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeByte(type);

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeByte(pitch);
        buf.writeByte(yaw);

        buf.writeInt(data);

        if (data > 0) {
            buf.writeShort(speedX);
            buf.writeShort(speedY);
            buf.writeShort(speedZ);
        }
    }

    @Override
    public String toString() {
        return "SpawnObjectS2CPacket{" +
                "entityId=" + entityId +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", data=" + data +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", speedZ=" + speedZ +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpawnObjectS2CPacket that = (SpawnObjectS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (type != that.type) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        if (pitch != that.pitch) return false;
        if (yaw != that.yaw) return false;
        if (data != that.data) return false;
        if (speedX != that.speedX) return false;
        if (speedY != that.speedY) return false;
        return speedZ == that.speedZ;
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + type;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + pitch;
        result = 31 * result + yaw;
        result = 31 * result + data;
        result = 31 * result + speedX;
        result = 31 * result + speedY;
        result = 31 * result + speedZ;
        return result;
    }
}
