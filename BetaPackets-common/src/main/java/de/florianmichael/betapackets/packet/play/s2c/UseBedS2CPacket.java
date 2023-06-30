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

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.position.BlockPos;

import java.util.Objects;

public class UseBedS2CPacket extends Packet {

    public int entityId;
    public BlockPos position;

    public UseBedS2CPacket(final FunctionalByteBuf buf) {
        this(buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9) ? buf.readVarInt() : buf.readInt(), BlockPos.fromLong(buf.readLong()));
    }

    public UseBedS2CPacket(int entityId, BlockPos position) {
        this.entityId = entityId;
        this.position = position;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeVarInt(entityId);
        } else {
            buf.writeInt(entityId);
        }
        buf.writeLong(position.toLong());
    }

    @Override
    public String toString() {
        return "UseBedS2CPacket{" +
                "entityId=" + entityId +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseBedS2CPacket that = (UseBedS2CPacket) o;

        if (entityId != that.entityId) return false;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
