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

package org.betapackets.betapackets.netty.base;

import org.betapackets.betapackets.connection.UserConnection;
import org.betapackets.betapackets.model.base.VersionEnum;
import org.betapackets.betapackets.model.base.Reader;
import org.betapackets.betapackets.model.base.Writer;
import org.betapackets.betapackets.model.entity.metadata.Metadata;
import org.betapackets.betapackets.model.position.BlockPos;
import org.betapackets.betapackets.model.position.ChunkSectionPos;
import org.betapackets.betapackets.model.position.GlobalPos;
import org.betapackets.betapackets.model.position.Vec3f;
import org.betapackets.betapackets.model.world.item.ItemStackV1_3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import org.joml.Quaternionf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    public void writeIdentifier(Identifier identifier) {
        writeString(identifier.get());
    }

    public Identifier readIdentifier() {
        return Identifier.of(readString());
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

    public void writeBitSet(BitSet bitSet) {
        long[] words = bitSet.toLongArray();
        writeVarInt(words.length);
        for (long word : words) {
            writeLong(word);
        }
    }

    public BitSet readBitSet() {
        long[] words = new long[readVarInt()];
        for (int i = 0; i < words.length; i++) {
            words[i] = readLong();
        }
        return BitSet.valueOf(words);
    }

    public CompoundTag readCompoundTag() throws IOException {
        int i = readerIndex();
        if (readableBytes() == 0 || readByte() == 0) {
            return null;
        }
        readerIndex(i);
        final DataInputStream dataInputStream = new DataInputStream(new ByteBufInputStream(getBuffer()));
        final INbtTag tag = NbtIO.JAVA.read(dataInputStream, false, NbtReadTracker.unlimited());
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
        NbtIO.JAVA.write(dataOutputStream, "", tag, false);
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
        return userConnection.getTextComponentSerializer().deserialize(readString());
    }

    public void writeComponent(final ATextComponent component) {
        writeString(userConnection.getTextComponentSerializer().serialize(component));
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
        return (T) ((Enum[]) enumClass.getEnumConstants())[readVarInt()];
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

    public void writeChunkSectionPos(ChunkSectionPos pos) {
        long value = 0L;
        value |= ((long) pos.x & 0x3FFFFFL) << 42;
        value |= ((long) pos.y & 0xFFFFFL) << 0;
        value |= ((long) pos.z & 0x3FFFFFL) << 20;
        writeLong(value);
    }

    public ChunkSectionPos readChunkSectionPos() {
        long value = readLong();
        return new ChunkSectionPos(
                (int) (value << 0 >> 42),
                (int) (value << 44 >> 44),
                (int) (value << 22 >> 42)
        );
    }

    // https://github.com/retrooper/packetevents/blob/2.0/api/src/main/java/com/github/retrooper/packetevents/util/Vector3i.java#L63
    public BlockPos readBlockPos() {
        long value = readLong();
        int x = (int) (value >> 38);
        int y;
        int z;
        if (getProtocolVersion().isNewerThanOrEqualTo(VersionEnum.R1_14)) {
            y = (int) (value << 52 >> 52);
            z = (int) (value << 26 >> 38);
        } else {
            y = (int) ((value >> 26) & 0xFFF);
            z = (int) (value << 38 >> 38);
        }
        return new BlockPos(x, y, z);
    }

    // https://github.com/retrooper/packetevents/blob/2.0/api/src/main/java/com/github/retrooper/packetevents/util/Vector3i.java#L63
    public void writeBlockPos(BlockPos pos) {
        long value;
        if (getProtocolVersion().isNewerThanOrEqualTo(VersionEnum.R1_17)) {
            long x = pos.x & 0x3FFFFFF;
            long y = pos.y & 0xFFF;
            long z = pos.z & 0x3FFFFFF;

            value = x << 38 | z << 12 | y;
        } else if (getProtocolVersion().isNewerThanOrEqualTo(VersionEnum.R1_14)) {
            value = ((long) (pos.x & 0x3FFFFFF) << 38) | ((long) (pos.z & 0x3FFFFFF) << 12) | (pos.y & 0xFFF);
        } else {
            value = ((long) (pos.x & 0x3FFFFFF) << 38) | ((long) (pos.y & 0xFFF) << 26) | (pos.z & 0x3FFFFFF);
        }
        writeLong(value);
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    public VersionEnum getProtocolVersion() {
        return userConnection.getProtocolVersion();
    }

    public GlobalPos readGlobalPos() {
        return new GlobalPos(readIdentifier(), readBlockPos());
    }

    public void writeGlobalPos(GlobalPos globalPos) {
        writeIdentifier(globalPos.getDimensionName());
        writeBlockPos(globalPos.getPos());
    }
}
