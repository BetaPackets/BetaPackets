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
import de.florianmichael.betapackets.model.game.potion.PotionEffectTypes;

import java.util.Objects;

public class EntityEffectS2CPacket extends Packet {

    public int entityId;
    public ModelMapper<Byte, PotionEffectTypes> entityEffect = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, PotionEffectTypes::getById);
    public byte amplifier;
    public int duration;
    public byte hideParticles;

    public EntityEffectS2CPacket(FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.entityEffect.read(buf);
        this.amplifier = buf.readByte();
        this.duration = buf.readVarInt();
        this.hideParticles = buf.readByte();
    }

    public EntityEffectS2CPacket(int entityId, PotionEffectTypes entityEffect, byte amplifier, int duration, byte hideParticles) {
        this.entityId = entityId;
        this.entityEffect = new ModelMapper<>(FunctionalByteBuf::writeByte, entityEffect);
        this.amplifier = amplifier;
        this.duration = duration;
        this.hideParticles = hideParticles;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        entityEffect.write(buf);
        buf.writeByte(amplifier);
        buf.writeVarInt(duration);
        buf.writeByte(hideParticles);
    }

    @Override
    public String toString() {
        return "EntityEffectS2CPacket{" +
                "entityId=" + entityId +
                ", entityEffect=" + entityEffect +
                ", amplifier=" + amplifier +
                ", duration=" + duration +
                ", hideParticles=" + hideParticles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityEffectS2CPacket that = (EntityEffectS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (amplifier != that.amplifier) return false;
        if (duration != that.duration) return false;
        if (hideParticles != that.hideParticles) return false;
        return Objects.equals(entityEffect, that.entityEffect);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (entityEffect != null ? entityEffect.hashCode() : 0);
        result = 31 * result + (int) amplifier;
        result = 31 * result + duration;
        result = 31 * result + (int) hideParticles;
        return result;
    }
}
