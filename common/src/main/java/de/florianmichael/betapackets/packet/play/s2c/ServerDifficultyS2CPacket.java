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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.Difficulty;

import java.util.Objects;

public class ServerDifficultyS2CPacket extends Packet {

    public ModelMapper<Short, Difficulty> difficulty = new ModelMapper<>(FunctionalByteBuf::readUnsignedByte, FunctionalByteBuf::writeByte, Difficulty::getById);

    public ServerDifficultyS2CPacket(final FunctionalByteBuf buf) {
        this.difficulty.read(buf);
    }

    public ServerDifficultyS2CPacket(final Difficulty difficulty) {
        this.difficulty = new ModelMapper<>(FunctionalByteBuf::writeByte, difficulty);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.difficulty.write(buf);
    }

    @Override
    public String toString() {
        return "ServerDifficultyS2CPacket{" +
                "difficulty=" + difficulty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerDifficultyS2CPacket that = (ServerDifficultyS2CPacket) o;

        return Objects.equals(difficulty, that.difficulty);
    }

    @Override
    public int hashCode() {
        return difficulty != null ? difficulty.hashCode() : 0;
    }
}
