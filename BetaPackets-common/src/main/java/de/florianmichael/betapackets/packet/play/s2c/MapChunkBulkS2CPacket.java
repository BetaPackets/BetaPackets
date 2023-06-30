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
import de.florianmichael.betapackets.model.world.chunk.Chunk;
import de.florianmichael.betapackets.model.world.chunk.ChunkData;

import java.util.Arrays;

public class MapChunkBulkS2CPacket extends Packet {

    public boolean isOverWorld;
    public int[] xPositions;
    public int[] zPositions;
    public ChunkData[] chunksData;

    // Model data
    public Chunk[] chunks;

    public MapChunkBulkS2CPacket(final FunctionalByteBuf buf) {
        this.isOverWorld = buf.readBoolean();
        final int chunkCount = buf.readVarInt();

        this.xPositions = new int[chunkCount];
        this.zPositions = new int[chunkCount];
        this.chunksData = new ChunkData[chunkCount];

        this.chunks = new Chunk[chunkCount];

        for (int i = 0; i < chunkCount; ++i) {
            this.xPositions[i] = buf.readInt();
            this.zPositions[i] = buf.readInt();

            final int dataSize = buf.readShort() & 65535;
            this.chunksData[i] = new ChunkData(dataSize, new byte[Chunk.calculateStackSize(Integer.bitCount(dataSize), this.isOverWorld, true)]);
        }

        for (int i = 0; i < chunkCount; ++i) {
            buf.readBytes(this.chunksData[i].data);

            this.chunks[i] = new Chunk(this.xPositions[i], this.zPositions[i]);
            this.chunks[i].fillDepth(this.chunksData[i], this.isOverWorld, true);
        }
    }

    public MapChunkBulkS2CPacket(int[] xPositions, int[] zPositions, ChunkData[] chunksData, boolean isOverWorld) {
        this.xPositions = xPositions;
        this.zPositions = zPositions;
        this.chunksData = chunksData;
        this.isOverWorld = isOverWorld;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeBoolean(this.isOverWorld);
        buf.writeVarInt(this.chunksData.length);

        for (int i = 0; i < this.chunksData.length; ++i) {
            buf.writeInt(this.xPositions[i]);
            buf.writeInt(this.zPositions[i]);

            this.chunksData[i] = this.chunks[i].writeToData();
            buf.writeShort((short) (this.chunksData[i].dataSize & 65535));
        }

        for (ChunkData chunksDatum : this.chunksData) {
            buf.writeBytes(chunksDatum.data);
        }
    }

    @Override
    public String toString() {
        return "MapChunkBulkS2CPacket{" +
                "isOverWorld=" + isOverWorld +
                ", xPositions=" + Arrays.toString(xPositions) +
                ", zPositions=" + Arrays.toString(zPositions) +
                ", chunksData=" + Arrays.toString(chunksData) +
                ", chunks=" + Arrays.toString(chunks) +
                '}';
    }
}
