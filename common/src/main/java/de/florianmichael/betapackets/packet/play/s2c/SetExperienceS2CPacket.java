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

public class SetExperienceS2CPacket extends Packet {

    public float experienceBar;
    public int level;
    public int totalExperience;

    public SetExperienceS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readFloat(),
                transformer.readVarInt(),
                transformer.readVarInt()
        );
    }

    public SetExperienceS2CPacket(float experienceBar, int level, int totalExperience) {
        this.experienceBar = experienceBar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeFloat(experienceBar);
        buf.writeVarInt(level);
        buf.writeVarInt(totalExperience);
    }

    @Override
    public String toString() {
        return "SetExperienceS2CPacket{" +
                "experienceBar=" + experienceBar +
                ", level=" + level +
                ", totalExperience=" + totalExperience +
                '}';
    }
}
