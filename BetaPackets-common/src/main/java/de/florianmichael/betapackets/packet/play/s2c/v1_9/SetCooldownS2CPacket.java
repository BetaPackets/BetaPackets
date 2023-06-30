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

public class SetCooldownS2CPacket extends Packet {

    public int item;
    public int ticks;

    public SetCooldownS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readVarInt(), buf.readVarInt());
    }

    public SetCooldownS2CPacket(int item, int ticks) {
        this.item = item;
        this.ticks = ticks;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(item);
        buf.writeVarInt(ticks);
    }

    @Override
    public String toString() {
        return "SetCooldownS2CPacket{" +
                "item=" + item +
                ", ticks=" + ticks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetCooldownS2CPacket that = (SetCooldownS2CPacket) o;

        if (item != that.item) return false;
        return ticks == that.ticks;
    }

    @Override
    public int hashCode() {
        int result = item;
        result = 31 * result + ticks;
        return result;
    }
}
