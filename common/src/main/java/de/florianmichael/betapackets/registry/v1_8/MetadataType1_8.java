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

package de.florianmichael.betapackets.registry.v1_8;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.item.ItemStackV1_3;
import de.florianmichael.betapackets.base.registry.model.IMetadataType;
import de.florianmichael.betapackets.model.position.Rotations;
import de.florianmichael.betapackets.model.position.BlockPos;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum MetadataType1_8 implements IMetadataType {
    BYTE(FunctionalByteBuf::readByte, (byteBuf, value) -> byteBuf.writeByte((Integer) value)),
    SHORT(FunctionalByteBuf::readShort, (byteBuf, value) -> byteBuf.writeShort((Integer) value)),
    INT(FunctionalByteBuf::readInt, (byteBuf, value) -> byteBuf.writeInt((Integer) value)),
    FLOAT(FunctionalByteBuf::readFloat, (byteBuf, value) -> byteBuf.writeFloat((Float) value)),
    STRING(FunctionalByteBuf::readString, (byteBuf, value) -> byteBuf.writeString((String) value)),
    ITEM_STACK(FunctionalByteBuf::readItemStack, (byteBuf, value) -> byteBuf.writeItemStack((ItemStackV1_3) value)),
    BLOCK_POS(byteBuf -> new BlockPos(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt()), (byteBuf, value) -> {
        final BlockPos pos = (BlockPos) value;

        byteBuf.writeInt(pos.x);
        byteBuf.writeInt(pos.y);
        byteBuf.writeInt(pos.z);
    }),
    ROTATIONS(byteBuf -> new Rotations(byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat()), (byteBuf, value) -> {
        final Rotations rotations = (Rotations) value;

        byteBuf.writeFloat(rotations.x);
        byteBuf.writeFloat(rotations.y);
        byteBuf.writeFloat(rotations.z);
    }),

    NONE(null, null);

    public final Function<FunctionalByteBuf, Object> reader;
    public final BiConsumer<FunctionalByteBuf, Object> writer;

    MetadataType1_8(Function<FunctionalByteBuf, Object> reader, BiConsumer<FunctionalByteBuf, Object> writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Function<FunctionalByteBuf, Object> getReader() {
        return this.reader;
    }

    @Override
    public BiConsumer<FunctionalByteBuf, Object> getWriter() {
        return writer;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public IMetadataType getByIndex(int index) {
        for (MetadataType1_8 value : values()) {
            if (value.ordinal() == index) return value;
        }
        return null;
    }
}
