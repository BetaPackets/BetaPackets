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
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Facing;

public class SpawnPaintingS2CPacket extends Packet {

    public int entityId;
    public String title;
    public BlockPos pos;
    public Facing facing;

    public SpawnPaintingS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readVarInt(),
                transformer.readString("SkullAndRoses".length()),
                BlockPos.fromLong(transformer.readLong()),
                Facing.byId(transformer.readByte())
        );
    }

    public SpawnPaintingS2CPacket(int entityId, String title, BlockPos pos, Facing facing) {
        this.entityId = entityId;
        this.title = title;
        this.pos = pos;
        this.facing = facing;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeString(title);
        buf.writeLong(pos.toLong());
        buf.writeByte(facing.ordinal());
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
}
