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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FriendlyByteBuf {
    public final static short DEFAULT_MAX_STRING_LENGTH = Short.MAX_VALUE;

    private final ByteBuf buffer;

    public FriendlyByteBuf() {
        this(Unpooled.buffer());
    }

    public FriendlyByteBuf(final ByteBuf buffer) {
        this.buffer = buffer;
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

    public short readShort() {
        return buffer.readShort();
    }

    public int readableBytes() {
        return buffer.readableBytes();
    }

    public byte[] readByteArray() {
        return readByteArray(readableBytes());
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

    public ByteBuf unwrapped() {
        return buffer;
    }
}
