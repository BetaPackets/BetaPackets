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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.EntityTypes;

import java.util.Objects;
import java.util.UUID;

public class SpawnObjectS2CPacket extends Packet {

    public int entityId;
    public UUID objectUUID_1_9;
    public ModelMapper<Byte, EntityTypes> type = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, EntityTypes::getById);
    public double x;
    public double y;
    public double z;
    public int pitch;
    public int yaw;
    public int data;
    public int speedX;
    public int speedY;
    public int speedZ;

    public SpawnObjectS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.objectUUID_1_9 = buf.readUUID();
        }
        this.type.read(buf);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        } else {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();
        }
        this.pitch = buf.readByte();
        this.yaw = buf.readByte();
        this.data = buf.readInt();
        if (this.data > 0) {
            this.speedX = buf.readShort();
            this.speedY = buf.readShort();
            this.speedZ = buf.readShort();
        }
    }

    public SpawnObjectS2CPacket(int entityId, EntityTypes type, int x, int y, int z, int pitch, int yaw, int data, int speedX, int speedY, int speedZ) {
        this.entityId = entityId;
        this.type = new ModelMapper<>(FunctionalByteBuf::writeByte, type);
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
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeUUID(objectUUID_1_9);
        }
        this.type.write(buf);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        } else {
            buf.writeInt((int) x);
            buf.writeInt((int) y);
            buf.writeInt((int) z);
        }
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
                ", objectUUID_1_9=" + objectUUID_1_9 +
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
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        if (pitch != that.pitch) return false;
        if (yaw != that.yaw) return false;
        if (data != that.data) return false;
        if (speedX != that.speedX) return false;
        if (speedY != that.speedY) return false;
        if (speedZ != that.speedZ) return false;
        return Objects.equals(objectUUID_1_9, that.objectUUID_1_9);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId;
        result = 31 * result + (objectUUID_1_9 != null ? objectUUID_1_9.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + pitch;
        result = 31 * result + yaw;
        result = 31 * result + data;
        result = 31 * result + speedX;
        result = 31 * result + speedY;
        result = 31 * result + speedZ;
        return result;
    }
}
