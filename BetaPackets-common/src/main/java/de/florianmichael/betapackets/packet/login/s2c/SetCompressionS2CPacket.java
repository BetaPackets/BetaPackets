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

public class SetCompressionS2CPacket extends Packet {
    public int threshold;

    public SetCompressionS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readVarInt());
    }

    public SetCompressionS2CPacket(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeVarInt(threshold);
    }

    @Override
    public String toString() {
        return "SetCompressionS2CPacket{" +
                "threshold=" + threshold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetCompressionS2CPacket that = (SetCompressionS2CPacket) o;

        return threshold == that.threshold;
    }

    @Override
    public int hashCode() {
        return threshold;
    }
}
