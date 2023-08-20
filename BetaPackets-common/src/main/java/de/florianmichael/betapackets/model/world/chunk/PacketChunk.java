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

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class PacketChunk {

    private CompoundTag heightmaps;

    private List<PacketBlockEntity> blockEntities;
    private ChunkSection[] sections = new ChunkSection[256 >> 4];
    private BitSet skyLightMask;
    private BitSet blockLightMask;
    private BitSet emptySkylightMask;
    private BitSet emptyBlockLightMask;
    private List<byte[]> skyLightNibbles;
    private List<byte[]> blockLightNibbles;

    public List<byte[]> getSkyLightNibbles() {
        return skyLightNibbles;
    }

    public ChunkSection[] getSections() {
        return sections;
    }

    public void setSections(ChunkSection[] sections) {
        this.sections = sections;
    }

    public void setSkyLightNibbles(List<byte[]> skyLightNibbles) {
        this.skyLightNibbles = skyLightNibbles;
    }

    public List<byte[]> getBlockLightNibbles() {
        return blockLightNibbles;
    }

    public void setBlockLightNibbles(List<byte[]> blockLightNibbles) {
        this.blockLightNibbles = blockLightNibbles;
    }

    public BitSet getSkyLightMask() {
        return skyLightMask;
    }

    public void setSkyLightMask(BitSet skyLightMask) {
        this.skyLightMask = skyLightMask;
    }

    public BitSet getBlockLightMask() {
        return blockLightMask;
    }

    public void setBlockLightMask(BitSet blockLightMask) {
        this.blockLightMask = blockLightMask;
    }

    public BitSet getEmptySkylightMask() {
        return emptySkylightMask;
    }

    public void setEmptySkylightMask(BitSet emptySkylightMask) {
        this.emptySkylightMask = emptySkylightMask;
    }

    public BitSet getEmptyBlockLightMask() {
        return emptyBlockLightMask;
    }

    public void setEmptyBlockLightMask(BitSet emptyBlockLightMask) {
        this.emptyBlockLightMask = emptyBlockLightMask;
    }

    public void setBlockEntities(List<PacketBlockEntity> blockEntities) {
        this.blockEntities = blockEntities;
    }

    public List<PacketBlockEntity> getBlockEntities() {
        return blockEntities;
    }

    public void setHeightmaps(CompoundTag heightmaps) {
        this.heightmaps = heightmaps;
    }

    public CompoundTag getHeightmaps() {
        return heightmaps;
    }

    @Override
    public String toString() {
        return "PacketChunk{" +
                "heightmaps=" + heightmaps +
                ", blockEntities=" + blockEntities +
                ", sections=" + Arrays.toString(sections) +
                ", skyLightMask=" + skyLightMask +
                ", blockLightMask=" + blockLightMask +
                ", emptySkylightMask=" + emptySkylightMask +
                ", emptyBlockLightMask=" + emptyBlockLightMask +
                ", skyLightNibbles=" + skyLightNibbles +
                ", blockLightNibbles=" + blockLightNibbles +
                '}';
    }
}
