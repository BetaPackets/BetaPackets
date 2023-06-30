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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class ConfirmTransactionC2SPacket extends Packet {

    public int windowId;
    public short uid;
    public boolean accepted;

    public ConfirmTransactionC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readByte(), buf.readShort(), buf.readByte() != 0);
    }

    public ConfirmTransactionC2SPacket(int windowId, short uid, boolean accepted) {
        this.windowId = windowId;
        this.uid = uid;
        this.accepted = accepted;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(this.windowId);
        buf.writeShort(this.uid);
        buf.writeByte(this.accepted ? 1 : 0);
    }

    @Override
    public String toString() {
        return "ConfirmTransactionC2SPacket{" +
                "windowId=" + windowId +
                ", uid=" + uid +
                ", accepted=" + accepted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmTransactionC2SPacket that = (ConfirmTransactionC2SPacket) o;

        if (windowId != that.windowId) return false;
        if (uid != that.uid) return false;
        return accepted == that.accepted;
    }

    @Override
    public int hashCode() {
        int result = windowId;
        result = 31 * result + (int) uid;
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }
}
