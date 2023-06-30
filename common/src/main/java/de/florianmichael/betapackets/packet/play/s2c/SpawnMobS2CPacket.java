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
import de.florianmichael.betapackets.model.entity.metadata.Metadata;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpawnMobS2CPacket extends Packet {

    public int entityId;
    public UUID uuid_1_9;
    public ModelMapper<Byte, EntityTypes> type = new ModelMapper<>(buf -> (byte) (buf.readByte() & 255), (buf, value) -> buf.writeByte(value & 255), EntityTypes::getById);
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public byte headPitch;
    public int velocityX;
    public int velocityY;
    public int velocityZ;

    public List<Metadata> metadata;

    public SpawnMobS2CPacket(final FunctionalByteBuf buf) throws Exception {
        this.entityId = buf.readVarInt();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.uuid_1_9 = buf.readUUID();
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
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
        this.headPitch = buf.readByte();
        this.velocityX = buf.readShort();
        this.velocityY = buf.readShort();
        this.velocityZ = buf.readShort();
        this.metadata = buf.readMetadata();
    }

    // 1.8 constructor
    public SpawnMobS2CPacket(int entityId, EntityTypes type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, int velocityX, int velocityY, int velocityZ, List<Metadata> metadata) {
        this(entityId, null, type, x, y, z, yaw, pitch, headPitch, velocityX, velocityY, velocityZ, metadata);
    }

    // 1.9+ constructor
    public SpawnMobS2CPacket(int entityId, UUID uuid_1_9, EntityTypes type, double x, double y, double z, byte yaw, byte pitch, byte headPitch, int velocityX, int velocityY, int velocityZ, List<Metadata> metadata) {
        this.entityId = entityId;
        this.uuid_1_9 = uuid_1_9;
        this.type = new ModelMapper<>((buf, value) -> buf.writeByte(value & 255), type);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.metadata = metadata;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeUUID(uuid_1_9);
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
        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeByte(headPitch);
        buf.writeShort(velocityX);
        buf.writeShort(velocityY);
        buf.writeShort(velocityZ);
        buf.writeMetadata(metadata);
    }

    @Override
    public String toString() {
        return "SpawnMobS2CPacket{" +
                "entityId=" + entityId +
                ", uuid_1_9=" + uuid_1_9 +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", headPitch=" + headPitch +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                ", metadata=" + metadata +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpawnMobS2CPacket that = (SpawnMobS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (type != that.type) return false;
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        if (yaw != that.yaw) return false;
        if (pitch != that.pitch) return false;
        if (headPitch != that.headPitch) return false;
        if (velocityX != that.velocityX) return false;
        if (velocityY != that.velocityY) return false;
        if (velocityZ != that.velocityZ) return false;
        if (!Objects.equals(uuid_1_9, that.uuid_1_9)) return false;
        return Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId;
        result = 31 * result + (uuid_1_9 != null ? uuid_1_9.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) yaw;
        result = 31 * result + (int) pitch;
        result = 31 * result + (int) headPitch;
        result = 31 * result + velocityX;
        result = 31 * result + velocityY;
        result = 31 * result + velocityZ;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
}
