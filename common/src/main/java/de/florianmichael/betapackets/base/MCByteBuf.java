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

package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.model.item.ItemStackV1_3;
import de.florianmichael.betapackets.model.metadata.IMetadataType;
import de.florianmichael.betapackets.model.metadata.Metadata;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MCByteBuf {

    public final static short DEFAULT_MAX_STRING_LENGTH = Short.MAX_VALUE;

    private final ByteBuf buffer;

    public MCByteBuf() {
        this(Unpooled.buffer());
    }

    public MCByteBuf(final ByteBuf buffer) {
        this.buffer = buffer;
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public void writeFloat(final float input) {
        buffer.writeFloat(input);
    }

    public void writeDouble(final double input) {
        buffer.writeDouble(input);
    }

    public int readVarInt() {
        byte b;
        int i = 0;
        int j = 0;
        do {
            b = this.readByte();
            i |= (b & 0x7F) << j++ * 7;
            if (j <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((b & 0x80) == 128);
        return i;
    }

    public ItemStackV1_3 readItemStack() {
        try {
            int id = this.readShort();

            if (id >= 0) {
                final int count = this.readByte();
                final int damage = this.readShort();

                return new ItemStackV1_3(id, count, damage, this.readCompoundTag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompoundTag readCompoundTag() {
        try {
            final DataInput dataInput = new DataInputStream(new ByteBufInputStream(buffer));
            return NbtIO.JAVA.getReader().readCompound(dataInput, NbtReadTracker.unlimited());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeItemStack(ItemStackV1_3 stack) {
        try {
            if (stack == null) {
                this.writeShort(-1);
            } else {
                this.writeShort(stack.itemId);
                this.writeByte(stack.count);
                this.writeShort(stack.damage);
                this.writeCompoundTag(stack.tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeCompoundTag(final CompoundTag tag) {
        try {
            final DataOutput dataOutput = new DataOutputStream(new ByteBufOutputStream(buffer));
            NbtIO.JAVA.getWriter().write(dataOutput, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Metadata> readMetadata(final IMetadataType metadataType) {
        final List<Metadata> list = new ArrayList<>();
        for (int i = readByte(); i != Byte.MAX_VALUE; i = readByte()) {
            list.add(new Metadata(i & 31, metadataType.byIndex((i & 224) >> 5), this));
        }
        return list;
    }

    public void writeMetadata(final List<Metadata> metadata) {
        for (Metadata metadatum : metadata) {
            this.writeByte((metadatum.metadataType.getIndex() << 5 | metadatum.index & 31));
            metadatum.metadataType.getWriter().accept(this, metadatum);
        }
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public String readString() {
        return this.readString(DEFAULT_MAX_STRING_LENGTH);
    }

    public String readString(int maxLength) {
        int i = toEncodedStringLength(maxLength);
        int j = this.readVarInt();

        if (j > i) throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i + ")");
        if (j < 0) throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");

        final String string = this.toString(this.readerIndex(), j, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + j);

        if (string.length() > maxLength) throw new DecoderException("The received string length is longer than maximum allowed (" + string.length() + " > " + maxLength + ")");
        return string;
    }

    private int toEncodedStringLength(int decodedLength) {
        return decodedLength * 4;
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public int readerIndex() {
        return buffer.readerIndex();
    }

    public long readLong() {
        return buffer.readLong();
    }

    public ByteBuf readerIndex(int index) {
        return buffer.readerIndex(index);
    }

    public String toString(final Charset charset) {
        return buffer.toString(charset);
    }

    public String toString(final int index, final int length, final Charset charset) {
        return buffer.toString(index, length, charset);
    }

    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    public short readShort() {
        return buffer.readShort();
    }

    public int readableBytes() {
        return buffer.readableBytes();
    }

    public byte[] readByteArray() {
        return readByteArray(readableBytes());
    }

    public int readInt() {
        return buffer.readInt();
    }

    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }

    public ByteBuf readBytes(byte[] bytes) {
        return buffer.readBytes(bytes);
    }

    public byte[] readByteArray(int maxSize) {
        int i = this.readVarInt();
        if (i > maxSize) {
            throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxSize);
        }
        byte[] bs = new byte[i];
        this.readBytes(bs);
        return bs;
    }

    public void writeString(String string) {
        byte[] asBytes = string.getBytes(StandardCharsets.UTF_8);

        if (asBytes.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        }

        this.writeVarInt(asBytes.length);
        this.writeBytes(asBytes);
    }

    public void writeVarInt(int input) {
        while ((input & -128) != 0)
        {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.writeByte(input);
    }

    public void writeByteArray(byte[] array) {
        this.writeVarInt(array.length);
        this.writeBytes(array);
    }

    public void writeByte(final int input) {
        buffer.writeByte(input);
    }

    public void writeBytes(final byte[] input) {
        buffer.writeBytes(input);
    }

    public void writeShort(final int input) {
        buffer.writeShort(input);
    }

    public void writeLong(final long input) {
        buffer.writeLong(input);
    }

    public void writeBoolean(final boolean input) {
        buffer.writeBoolean(input);
    }

    public void writeInt(final int input) {
        buffer.writeInt(input);
    }

    public ByteBuf unwrapped() {
        return buffer;
    }
}
