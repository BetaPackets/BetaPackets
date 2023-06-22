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

package de.florianmichael.betapackets.packet.play.c2s;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.PlayerDiggingStatus;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Facing;

import java.util.Objects;

public class PlayerDiggingC2SPacket extends Packet {

    public ModelMapper<Integer, PlayerDiggingStatus> status = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, PlayerDiggingStatus::getById);
    public BlockPos blockPos;
    public ModelMapper<Byte, Facing> facing = new ModelMapper<>(buf -> (byte) buf.readUnsignedByte(), FunctionalByteBuf::writeByte, Facing::getById);

    public PlayerDiggingC2SPacket(final FunctionalByteBuf buf) {
        this.status.read(buf);
        this.blockPos = BlockPos.fromLong(buf.readLong());
        this.facing.read(buf);
    }

    public PlayerDiggingC2SPacket(PlayerDiggingStatus status, BlockPos blockPos, Facing facing) {
        this.status = new ModelMapper<>(FunctionalByteBuf::writeVarInt, status);
        this.blockPos = blockPos;
        this.facing = new ModelMapper<>(FunctionalByteBuf::writeByte, facing);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.status.write(buf);
        buf.writeLong(this.blockPos.toLong());
        this.facing.write(buf);
    }

    @Override
    public String toString() {
        return "PlayerDiggingC2SPacket{" +
                "status=" + status +
                ", blockPos=" + blockPos +
                ", facing=" + facing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerDiggingC2SPacket that = (PlayerDiggingC2SPacket) o;

        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(blockPos, that.blockPos)) return false;
        return Objects.equals(facing, that.facing);
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (blockPos != null ? blockPos.hashCode() : 0);
        result = 31 * result + (facing != null ? facing.hashCode() : 0);
        return result;
    }
}
