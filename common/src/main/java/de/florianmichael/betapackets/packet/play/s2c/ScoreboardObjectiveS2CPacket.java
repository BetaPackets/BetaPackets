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
import de.florianmichael.betapackets.model.hud.scoreboard.ScoreMode;
import de.florianmichael.betapackets.model.hud.scoreboard.ScoreRenderType;

import java.util.Objects;

public class ScoreboardObjectiveS2CPacket extends Packet {

    public String objectiveName;
    public ModelMapper<Byte, ScoreMode> mode = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ScoreMode::getById);
    public String objectiveValue;
    public ModelMapper<String, ScoreRenderType> type = new ModelMapper<>(buf -> buf.readString(16), FunctionalByteBuf::writeString, ScoreRenderType::getByName);

    public ScoreboardObjectiveS2CPacket(final FunctionalByteBuf buf) {
        this.objectiveName = buf.readString(16);
        this.mode.read(buf);
        if (this.mode.value == 0 || this.mode.value == 2) {
            this.objectiveValue = buf.readString(32);
            this.type.read(buf);
        }
    }

    public ScoreboardObjectiveS2CPacket(String objectiveName, ScoreMode mode, String objectiveValue, ScoreRenderType type) {
        this.objectiveName = objectiveName;
        this.mode = new ModelMapper<>(FunctionalByteBuf::writeByte, mode);
        this.objectiveValue = objectiveValue;
        this.type = new ModelMapper<>(FunctionalByteBuf::writeString, type);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.objectiveName);
        this.mode.write(buf);
        if (this.mode.value == 0 || this.mode.value == 2) {
            buf.writeString(this.objectiveValue);
            this.type.write(buf);
        }
    }

    @Override
    public String toString() {
        return "ScoreboardObjectiveS2CPacket{" +
                "objectiveName='" + objectiveName + '\'' +
                ", mode=" + mode +
                ", objectiveValue='" + objectiveValue + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreboardObjectiveS2CPacket that = (ScoreboardObjectiveS2CPacket) o;

        if (!Objects.equals(objectiveName, that.objectiveName))
            return false;
        if (!Objects.equals(mode, that.mode)) return false;
        if (!Objects.equals(objectiveValue, that.objectiveValue))
            return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = objectiveName != null ? objectiveName.hashCode() : 0;
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (objectiveValue != null ? objectiveValue.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
