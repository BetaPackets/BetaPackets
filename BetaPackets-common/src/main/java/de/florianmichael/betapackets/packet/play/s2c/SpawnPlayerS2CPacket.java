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
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.metadata.Metadata;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpawnPlayerS2CPacket extends Packet {

    public int entityId;
    public UUID playerId;
    public double x;
    public double y;
    public double z;
    public byte yaw;
    public byte pitch;
    public int currentItem;

    public List<Metadata> metadata;

    public SpawnPlayerS2CPacket(final FunctionalByteBuf buf) throws Exception {
        this.entityId = buf.readVarInt();
        this.playerId = buf.readUUID();
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
        this.currentItem = buf.readShort();
        this.metadata = buf.readMetadata();
    }

    public SpawnPlayerS2CPacket(int entityId, UUID playerId, double x, double y, double z, byte yaw, byte pitch, int currentItem, List<Metadata> metadata) {
        this.entityId = entityId;
        this.playerId = playerId;

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;

        this.currentItem = currentItem;

        this.metadata = metadata;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);

        buf.writeUUID(playerId);

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

        buf.writeShort(currentItem);

        buf.writeMetadata(metadata);
    }

    @Override
    public String toString() {
        return "SpawnPlayerS2CPacket{" +
                "entityId=" + entityId +
                ", playerId=" + playerId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", currentItem=" + currentItem +
                ", metadata=" + metadata +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpawnPlayerS2CPacket that = (SpawnPlayerS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        if (yaw != that.yaw) return false;
        if (pitch != that.pitch) return false;
        if (currentItem != that.currentItem) return false;
        if (!Objects.equals(playerId, that.playerId)) return false;
        return Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId;
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) yaw;
        result = 31 * result + (int) pitch;
        result = 31 * result + currentItem;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
}
