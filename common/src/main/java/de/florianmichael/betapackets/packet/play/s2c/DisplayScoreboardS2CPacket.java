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
import de.florianmichael.betapackets.model.game.hud.scoreboard.ScoreboardPosition;

import java.util.Objects;

public class DisplayScoreboardS2CPacket extends Packet {

    public ModelMapper<Byte, ScoreboardPosition> position = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ScoreboardPosition::getById);
    public String scoreName;

    public DisplayScoreboardS2CPacket(final FunctionalByteBuf buf) {
        this.position.read(buf);
        this.scoreName = buf.readString(16);
    }

    public DisplayScoreboardS2CPacket(ScoreboardPosition position, String scoreName) {
        this.position = new ModelMapper<>(FunctionalByteBuf::writeByte, position);
        this.scoreName = scoreName;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.position.write(buf);
        buf.writeString(this.scoreName);
    }

    @Override
    public String toString() {
        return "DisplayScoreboardS2CPacket{" +
                "position=" + position +
                ", scoreName='" + scoreName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayScoreboardS2CPacket that = (DisplayScoreboardS2CPacket) o;

        if (!Objects.equals(position, that.position)) return false;
        return Objects.equals(scoreName, that.scoreName);
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (scoreName != null ? scoreName.hashCode() : 0);
        return result;
    }
}
