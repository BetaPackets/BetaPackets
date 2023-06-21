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
import de.florianmichael.betapackets.model.game.Difficulty;
import de.florianmichael.betapackets.model.game.GameMode;
import de.florianmichael.betapackets.model.game.LevelType;

public class JoinGameS2CPacket extends Packet {

    public int entityId;
    public boolean hardcore;
    public GameMode gameMode;
    public int dimension;
    public Difficulty difficulty;
    public int maxPlayers;
    public LevelType levelType;
    public boolean reducedDebugInfo;

    public JoinGameS2CPacket(final FunctionalByteBuf transformer) {
        this.entityId = transformer.readInt();

        int mask = transformer.readUnsignedByte();
        this.hardcore = (mask & 8) == 0;
        mask &= -9;

        this.gameMode = GameMode.byId(mask);
        this.dimension = transformer.readByte();
        this.difficulty = Difficulty.byId(transformer.readUnsignedByte());
        this.maxPlayers = transformer.readUnsignedByte();
        this.levelType = LevelType.byType(transformer.readString(16));
        if (this.levelType == null) {
            this.levelType = LevelType.DEFAULT;
        }
        this.reducedDebugInfo = transformer.readBoolean();
    }

    public JoinGameS2CPacket(int entityId, boolean hardcore, GameMode gameMode, int dimension, Difficulty difficulty, int maxPlayers, LevelType levelType, boolean reducedDebugInfo) {
        this.entityId = entityId;
        this.hardcore = hardcore;
        this.gameMode = gameMode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reducedDebugInfo = reducedDebugInfo;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeInt(this.entityId);

        int i = this.gameMode.getId();
        if (this.hardcore) i |= 8;
        buf.writeByte(i);

        buf.writeByte(this.dimension);
        buf.writeByte(this.difficulty.ordinal());
        buf.writeByte(this.maxPlayers);
        buf.writeString(this.levelType.getName());
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
}
