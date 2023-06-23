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

package de.florianmichael.betapackets.packet.status.c2s;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class PingRequestC2SPacket extends Packet {
    public long payload;

    public PingRequestC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readLong());
    }

    public PingRequestC2SPacket(long payload) {
        this.payload = payload;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeLong(payload);
    }

    @Override
    public String toString() {
        return "PingRequestC2SPacket{" +
                "payload=" + payload +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PingRequestC2SPacket that = (PingRequestC2SPacket) o;

        return payload == that.payload;
    }

    @Override
    public int hashCode() {
        return (int) (payload ^ (payload >>> 32));
    }
}
