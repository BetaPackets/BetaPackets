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

public class CloseWindowS2CPacket extends Packet {

    public int windowId;

    public CloseWindowS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readUnsignedByte());
    }

    public CloseWindowS2CPacket(int windowId) {
        this.windowId = windowId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(windowId);
    }

    @Override
    public String toString() {
        return "CloseWindowS2CPacket{" +
                "windowId=" + windowId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CloseWindowS2CPacket that = (CloseWindowS2CPacket) o;

        return windowId == that.windowId;
    }

    @Override
    public int hashCode() {
        return windowId;
    }
}
