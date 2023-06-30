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

public class PlayerC2SPacket extends Packet {

    public boolean onGround;

    public PlayerC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readUnsignedByte() != 0);
    }

    public PlayerC2SPacket(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(this.onGround ? 1 : 0);
    }

    @Override
    public String toString() {
        return "PlayerC2SPacket{" +
                "onGround=" + onGround +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerC2SPacket that = (PlayerC2SPacket) o;

        return onGround == that.onGround;
    }

    @Override
    public int hashCode() {
        return (onGround ? 1 : 0);
    }
}
