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
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.world.Difficulty;
import de.florianmichael.betapackets.model.game.GameMode;
import de.florianmichael.betapackets.model.world.LevelType;

import java.util.Objects;

public class RespawnS2CPacket extends Packet {

    public int dimension;
    public ModelMapper<Short, Difficulty> difficulty = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, Difficulty::getById);
    public ModelMapper<Short, GameMode> gameMode = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, GameMode::getById);
    public ModelMapper<String, LevelType> levelType = new ModelMapper<>(buf -> buf.readString(16), FunctionalByteBuf::writeString, LevelType::getByType);

    public RespawnS2CPacket(final FunctionalByteBuf buf) {
        this.dimension = buf.readInt();
        this.difficulty.read(buf);
        this.gameMode.read(buf);
        this.levelType.read(buf);

        if (this.levelType.mappedValue == null) {
            this.levelType.mappedValue = LevelType.DEFAULT;
        }
    }

    public RespawnS2CPacket(int dimension, Difficulty difficulty, GameMode gameMode, LevelType levelType) {
        this.dimension = dimension;
        this.difficulty = new ModelMapper<>(FunctionalByteBuf::writeByte, difficulty);
        this.gameMode = new ModelMapper<>(FunctionalByteBuf::writeByte, gameMode);
        this.levelType = new ModelMapper<>(FunctionalByteBuf::writeString, levelType);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(dimension);
        this.difficulty.write(buf);
        this.gameMode.write(buf);
        this.levelType.write(buf);
    }

    @Override
    public String toString() {
        return "RespawnS2CPacket{" +
                "dimension=" + dimension +
                ", difficulty=" + difficulty +
                ", gameMode=" + gameMode +
                ", levelType=" + levelType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespawnS2CPacket that = (RespawnS2CPacket) o;

        if (dimension != that.dimension) return false;
        if (!Objects.equals(difficulty, that.difficulty)) return false;
        if (!Objects.equals(gameMode, that.gameMode)) return false;
        return Objects.equals(levelType, that.levelType);
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (gameMode != null ? gameMode.hashCode() : 0);
        result = 31 * result + (levelType != null ? levelType.hashCode() : 0);
        return result;
    }
}
