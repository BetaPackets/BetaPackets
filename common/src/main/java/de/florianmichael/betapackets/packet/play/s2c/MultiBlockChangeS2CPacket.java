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
import de.florianmichael.betapackets.model.position.BlockChunkRecord1_8;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockChangeS2CPacket extends Packet {

    public int chunkX;
    public int chunkZ;
    public List<BlockChunkRecord1_8> blockChunkRecords;

    public MultiBlockChangeS2CPacket(final FunctionalByteBuf transformer) {
        blockChunkRecords = new ArrayList<>();

        this.chunkX = transformer.readInt();
        this.chunkZ = transformer.readInt();

        final int recordCount = transformer.readVarInt();
        for (int i = 0; i < recordCount; i++) {
            blockChunkRecords.add(new BlockChunkRecord1_8(transformer.readShort(), transformer.readVarInt()));
        }
    }

    public MultiBlockChangeS2CPacket(int chunkX, int chunkZ, List<BlockChunkRecord1_8> blockChunkRecords) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blockChunkRecords = blockChunkRecords;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);

        buf.writeVarInt(blockChunkRecords.size());
        for (BlockChunkRecord1_8 blockChunkRecord : blockChunkRecords) {
            buf.writeShort(blockChunkRecord.chunkPosCrammed);
            buf.writeVarInt(blockChunkRecord.blockState);
        }
    }

    @Override
    public String toString() {
        return "MultiBlockChangeS2CPacket{" +
                "chunkX=" + chunkX +
                ", chunkZ=" + chunkZ +
                ", blockChunkRecords=" + blockChunkRecords +
                '}';
    }
}
