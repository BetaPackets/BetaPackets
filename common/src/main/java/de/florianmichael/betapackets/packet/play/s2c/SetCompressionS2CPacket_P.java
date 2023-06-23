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

package de.florianmichael.betapackets.packet.play.s2c;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

// This packet is completely broken and has been removed in the 1.9 snapshots.
public class SetCompressionS2CPacket_P extends Packet {
    public int threshold;

    public SetCompressionS2CPacket_P(final FunctionalByteBuf buf) {
        this(buf.readVarInt());
    }

    public SetCompressionS2CPacket_P(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.threshold);
    }

    @Override
    public String toString() {
        return "SetCompressionS2CPacket_P{" +
                "threshold=" + threshold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetCompressionS2CPacket_P that = (SetCompressionS2CPacket_P) o;

        return threshold == that.threshold;
    }

    @Override
    public int hashCode() {
        return threshold;
    }
}
