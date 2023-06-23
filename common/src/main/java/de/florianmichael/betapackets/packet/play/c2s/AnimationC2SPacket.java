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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.v1_9.Hand1_9;

import java.util.Objects;

public class AnimationC2SPacket extends Packet {

    public ModelMapper<Integer, Hand1_9> hand1_9 = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, Hand1_9::getById);

    public AnimationC2SPacket(final FunctionalByteBuf buf) {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand1_9.read(buf);
        }
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand1_9.write(buf);
        }
    }

    @Override
    public String toString() {
        return "AnimationC2SPacket{" +
                "hand1_9=" + hand1_9 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimationC2SPacket that = (AnimationC2SPacket) o;

        return Objects.equals(hand1_9, that.hand1_9);
    }

    @Override
    public int hashCode() {
        return hand1_9 != null ? hand1_9.hashCode() : 0;
    }
}
