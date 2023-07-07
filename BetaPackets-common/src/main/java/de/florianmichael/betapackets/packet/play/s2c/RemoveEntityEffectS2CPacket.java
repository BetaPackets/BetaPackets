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
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.potioneffect.PotionEffectType;

import java.util.Objects;

public class RemoveEntityEffectS2CPacket extends Packet {

    public int entityId;
    public ModelMapper<Byte, PotionEffectType> entityEffect = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, (protocolCollection, aByte) -> null);

    public RemoveEntityEffectS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        //this.entityEffect.read(buf);
    }

    /*public RemoveEntityEffectS2CPacket(int entityId, PotionEffectTypes entityEffect) {
        this.entityId = entityId;
        this.entityEffect = new ModelMapper<>(FunctionalByteBuf::writeByte, entityEffect);
    }*/

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        //this.entityEffect.write(buf);
    }

    @Override
    public String toString() {
        return "RemoveEntityEffectS2CPacket{" +
                "entityId=" + entityId +
                ", entityEffect=" + entityEffect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoveEntityEffectS2CPacket that = (RemoveEntityEffectS2CPacket) o;

        if (entityId != that.entityId) return false;
        return Objects.equals(entityEffect, that.entityEffect);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (entityEffect != null ? entityEffect.hashCode() : 0);
        return result;
    }
}
