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

package de.florianmichael.betapackets.packet.login.s2c;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;
import java.util.Objects;

public class EncryptionRequestS2CPacket extends Packet {
    public String serverId;
    public byte[] key;
    public byte[] verifyToken;

    public EncryptionRequestS2CPacket(final FunctionalByteBuf buf) {
        this(
                buf.readString(20),
                buf.readByteArray(),
                buf.readByteArray()
        );
    }

    public EncryptionRequestS2CPacket(String serverId, byte[] key, byte[] verifyToken) {
        this.serverId = serverId;
        this.key = key;
        this.verifyToken = verifyToken;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeString(serverId);
        buf.writeByteArray(key);
        buf.writeByteArray(verifyToken);
    }

    @Override
    public String toString() {
        return "EncryptionRequestS2CPacket{" +
                "serverId='" + serverId + '\'' +
                ", key=" + Arrays.toString(key) +
                ", verifyToken=" + Arrays.toString(verifyToken) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncryptionRequestS2CPacket that = (EncryptionRequestS2CPacket) o;

        if (!Objects.equals(serverId, that.serverId)) return false;
        if (!Arrays.equals(key, that.key)) return false;
        return Arrays.equals(verifyToken, that.verifyToken);
    }

    @Override
    public int hashCode() {
        int result = serverId != null ? serverId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(key);
        result = 31 * result + Arrays.hashCode(verifyToken);
        return result;
    }
}
