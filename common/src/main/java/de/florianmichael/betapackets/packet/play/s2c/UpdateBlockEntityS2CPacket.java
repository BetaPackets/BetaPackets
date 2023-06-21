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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.MetadataAction;
import de.florianmichael.betapackets.model.position.BlockPos;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

public class UpdateBlockEntityS2CPacket extends Packet {

    public BlockPos blockPos;
    public MetadataAction metadata;
    public CompoundTag nbtData;

    public UpdateBlockEntityS2CPacket(final FunctionalByteBuf transformer) {
        this(
                BlockPos.fromLong(transformer.readLong()),
                MetadataAction.getById(transformer.readUnsignedByte()),
                transformer.readCompoundTag()
        );
    }

    public UpdateBlockEntityS2CPacket(BlockPos blockPos, MetadataAction metadata, CompoundTag nbtData) {
        this.blockPos = blockPos;
        this.metadata = metadata;
        this.nbtData = nbtData;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(blockPos.toLong());
        buf.writeByte(metadata.getId());
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
}
