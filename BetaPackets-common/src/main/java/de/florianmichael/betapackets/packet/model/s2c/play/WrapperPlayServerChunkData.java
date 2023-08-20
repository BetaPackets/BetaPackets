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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.model.world.chunk.PacketChunk;
import de.florianmichael.betapackets.model.world.chunk.v1_20_1.ChunkFormat1_20_1;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;

public class WrapperPlayServerChunkData extends PacketWrapper<WrapperPlayServerChunkData> {

    private int chunkX;
    private int chunkZ;

    private PacketChunk packetChunk;

    public WrapperPlayServerChunkData(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        ChunkFormat1_20_1.INSTANCE.write(packetChunk, buf);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
        packetChunk = ChunkFormat1_20_1.INSTANCE.read(buf);
    }

    @Override
    public void copyFrom(WrapperPlayServerChunkData base) {
        chunkX = base.chunkX;
        chunkZ = base.chunkZ;
        packetChunk = base.packetChunk;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public PacketChunk getPacketChunk() {
        return packetChunk;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerChunkData{" +
                "chunkX=" + chunkX +
                ", chunkZ=" + chunkZ +
                ", packetChunk=" + packetChunk +
                '}';
    }
}
