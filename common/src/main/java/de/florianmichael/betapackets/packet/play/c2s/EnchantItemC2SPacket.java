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

public class EnchantItemC2SPacket extends Packet {

    public int windowId;
    public int button;

    public EnchantItemC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readByte(), buf.readByte());
    }

    public EnchantItemC2SPacket(int windowId, int button) {
        this.windowId = windowId;
        this.button = button;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(this.windowId);
        buf.writeByte(this.button);
    }

    @Override
    public String toString() {
        return "EnchantItemC2SPacket{" +
                "windowId=" + windowId +
                ", button=" + button +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnchantItemC2SPacket that = (EnchantItemC2SPacket) o;

        if (windowId != that.windowId) return false;
        return button == that.button;
    }

    @Override
    public int hashCode() {
        int result = windowId;
        result = 31 * result + button;
        return result;
    }
}
