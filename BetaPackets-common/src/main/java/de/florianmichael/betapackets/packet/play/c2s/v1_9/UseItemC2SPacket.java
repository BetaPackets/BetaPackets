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

package de.florianmichael.betapackets.packet.play.c2s.v1_9;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.entity.v1_9.Hand1_9;

import java.util.Objects;

// MCP calls that packet CPacketPlayerBlockPlacement ???
public class UseItemC2SPacket extends Packet {

    public ModelMapper<Integer, Hand1_9> hand = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, Hand1_9::getById);

    public UseItemC2SPacket(final FunctionalByteBuf buf) {
        this.hand.read(buf);
    }

    public UseItemC2SPacket(Hand1_9 hand) {
        this.hand = new ModelMapper<>(FunctionalByteBuf::writeVarInt, hand);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.hand.write(buf);
    }

    @Override
    public String toString() {
        return "UseItemC2SPacket{" +
                "hand=" + hand +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseItemC2SPacket that = (UseItemC2SPacket) o;

        return Objects.equals(hand, that.hand);
    }

    @Override
    public int hashCode() {
        return hand != null ? hand.hashCode() : 0;
    }
}
