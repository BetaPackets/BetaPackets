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

package de.florianmichael.betapackets.packet.play.s2c.v1_9;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class UnloadChunkS2CPacket extends Packet {

    public int x;
    public int z;

    public UnloadChunkS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    public UnloadChunkS2CPacket(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(x);
        buf.writeInt(z);
    }

    @Override
    public String toString() {
        return "UnloadChunkS2CPacket{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnloadChunkS2CPacket that = (UnloadChunkS2CPacket) o;

        if (x != that.x) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }
}
