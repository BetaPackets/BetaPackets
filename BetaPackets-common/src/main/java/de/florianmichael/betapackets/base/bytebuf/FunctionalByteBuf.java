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

package de.florianmichael.betapackets.base.bytebuf;

import de.florianmichael.betapackets.base.UserConnection;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.base.Reader;
import de.florianmichael.betapackets.model.base.Writer;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Vec3f;
import de.florianmichael.betapackets.model.world.item.ItemStackV1_3;
import de.florianmichael.betapackets.model.entity.metadata.Metadata;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import org.joml.Quaternionf;

import java.io.*;
import java.util.*;

/**
 * This class is an extension of the {@link PrimitiveByteBuf} which adds version dependent methods
 */
public class FunctionalByteBuf extends PrimitiveByteBuf {

    private final UserConnection userConnection;

    public FunctionalByteBuf(ByteBuf buffer, final UserConnection userConnection) {
        super(buffer);
        this.userConnection = userConnection;
    }

    public ItemStackV1_3 readItemStack() throws IOException {
        int id = this.readShort();

        if (id >= 0) {
            final int count = this.readByte();
            final int damage = this.readShort();

            return new ItemStackV1_3(id, count, damage, this.readCompoundTag());
        }
        return null;
    }

    public void writeItemStack(ItemStackV1_3 stack) throws IOException {
        if (stack == null) {
            this.writeShort(-1);
        } else {
            this.writeShort(stack.itemId);
            this.writeByte(stack.count);
            this.writeShort(stack.damage);
            this.writeCompoundTag(stack.tag);
        }
    }

    public CompoundTag readCompoundTag() throws IOException {
        if (readableBytes() == 0 || readByte() == 0) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new ByteBufInputStream(getBuffer()));
        final INbtTag tag = NbtIO.JAVA.read(dataInputStream, getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9), NbtReadTracker.unlimited());
        if (tag instanceof CompoundTag) {
            return (CompoundTag) tag;
        }
        return null;
    }

    public void writeCompoundTag(final CompoundTag tag) throws IOException {
        if (tag == null) {
            writeByte(0);
            return;
        }

        final DataOutputStream dataOutputStream = new DataOutputStream(new ByteBufOutputStream(getBuffer()));
        NbtIO.JAVA.write(dataOutputStream, "", tag, getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9));
    }

    public List<Metadata> readMetadata() throws Exception {
        final List<Metadata> list = new ArrayList<>();
        /*if (getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            int i;
            while ((i = readUnsignedByte()) != 255) {
                list.add(new Metadata(i, Objects.requireNonNull(MetadataTypes.getById(getProtocolVersion(), readUnsignedByte())), this));
            }
        } else {
            for (int i = readByte(); i != Byte.MAX_VALUE; i = readByte()) {
                list.add(new Metadata(i & 31, Objects.requireNonNull(MetadataTypes.getById(getProtocolVersion(), (i & 224) >> 5)), this));
            }
        }*/
        return list;
    }

    public void writeMetadata(final List<Metadata> metadata) throws Exception {
        /*for (Metadata metadatum : metadata) {
            if (getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
                this.writeByte(metadatum.index);
                this.writeVarInt((metadatum.metadataType.getId(getProtocolVersion())));
                metadatum.metadataType.getWriter().accept(this, metadatum.value);
            } else {
                this.writeByte((metadatum.metadataType.getId(getProtocolVersion()) << 5 | metadatum.index & 31) & 255);
                metadatum.metadataType.getWriter().accept(this, metadatum.value);
            }
        }
        if (getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            writeByte(255);
        } else {
            writeByte(Byte.MAX_VALUE);
        }*/
    }

    public Optional<ATextComponent> readOptionalComponent() {
        if (readBoolean()) return Optional.of(readComponent());
        return Optional.empty();
    }

    public void writeOptionalComponent(Optional<ATextComponent> optional) {
        if (optional.isPresent()) {
            writeBoolean(true);
            writeComponent(optional.get());
        } else {
            writeBoolean(false);
        }
    }

    public ATextComponent readComponent() {
        final String text = readString(32767);
        if (getProtocolVersion() == ProtocolCollection.R1_8) {
            return TextComponentSerializer.V1_8.deserialize(text);
        } else if (getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return TextComponentSerializer.V1_9.deserialize(text);
        }
        return null;
    }

    public void writeComponent(final ATextComponent component) {
        if (getProtocolVersion() == ProtocolCollection.R1_8) {
            writeString(TextComponentSerializer.V1_8.serialize(component));
        } else if (getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            writeString(TextComponentSerializer.V1_9.serialize(component));
        }
    }

    public Optional<BlockPos> readOptionalBlockPos() {
        if (readBoolean())
            return Optional.of(readBlockPos());
        return Optional.empty();
    }

    public void writeOptionalBlockPos(Optional<BlockPos> blockPos) {
        if (blockPos.isPresent()) {
            writeBoolean(true);
            writeBlockPos(blockPos.get());
        } else {
            writeBoolean(false);
        }
    }

    public void writeEnumConstant(Enum<?> instance) {
        writeVarInt(instance.ordinal());
    }

    public <T extends Enum<T>> T readEnumConstant(Class<T> enumClass) {
        return (T) ((Enum[])enumClass.getEnumConstants())[readVarInt()];
    }

    public void writeOptionalUUID(Optional<UUID> uuid) {
        if (uuid.isPresent()) {
            writeBoolean(true);
            writeUUID(uuid.get());
        } else {
            writeBoolean(false);
        }
    }

    public Optional<UUID> readOptionalUUID() {
        if (readBoolean()) return Optional.of(readUUID());
        return Optional.empty();
    }

    public <T> void writeOptional(Optional<T> value, Writer<T> writer) throws IOException {
        if (value.isPresent()) {
            writeBoolean(true);
            writer.write(this, value.get());
        } else {
            writeBoolean(false);
        }
    }

    public <T> Optional<T> readOptional(Reader<T> reader) throws IOException {
        if (readBoolean()) {
            return Optional.of(reader.read(this));
        } else {
            return Optional.empty();
        }
    }

    public Vec3f readVec3f() {
        return new Vec3f(this.readFloat(), this.readFloat(), this.readFloat());
    }

    public void writeVec3f(Vec3f vector3f) {
        this.writeFloat(vector3f.x);
        this.writeFloat(vector3f.y);
        this.writeFloat(vector3f.z);
    }

    public Quaternionf readQuaternionf() {
        return new Quaternionf(this.readFloat(), this.readFloat(), this.readFloat(), this.readFloat());
    }

    public void writeQuaternionf(Quaternionf quaternionf) {
        this.writeFloat(quaternionf.x);
        this.writeFloat(quaternionf.y);
        this.writeFloat(quaternionf.z);
        this.writeFloat(quaternionf.w);
    }

    public BlockPos readBlockPos() {
        return BlockPos.fromLong(readLong());
    }

    public void writeBlockPos(BlockPos pos) {
        writeLong(pos.toLong());
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    public ProtocolCollection getProtocolVersion() {
        return userConnection.getProtocolVersion();
    }
}
