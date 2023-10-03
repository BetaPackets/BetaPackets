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
import org.betapackets.betapackets.model.block.BlockState;
import org.betapackets.betapackets.model.position.BlockPos;
import org.betapackets.betapackets.model.position.ChunkSectionPos;
import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WrapperPlayServerUpdateSectionBlocks extends PacketWrapper<WrapperPlayServerUpdateSectionBlocks> {

    private ChunkSectionPos chunkSection;
    private List<Update> updates;

    public WrapperPlayServerUpdateSectionBlocks(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeChunkSectionPos(chunkSection);
        buf.writeVarInt(updates.size());
        for (Update update : updates) {
            buf.writeVarLong((long)update.getState().getId() << 12 | (long) chunkSection.getIdByPos(update.getPosition()));
        }
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        chunkSection = buf.readChunkSectionPos();
        int updateAmount = buf.readVarInt();
        updates = new ArrayList<>(updateAmount);
        for (int i = 0; i < updateAmount; i++) {
            long mask = buf.readVarLong();
            updates.add(new Update(chunkSection.getPosById((short) (mask & 0xFFFL)),
                    buf.getProtocolVersion().getBlockStateMapping().getById((int) (mask >>> 12))));
        }
    }

    @Override
    public void copyFrom(WrapperPlayServerUpdateSectionBlocks base) {
        chunkSection = base.chunkSection;
        updates = base.updates;
    }

    public void setChunkSection(ChunkSectionPos chunkSection) {
        this.chunkSection = chunkSection;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }

    public ChunkSectionPos getChunkSection() {
        return chunkSection;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerUpdateSectionBlocks{" +
                "chunkSection=" + chunkSection +
                ", updates=" + updates +
                '}';
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public static class Update {

        private BlockPos position;
        private BlockState state;

        public Update(BlockPos position, BlockState state) {
            this.position = position;
            this.state = state;
        }

        public BlockPos getPosition() {
            return position;
        }

        public void setPosition(BlockPos position) {
            this.position = position;
        }

        public BlockState getState() {
            return state;
        }

        public void setState(BlockState state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "Update{" +
                    "position=" + position +
                    ", state=" + state +
                    '}';
        }
    }

}
