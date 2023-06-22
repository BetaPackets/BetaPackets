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

package de.florianmichael.betapackets.packet.play.c2s;

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

import java.util.Objects;

public class ChatMessageC2SPacket extends Packet {

    public String message;

    public ChatMessageC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readString(100));
    }

    public ChatMessageC2SPacket(String message) {
        if (message.length() > 100) {
            message = message.substring(0, 100);
        }
        this.message = message;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.message);
    }

    @Override
    public String toString() {
        return "ChatMessageC2SPacket{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessageC2SPacket that = (ChatMessageC2SPacket) o;

        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
