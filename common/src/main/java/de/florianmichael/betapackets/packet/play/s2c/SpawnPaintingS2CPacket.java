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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Facing;

import java.util.Objects;

public class SpawnPaintingS2CPacket extends Packet {

    public int entityId;
    public String title;
    public BlockPos pos;
    public ModelMapper<Byte, Facing> facing = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, Facing::getById);

    public SpawnPaintingS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.title = buf.readString("SkullAndRoses".length());
        this.pos = BlockPos.fromLong(buf.readLong());
        this.facing.read(buf);
    }

    public SpawnPaintingS2CPacket(int entityId, String title, BlockPos pos, Facing facing) {
        this.entityId = entityId;
        this.title = title;
        this.pos = pos;
        this.facing = new ModelMapper<>(FunctionalByteBuf::writeByte, facing);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeString(title);
        buf.writeLong(pos.toLong());
        this.facing.write(buf);
    }

    @Override
    public String toString() {
        return "SpawnPaintingS2CPacket{" +
                "entityId=" + entityId +
                ", title='" + title + '\'' +
                ", pos=" + pos +
                ", facing=" + facing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpawnPaintingS2CPacket that = (SpawnPaintingS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(pos, that.pos)) return false;
        return Objects.equals(facing, that.facing);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (facing != null ? facing.hashCode() : 0);
        return result;
    }
}
