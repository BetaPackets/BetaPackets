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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class SpawnGlobalEntityS2CPacket extends Packet {

    public int entityId;
    public int type;

    public int x;
    public int y;
    public int z;

    public SpawnGlobalEntityS2CPacket(final FunctionalByteBuf buf) {
        this(
                buf.readVarInt(),
                buf.readByte(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt()
        );
    }

    public SpawnGlobalEntityS2CPacket(int entityId, int type, int x, int y, int z) {
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
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
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
        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + type;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
