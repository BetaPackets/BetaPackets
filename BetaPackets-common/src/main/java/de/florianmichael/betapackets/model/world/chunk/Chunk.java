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

package de.florianmichael.betapackets.model.world.chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Chunk {

    public final ExtendedBlockStorage[] sections = new ExtendedBlockStorage[16];
    public final byte[] blockBiomeArray = new byte[256];

    public int x;
    public int y;

    public ChunkData data;
    public boolean hasSkyLight;
    public boolean fullChunk;

    public Chunk(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int pushReaderIndex(final byte[] origin, final int readerIndex, final NibbleArray nibbleArray) {
        System.arraycopy(origin, readerIndex, nibbleArray.data, 0, nibbleArray.data.length);
        return nibbleArray.data.length;
    }

    public void fillDepth(final ChunkData data, final boolean hasSkyLight, final boolean fullChunk) {
        this.data = new ChunkData(data.dataSize, data.data);
        this.hasSkyLight = hasSkyLight;
        this.fullChunk = fullChunk;

        int readerIndex = 0;
        for (int i = 0; i < this.sections.length; ++i) {
            if ((data.dataSize & 1 << i) != 0) {
                if (this.sections[i] == null) {
                    this.sections[i] = new ExtendedBlockStorage(i << 4, hasSkyLight);
                }
                char[] blockStates = this.sections[i].blockStates;
                for (int j = 0; j < blockStates.length; ++j) {
                    blockStates[j] = (char) ((data.data[readerIndex + 1] & 255) << 8 | data.data[readerIndex] & 255);
                    readerIndex += 2;
                }
            } else if (fullChunk && this.sections[i] != null) {
                this.sections[i] = null;
            }
        }

        for (int i = 0; i < this.sections.length; ++i) {
            if ((data.dataSize & 1 << i) != 0 && this.sections[i] != null) {
                final NibbleArray blockLight = this.sections[i].blockLightArray;

                readerIndex += pushReaderIndex(data.data, readerIndex, blockLight);
            }
        }

        if (hasSkyLight) {
            for (int i = 0; i < this.sections.length; ++i) {
                if ((data.dataSize & 1 << i) != 0 && this.sections[i] != null) {
                    final NibbleArray skyLight = this.sections[i].skyLightArray;

                    readerIndex += pushReaderIndex(data.data, readerIndex, skyLight);
                }
            }
        }

        if (fullChunk) {
            System.arraycopy(data.data, readerIndex, this.blockBiomeArray, 0, this.blockBiomeArray.length);
        }
    }

    public static int calculateStackSize(final int dataSizeBits, final boolean hasSkyLight, final boolean fullChunk) {
        final int baseX = dataSizeBits * 2 * 16 * 16 * 16;
        final int baseZ = dataSizeBits * 16 * 16 * 16 / 2;
        final int skyLight = hasSkyLight ? dataSizeBits * 16 * 16 * 16 / 2 : 0;
        final int chunkLayerCount = fullChunk ? 256 : 0;

        return baseX + baseZ + skyLight + chunkLayerCount;
    }

    public int pushWriterIndex(final byte[] origin, final byte[] target, final int size) {
        System.arraycopy(origin, 0, target, size, origin.length);
        return size + origin.length;
    }

    public ChunkData writeToData() {
        final List<ExtendedBlockStorage> workingSections = new ArrayList<>();
        final ChunkData goal = new ChunkData();

        for (int i = 0; i < this.sections.length; ++i) {
            final ExtendedBlockStorage section = this.sections[i];
            if (section != null && (this.data.dataSize & 1 << i) != 0) {
                goal.dataSize |= 1 << i;
                workingSections.add(section);
            }
        }

        goal.data = new byte[calculateStackSize(Integer.bitCount(goal.dataSize), hasSkyLight, fullChunk)];
        int writerIndex = 0;

        for (ExtendedBlockStorage section : workingSections) {
            // Block States
            char[] blockStates = section.blockStates;

            for (char blockState : blockStates) {
                goal.data[writerIndex++] = (byte) (blockState & 255);
                goal.data[writerIndex++] = (byte) (blockState >> 8 & 255);
            }
        }

        for (ExtendedBlockStorage section : workingSections) {
            // Block Light
            writerIndex = pushWriterIndex(section.blockLightArray.data, goal.data, writerIndex);
        }

        if (hasSkyLight) {
            for (ExtendedBlockStorage section : workingSections) {
                // Skylight
                writerIndex = pushWriterIndex(section.skyLightArray.data, goal.data, writerIndex);
            }
        }

        if (fullChunk) {
            pushWriterIndex(this.blockBiomeArray, goal.data, writerIndex);
        }

        return goal;
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "sections=" + Arrays.toString(sections) +
                ", blockBiomeArray=" + Arrays.toString(blockBiomeArray) +
                ", x=" + x +
                ", y=" + y +
                ", data=" + data +
                ", hasSkyLight=" + hasSkyLight +
                ", fullChunk=" + fullChunk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chunk chunk = (Chunk) o;

        if (x != chunk.x) return false;
        if (y != chunk.y) return false;
        if (hasSkyLight != chunk.hasSkyLight) return false;
        if (fullChunk != chunk.fullChunk) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(sections, chunk.sections)) return false;
        if (!Arrays.equals(blockBiomeArray, chunk.blockBiomeArray)) return false;
        return Objects.equals(data, chunk.data);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(sections);
        result = 31 * result + Arrays.hashCode(blockBiomeArray);
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (hasSkyLight ? 1 : 0);
        result = 31 * result + (fullChunk ? 1 : 0);
        return result;
    }
}
