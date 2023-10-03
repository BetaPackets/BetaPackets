/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.model.world.chunk.v1_20_1;

import org.betapackets.betapackets.model.world.chunk.ChunkFormat;
import org.betapackets.betapackets.model.world.chunk.ChunkSection;
import org.betapackets.betapackets.model.world.chunk.PacketBlockEntity;
import org.betapackets.betapackets.model.world.chunk.PacketChunk;
import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChunkFormat1_20_1 implements ChunkFormat {

    public static ChunkFormat1_20_1 INSTANCE = new ChunkFormat1_20_1();

    @Override
    public void write(PacketChunk chunk, FunctionalByteBuf buf) throws IOException {
        PacketChunk1_20_1 chunk1_20_1 = (PacketChunk1_20_1) chunk;
        buf.writeCompoundTag(chunk.getHeightmaps());
        buf.writeByteArray(chunk1_20_1.getData());
        buf.writeVarInt(chunk.getBlockEntities().size());
        for (PacketBlockEntity blockEntity : chunk.getBlockEntities()) {
            buf.writeByte(blockEntity.getPackedXZ());
            buf.writeShort(blockEntity.getY());
            buf.writeVarInt(blockEntity.getType());
            buf.writeCompoundTag(blockEntity.getData());
        }
        buf.writeBitSet(chunk.getSkyLightMask());
        buf.writeBitSet(chunk.getBlockLightMask());
        buf.writeBitSet(chunk.getEmptySkylightMask());
        buf.writeBitSet(chunk.getEmptyBlockLightMask());

        buf.writeVarInt(chunk.getSkyLightNibbles().size());
        for (byte[] skyLightNibble : chunk.getSkyLightNibbles()) {
            buf.writeByteArray(skyLightNibble);
        }

        buf.writeVarInt(chunk.getBlockLightNibbles().size());
        for (byte[] blockLightNibble : chunk.getBlockLightNibbles()) {
            buf.writeByteArray(blockLightNibble);
        }
    }

    @Override
    public PacketChunk read(FunctionalByteBuf buf) throws IOException {
        PacketChunk1_20_1 chunk = new PacketChunk1_20_1();

        chunk.setHeightmaps(buf.readCompoundTag());

        byte[] data = buf.readByteArray();
        chunk.setData(data);
        FunctionalByteBuf sectionData = new FunctionalByteBuf(Unpooled.wrappedBuffer(data), buf.getUserConnection());
        ChunkSection[] sections = new ChunkSection[16];
        try {
            for (int i = 0; i < sections.length; i++) {
                ChunkSection1_20_1 chunkSection = new ChunkSection1_20_1();
                chunkSection.read(sectionData);
                sections[i] = chunkSection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chunk.setSections(sections);
        int blockEntityAmount = buf.readVarInt();
        List<PacketBlockEntity> blockEntities = new ArrayList<>(blockEntityAmount);
        for (int i = 0; i < blockEntityAmount; i++) {
            blockEntities.add(new PacketBlockEntity(buf.readByte(), buf.readShort(), buf.readVarInt(), buf.readCompoundTag()));
        }
        chunk.setBlockEntities(blockEntities);

        chunk.setSkyLightMask(buf.readBitSet());
        chunk.setBlockLightMask(buf.readBitSet());
        chunk.setEmptySkylightMask(buf.readBitSet());
        chunk.setEmptyBlockLightMask(buf.readBitSet());

        int skylightArrays = buf.readVarInt();
        List<byte[]> skyLightNibbles = new ArrayList<>(skylightArrays);
        for (int i = 0; i < skylightArrays; i++) {
            skyLightNibbles.add(buf.readByteArray());
        }
        chunk.setSkyLightNibbles(skyLightNibbles);

        int blocklightArrays = buf.readVarInt();
        List<byte[]> blockLightNibbles = new ArrayList<>(blocklightArrays);
        for (int i = 0; i < blocklightArrays; i++) {
            blockLightNibbles.add(buf.readByteArray());
        }
        chunk.setBlockLightNibbles(blockLightNibbles);

        return chunk;
    }
}
