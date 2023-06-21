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
import de.florianmichael.betapackets.base.Packet;

import java.util.UUID;

public class LoginSuccessS2CPacket extends Packet {
    public UUID uuid;
    public String username;

    public LoginSuccessS2CPacket(final FunctionalByteBuf buf) {
        this(
                UUID.fromString(buf.readString(36)),
                buf.readString(16)
        );
    }

    public LoginSuccessS2CPacket(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeString(uuid.toString());
        buf.writeString(username);
    }

    @Override
    public String toString() {
        return "LoginSuccessS2CPacket{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
