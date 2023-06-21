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

import de.florianmichael.betapackets.base.PacketTransformer;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.game.Difficulty;
import de.florianmichael.betapackets.model.game.GameMode;
import de.florianmichael.betapackets.model.game.LevelType;

public class RespawnS2CPacket extends Packet {

    public int dimension;
    public Difficulty difficulty;
    public GameMode gameMode;
    public LevelType levelType;

    public RespawnS2CPacket(final PacketTransformer transformer) {
        this(
                transformer.readVarInt(),
                Difficulty.byId(transformer.readUnsignedByte()),
                GameMode.byId(transformer.readUnsignedByte()),
                LevelType.byType(transformer.readString(16))
        );
        if (levelType == null) levelType = LevelType.DEFAULT;
    }

    public RespawnS2CPacket(int dimension, Difficulty difficulty, GameMode gameMode, LevelType levelType) {
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.levelType = levelType;
    }

    @Override
    public void write(PacketTransformer buf) throws Exception {
        buf.writeVarInt(dimension);
        buf.writeByte(difficulty.ordinal());
        buf.writeByte(gameMode.ordinal());
        buf.writeString(levelType.getName());
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
}
