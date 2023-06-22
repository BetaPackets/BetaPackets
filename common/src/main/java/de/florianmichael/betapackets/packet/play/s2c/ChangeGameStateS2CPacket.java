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
import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.GameStateTypes;

import java.util.Objects;

public class ChangeGameStateS2CPacket extends Packet {

    public ModelMapper<Short, GameStateTypes> reason = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, GameStateTypes::getById);
    public float value;

    public ChangeGameStateS2CPacket(final FunctionalByteBuf buf) {
        this.reason.read(buf);
        this.value = buf.readFloat();
    }

    public ChangeGameStateS2CPacket(GameStateTypes reason, float value) {
        this.reason = new ModelMapper<>(FunctionalByteBuf::writeByte, reason);
        this.value = value;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        reason.write(buf);
        buf.writeFloat(value);
    }

    public GameStateTypes getReason() {
        return reason.getValue();
    }

    @Override
    public String toString() {
        return "ChangeGameStateS2CPacket{" +
                "reason=" + reason +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeGameStateS2CPacket that = (ChangeGameStateS2CPacket) o;

        if (Float.compare(that.value, value) != 0) return false;
        return Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        int result = reason != null ? reason.hashCode() : 0;
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        return result;
    }
}
