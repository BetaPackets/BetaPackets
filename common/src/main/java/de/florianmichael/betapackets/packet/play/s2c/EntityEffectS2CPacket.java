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

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.registry.model.IPotionEffectType;

public class EntityEffectS2CPacket extends EntityS2CPacket {

    public IPotionEffectType entityEffect;
    public byte amplifier;
    public int duration;
    public byte hideParticles;

    public EntityEffectS2CPacket(FunctionalByteBuf transformer) {
        super(transformer);

        this.entityEffect = transformer.getUserConnection().getCurrentRegistry().getPotionEffectType().getByIndex(transformer.readByte());
        this.amplifier = transformer.readByte();
        this.duration = transformer.readVarInt();
        this.hideParticles = transformer.readByte();
    }

    public EntityEffectS2CPacket(int entityId, IPotionEffectType entityEffect, byte amplifier, int duration, byte hideParticles) {
        super(entityId);

        this.entityEffect = entityEffect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.hideParticles = hideParticles;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeByte(entityEffect.getIndex());
        buf.writeByte(amplifier);
        buf.writeVarInt(duration);
        buf.writeByte(hideParticles);
    }

    @Override
    public String toString() {
        return "EntityEffectS2CPacket{" +
                "entityEffect=" + entityEffect +
                ", amplifier=" + amplifier +
                ", duration=" + duration +
                ", hideParticles=" + hideParticles +
                ", entityId=" + entityId +
                '}';
    }
}
