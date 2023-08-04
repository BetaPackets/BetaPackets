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

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * This class is a wrapper for the ByteBuf class and provides methods for reading and writing primitive data types.
 */
public class PrimitiveByteBuf {
    public final static short DEFAULT_MAX_STRING_LENGTH = Short.MAX_VALUE;

    private final ByteBuf buffer;

    public PrimitiveByteBuf(final ByteBuf buffer) {
        this.buffer = buffer;
    }

    // Primitives (signed)
    public float readFloat() {
        return buffer.readFloat();
    }

    public void writeFloat(final float input) {
        buffer.writeFloat(input);
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public void writeDouble(final double input) {
        buffer.writeDouble(input);
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public void writeByte(final int input) {
        buffer.writeByte(input);
    }

    public void writeByte(final short input) {
        buffer.writeByte(input);
    }

    public void writeByte(final byte input) {
        buffer.writeByte(input);
    }

    public long readLong() {
        return buffer.readLong();
    }

    public void writeLong(final long input) {
        buffer.writeLong(input);
    }

    public short readShort() {
        return buffer.readShort();
    }

    public void writeShort(final int input) {
        buffer.writeShort(input);
    }

    public void writeShort(final short input) {
        buffer.writeShort(input);
    }

    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    public void writeBoolean(final boolean input) {
        buffer.writeBoolean(input);
    }

    public int readInt() {
        return buffer.readInt();
    }

    public void writeInt(final int input) {
        buffer.writeInt(input);
    }

    // Complex structures
    public String readString() {
        return this.readString(DEFAULT_MAX_STRING_LENGTH);
    }

    public String readString(int maxLength) {
        int i = toEncodedStringLength(maxLength);
        int j = this.readVarInt();

        if (j > i)
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i + ")");
        if (j < 0)
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");

        final String string = this.toString(this.readerIndex(), j, StandardCharsets.UTF_8);
        this.readerIndex(this.readerIndex() + j);

        if (string.length() > maxLength)
            throw new DecoderException("The received string length is longer than maximum allowed (" + string.length() + " > " + maxLength + ")");
        return string;
    }

    public void writeString(String string) {
        byte[] asBytes = string.getBytes(StandardCharsets.UTF_8);

        if (asBytes.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        }

        this.writeVarInt(asBytes.length);
        this.writeBytes(asBytes);
    }

    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    public void writeUUID(final UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    // Var and unsigned
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

    public void writeVarInt(int input) {
        while ((input & -128) != 0) {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.writeByte(input);
    }

    public long readVarLong() {
        long i = 0L;
        int j = 0;

        while (true) {
            byte b0 = this.readByte();
            i |= (long) (b0 & 127) << j++ * 7;

            if (j > 10) throw new RuntimeException("VarLong too big");
            if ((b0 & 128) != 128) break;
        }

        return i;
    }

    public void writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int)(value & 127L) | 128);
            value >>>= 7;
        }

        this.writeByte((int)value);
    }

    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }

    // Arrays
    public byte[] readByteArray() {
        return readByteArray(readableBytes());
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

    public void writeByteArray(byte[] array) {
        this.writeVarInt(array.length);
        this.writeBytes(array);
    }

    public int[] readVarIntArray() {
        final int[] varIntArray = new int[this.readVarInt()];
        for (int i = 0; i < varIntArray.length; ++i) {
            varIntArray[i] = this.readVarInt();
        }
        return varIntArray;
    }

    public void writeVarIntArray(int[] array) {
        this.writeVarInt(array.length);
        for (int j : array) {
            this.writeVarInt(j);
        }
    }

    public void readBytes(byte[] bytes) {
        buffer.readBytes(bytes);
    }

    public void writeBytes(ByteBuf input) {
        buffer.writeBytes(input);
    }

    public void writeBytes(final byte[] input) {
        buffer.writeBytes(input);
    }

    // Bytebuf self
    public int readerIndex() {
        return buffer.readerIndex();
    }

    public int readableBytes() {
        return buffer.readableBytes();
    }

    public void readerIndex(int index) {
        buffer.readerIndex(index);
    }

    // Utils (converter)
    private int toEncodedStringLength(int decodedLength) {
        return decodedLength * 4;
    }

    public String toString(final Charset charset) {
        return buffer.toString(charset);
    }

    public String toString(final int index, final int length, final Charset charset) {
        return buffer.toString(index, length, charset);
    }

    public ByteBuf getBuffer() {
        return buffer;
    }
}
