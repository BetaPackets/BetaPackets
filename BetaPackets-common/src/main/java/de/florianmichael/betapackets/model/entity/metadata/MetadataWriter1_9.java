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
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Facing;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MetadataWriter1_9 {

    public static class VarIntMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readVarInt;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeVarInt((Integer) o);
        }
    }

    public static class ChatMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readComponent;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeComponent((ATextComponent) o);
        }
    }

    public static class BooleanMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return FunctionalByteBuf::readBoolean;
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> buf.writeBoolean((Boolean) o);
        }
    }

    public static class OptionalBlockPosMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return buf -> {
                if (buf.readBoolean()) {
                    return Optional.of(BlockPos.fromLong(buf.readLong()));
                } else {
                    return Optional.empty();
                }
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> {
                final Optional<BlockPos> pos = (Optional<BlockPos>) o;
                if (pos.isPresent()) {
                    buf.writeBoolean(false);
                } else {
                    buf.writeBoolean(true);
                    buf.writeLong(pos.get().toLong());
                }
            };
        }
    }

    /**
     * Since rewriters are not allowed to delete data, we have to do a little cursed code here:
     * We read the raw data and see if we can convert it into a model, in case that works we write the model into an Object,
     * otherwise we write the raw data, when writing we then see if the Object has raw data or if it has a model.
     * <p>
     * If you use this system, make sure that ANY metadata that is a model in the Minecraft source CAN be a model here, but not MUST.
     * In case it is NOT a model, it is always the raw type it has on network level.
     */

    public static class FacingMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return buf -> {
                final byte raw = (byte) buf.readVarInt();
                final Facing facing = Facing.getById(buf.getProtocolVersion(), raw);

                if (facing == null) return raw;
                return facing;
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, value) -> {
                if (value instanceof Facing) {
                    buf.writeVarInt(((Facing) value).ordinal());
                } else {
                    buf.writeVarInt((byte) value);
                }
            };
        }
    }

    public static class OptionalUUIDMetadataWriter implements IMetadataWriter {

        @Override
        public Function<FunctionalByteBuf, Object> getReader() {
            return buf -> {
                if (buf.readBoolean()) {
                    return Optional.of(buf.readUUID());
                } else {
                    return Optional.empty();
                }
            };
        }

        @Override
        public BiConsumer<FunctionalByteBuf, Object> getWriter() {
            return (buf, o) -> {
                final Optional<UUID> uuid = (Optional<UUID>) o;
                if (uuid.isPresent()) {
                    buf.writeBoolean(false);
                } else {
                    buf.writeBoolean(true);
                    buf.writeUUID(uuid.get());
                }
            };
        }
    }
}
