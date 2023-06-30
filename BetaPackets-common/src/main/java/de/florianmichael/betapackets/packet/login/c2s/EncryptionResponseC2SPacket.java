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

package de.florianmichael.betapackets.packet.login.c2s;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;

public class EncryptionResponseC2SPacket extends Packet {
    public byte[] sharedSecret;
    public byte[] verifyToken;

    public EncryptionResponseC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readByteArray(), buf.readByteArray());
    }

    public EncryptionResponseC2SPacket(byte[] sharedSecret, byte[] verifyToken) {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeByteArray(sharedSecret);
        buf.writeByteArray(verifyToken);
    }

    @Override
    public String toString() {
        return "EncryptionResponseC2SPacket{" +
                "sharedSecret=" + Arrays.toString(sharedSecret) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncryptionResponseC2SPacket that = (EncryptionResponseC2SPacket) o;

        if (!Arrays.equals(sharedSecret, that.sharedSecret)) return false;
        return Arrays.equals(verifyToken, that.verifyToken);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(sharedSecret);
        result = 31 * result + Arrays.hashCode(verifyToken);
        return result;
    }
}
