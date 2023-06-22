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

import java.util.Objects;

public class BlockActionS2CPacket extends Packet {

    public BlockPos blockPos;
    public byte action1;
    public byte action2;
    public int blockId;

    public BlockActionS2CPacket(final FunctionalByteBuf transformer) {
        this(
                BlockPos.fromLong(transformer.readLong()),
                transformer.readByte(),
                transformer.readByte(),
                transformer.readVarInt() & 4095
        );
    }

    public BlockActionS2CPacket(BlockPos blockPos, byte action1, byte action2, int blockId) {
        this.blockPos = blockPos;
        this.action1 = action1;
        this.action2 = action2;
        this.blockId = blockId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(blockPos.toLong());
        buf.writeByte(action1);
        buf.writeByte(action2);
        buf.writeVarInt(blockId & 4095);
    }

    @Override
    public String toString() {
        return "BlockActionS2CPacket{" +
                "blockPos=" + blockPos +
                ", action1=" + action1 +
                ", action2=" + action2 +
                ", blockId=" + blockId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockActionS2CPacket that = (BlockActionS2CPacket) o;

        if (action1 != that.action1) return false;
        if (action2 != that.action2) return false;
        if (blockId != that.blockId) return false;
        return Objects.equals(blockPos, that.blockPos);
    }

    @Override
    public int hashCode() {
        int result = blockPos != null ? blockPos.hashCode() : 0;
        result = 31 * result + (int) action1;
        result = 31 * result + (int) action2;
        result = 31 * result + blockId;
        return result;
    }
}
