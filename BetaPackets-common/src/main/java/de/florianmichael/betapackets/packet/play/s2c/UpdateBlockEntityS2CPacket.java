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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.position.BlockPos;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.io.IOException;
import java.util.Objects;

public class UpdateBlockEntityS2CPacket extends Packet {
    @Override
    public void write(FunctionalByteBuf buf) throws Exception {

    }

    /*public BlockPos blockPos;
    public ModelMapper<Short, MetadataAction> metadata = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, MetadataAction::getById);
    public CompoundTag nbtData;

    public UpdateBlockEntityS2CPacket(final FunctionalByteBuf buf) throws IOException {
        this.blockPos = BlockPos.fromLong(buf.readLong());
        this.metadata.read(buf);
        this.nbtData = buf.readCompoundTag();
    }

    public UpdateBlockEntityS2CPacket(BlockPos blockPos, MetadataAction metadata, CompoundTag nbtData) {
        this.blockPos = blockPos;
        this.metadata = new ModelMapper<>(FunctionalByteBuf::writeByte, metadata);
        this.nbtData = nbtData;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(blockPos.toLong());
        metadata.write(buf);
        buf.writeCompoundTag(nbtData);
    }

    @Override
    public String toString() {
        return "UpdateBlockEntityS2CPacket{" +
                "blockPos=" + blockPos +
                ", metadata=" + metadata +
                ", nbtData=" + nbtData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateBlockEntityS2CPacket that = (UpdateBlockEntityS2CPacket) o;

        if (!Objects.equals(blockPos, that.blockPos)) return false;
        if (!Objects.equals(metadata, that.metadata)) return false;
        return Objects.equals(nbtData, that.nbtData);
    }

    @Override
    public int hashCode() {
        int result = blockPos != null ? blockPos.hashCode() : 0;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (nbtData != null ? nbtData.hashCode() : 0);
        return result;
    }*/
}
