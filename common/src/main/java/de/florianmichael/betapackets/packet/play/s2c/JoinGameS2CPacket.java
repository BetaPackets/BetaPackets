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
import de.florianmichael.betapackets.model.game.Difficulty;
import de.florianmichael.betapackets.model.game.GameMode;
import de.florianmichael.betapackets.model.game.LevelType;

import java.util.Objects;

public class JoinGameS2CPacket extends Packet {

    public int entityId;
    public boolean hardcore;
    public ModelMapper<Short, GameMode> gameMode = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, (buf, value) -> buf.writeByte(this.hardcore ? value | 8 : value), (version, value) -> {
        this.hardcore = (value & 8) == 0;
        return GameMode.getById(version, (short) (value & -9));
    });
    public int dimension;
    public ModelMapper<Short, Difficulty> difficulty = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, Difficulty::getById);;
    public int maxPlayers;
    public ModelMapper<String, LevelType> levelType = new ModelMapper<>(buf -> buf.readString(16), FunctionalByteBuf::writeString, LevelType::getByType);
    public boolean reducedDebugInfo;

    public JoinGameS2CPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readInt();
        this.gameMode.read(buf);
        this.dimension = buf.readByte();
        this.difficulty.read(buf);
        this.maxPlayers = buf.readUnsignedByte();
        this.levelType.read(buf);
        this.reducedDebugInfo = buf.readBoolean();
    }

    public JoinGameS2CPacket(int entityId, boolean hardcore, GameMode gameMode, int dimension, Difficulty difficulty, int maxPlayers, LevelType levelType, boolean reducedDebugInfo) {
        this.entityId = entityId;
        this.hardcore = hardcore;
        this.gameMode = new ModelMapper<>(FunctionalByteBuf::writeByte, gameMode);
        this.dimension = dimension;
        this.difficulty = new ModelMapper<>(FunctionalByteBuf::writeByte, difficulty);
        this.maxPlayers = maxPlayers;
        this.levelType = new ModelMapper<>(FunctionalByteBuf::writeString, levelType);
        this.reducedDebugInfo = reducedDebugInfo;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeInt(this.entityId);
        this.gameMode.write(buf);
        buf.writeByte(this.dimension);
        this.difficulty.write(buf);
        buf.writeByte(this.maxPlayers);
        this.levelType.write(buf);
        buf.writeBoolean(this.reducedDebugInfo);
    }

    @Override
    public String toString() {
        return "JoinGameS2CPacket{" +
                "entityId=" + entityId +
                ", hardcore=" + hardcore +
                ", gameMode=" + gameMode +
                ", dimension=" + dimension +
                ", difficulty=" + difficulty +
                ", maxPlayers=" + maxPlayers +
                ", levelType=" + levelType +
                ", reducedDebugInfo=" + reducedDebugInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinGameS2CPacket that = (JoinGameS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (hardcore != that.hardcore) return false;
        if (dimension != that.dimension) return false;
        if (maxPlayers != that.maxPlayers) return false;
        if (reducedDebugInfo != that.reducedDebugInfo) return false;
        if (!Objects.equals(gameMode, that.gameMode)) return false;
        if (!Objects.equals(difficulty, that.difficulty)) return false;
        return Objects.equals(levelType, that.levelType);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (hardcore ? 1 : 0);
        result = 31 * result + (gameMode != null ? gameMode.hashCode() : 0);
        result = 31 * result + dimension;
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + maxPlayers;
        result = 31 * result + (levelType != null ? levelType.hashCode() : 0);
        result = 31 * result + (reducedDebugInfo ? 1 : 0);
        return result;
    }
}
