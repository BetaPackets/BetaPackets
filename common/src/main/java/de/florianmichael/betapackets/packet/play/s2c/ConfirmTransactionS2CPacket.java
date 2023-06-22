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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class ConfirmTransactionS2CPacket extends Packet {

    public int windowID;
    public short actionNumber;
    public boolean accepted;

    public ConfirmTransactionS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readUnsignedByte(), buf.readShort(), buf.readBoolean());
    }

    public ConfirmTransactionS2CPacket(int windowID, short actionNumber, boolean accepted) {
        this.windowID = windowID;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(windowID);
        buf.writeShort(actionNumber);
        buf.writeBoolean(accepted);
    }

    @Override
    public String toString() {
        return "ConfirmTransactionS2CPacket{" +
                "windowID=" + windowID +
                ", actionNumber=" + actionNumber +
                ", accepted=" + accepted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmTransactionS2CPacket that = (ConfirmTransactionS2CPacket) o;

        if (windowID != that.windowID) return false;
        if (actionNumber != that.actionNumber) return false;
        return accepted == that.accepted;
    }

    @Override
    public int hashCode() {
        int result = windowID;
        result = 31 * result + (int) actionNumber;
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }
}
