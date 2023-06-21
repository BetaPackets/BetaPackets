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

public class BlockChangeS2CPacket extends Packet {

    public BlockPos blockPos;
    public int blockId;

    public BlockChangeS2CPacket(final FunctionalByteBuf transformer) {
        this(
                BlockPos.fromLong(transformer.readLong()),
                transformer.readVarInt()
        );
    }

    public BlockChangeS2CPacket(BlockPos blockPos, int blockId) {
        this.blockPos = blockPos;
        this.blockId = blockId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(blockPos.toLong());
        buf.writeVarInt(blockId);
    }

    @Override
    public String toString() {
        return "BlockChangeS2CPacket{" +
                "blockPos=" + blockPos +
                ", blockId=" + blockId +
                '}';
    }
}
