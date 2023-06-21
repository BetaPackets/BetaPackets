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

import de.florianmichael.betapackets.base.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class UpdateHealthS2CPacket extends Packet {

    public float health;
    public int foodLevel;
    public float saturationLevel;

    public UpdateHealthS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readFloat(),
                transformer.readVarInt(),
                transformer.readFloat()
        );
    }

    public UpdateHealthS2CPacket(float health, int foodLevel, float saturationLevel) {
        this.health = health;
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeFloat(health);
        buf.writeVarInt(foodLevel);
        buf.writeFloat(saturationLevel);
    }

    @Override
    public String toString() {
        return "UpdateHealthS2CPacket{" +
                "health=" + health +
                ", foodLevel=" + foodLevel +
                ", saturationLevel=" + saturationLevel +
                '}';
    }
}
