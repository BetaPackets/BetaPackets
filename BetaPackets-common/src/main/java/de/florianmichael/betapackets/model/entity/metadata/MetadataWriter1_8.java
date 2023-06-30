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

package de.florianmichael.betapackets.model.entity.metadata;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.world.item.ItemStackV1_3;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Rotations;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MetadataWriter1_8 {

    public static class ByteMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readByte;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeByte((Byte) o);
        }
    }

    public static class ShortMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readShort;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeShort((Short) o);
        }
    }

    public static class IntMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readInt;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeInt((Integer) o);
        }
    }

    public static class FloatMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readFloat;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeFloat((Float) o);
        }
    }

    public static class StringMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readString;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeString((String) o);
        }
    }

    public static class ItemStackMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() throws Exception {
            return buf -> {
                try {
                    return buf.readItemStack();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() throws Exception {
            return (buf, o) -> {
                try {
                    buf.writeItemStack((ItemStackV1_3) o);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }
    }

    public static class BlockPosMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return buf -> {
                final int x = buf.readInt();
                final int y = buf.readInt();
                final int z = buf.readInt();

                return new BlockPos(x, y, z);
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> {
                final BlockPos blockPos = (BlockPos) o;

                buf.writeInt(blockPos.x);
                buf.writeInt(blockPos.y);
                buf.writeInt(blockPos.z);
            };
        }
    }

    public static class RotationsMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return buf -> {
                final float x = buf.readFloat();
                final float y = buf.readFloat();
                final float z = buf.readFloat();

                return new Rotations(x, y, z);
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> {
                final Rotations rotations = (Rotations) o;

                buf.writeFloat(rotations.x);
                buf.writeFloat(rotations.y);
                buf.writeFloat(rotations.z);
            };
        }
    }
}
