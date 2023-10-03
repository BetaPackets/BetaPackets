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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayServerUnloadChunk extends PacketWrapper<WrapperPlayServerUnloadChunk> {

    private int chunkX;
    private int chunkZ;

    public WrapperPlayServerUnloadChunk(int chunkX, int chunkZ) {
        super(PacketType.Play.Server.UNLOAD_CHUNK);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public WrapperPlayServerUnloadChunk(PacketEvent event) throws IOException {
        super(event);
    }


    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
    }

    @Override
    public void copyFrom(WrapperPlayServerUnloadChunk base) {
        chunkX = base.chunkX;
        chunkZ = base.chunkZ;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerUnloadChunk{" +
                "chunkX=" + chunkX +
                ", chunkZ=" + chunkZ +
                '}';
    }
}
