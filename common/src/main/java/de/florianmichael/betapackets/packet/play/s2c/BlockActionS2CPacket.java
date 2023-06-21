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

import de.florianmichael.betapackets.base.PacketTransformer;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.world.BlockPos;

public class BlockActionS2CPacket extends Packet {

    public BlockPos blockPos;
    public byte action1;
    public byte action2;
    public int blockId;

    public BlockActionS2CPacket(final PacketTransformer transformer) {
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
    public void write(PacketTransformer buf) throws Exception {
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
}
