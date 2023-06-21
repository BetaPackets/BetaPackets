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

import de.florianmichael.betapackets.base.PacketTransformer;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;

public class EncryptionRequestS2CPacket extends Packet {
    public String serverId;
    public byte[] key;
    public byte[] verifyToken;

    public EncryptionRequestS2CPacket(final PacketTransformer buf) {
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
    public void write(PacketTransformer buf) {
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
}
