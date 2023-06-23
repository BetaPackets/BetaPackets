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

public class SpawnGlobalEntityS2CPacket extends Packet {

    public int entityId;
    public int type;
    public double x;
    public double y;
    public double z;

    public SpawnGlobalEntityS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.type = buf.readByte();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        } else {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();
        }
    }

    public SpawnGlobalEntityS2CPacket(int entityId, int type, double x, double y, double z) {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeByte(type);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        } else {
            buf.writeInt((int) x);
            buf.writeInt((int) y);
            buf.writeInt((int) z);
        }
    }

    @Override
    public String toString() {
        return "SpawnGlobalEntityS2CPacket{" +
                "entityId=" + entityId +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpawnGlobalEntityS2CPacket that = (SpawnGlobalEntityS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (type != that.type) return false;
        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        return Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entityId;
        result = 31 * result + type;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
