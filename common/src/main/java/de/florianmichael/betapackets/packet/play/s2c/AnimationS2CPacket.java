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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.game.Animation;

public class AnimationS2CPacket extends Packet {

    public int entityId;
    public ModelMapper<Short, Animation> type = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, Animation::getById);

    public AnimationS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.type.read(buf);
    }

    public AnimationS2CPacket(int entityId, Animation type) {
        this.entityId = entityId;
        this.type = new ModelMapper<>(FunctionalByteBuf::writeByte, type);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        this.type.write(buf);
    }

    @Override
    public String toString() {
        return "AnimationS2CPacket{" +
                "entityId=" + entityId +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimationS2CPacket that = (AnimationS2CPacket) o;

        if (entityId != that.entityId) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + type.hashCode();
        return result;
    }
}
