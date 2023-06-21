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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.metadata.Metadata;

import java.util.List;
import java.util.UUID;

public class SpawnPlayerS2CPacket extends Packet {

    public int entityId;
    public UUID playerId;

    public int x;
    public int y;
    public int z;

    public byte yaw;
    public byte pitch;

    public int currentItem;

    public List<Metadata> metadata;

    public SpawnPlayerS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readVarInt(),
                new UUID(transformer.readLong(), transformer.readLong()),

                transformer.readInt(),
                transformer.readInt(),
                transformer.readInt(),

                transformer.readByte(),
                transformer.readByte(),

                transformer.readShort(),

                transformer.readMetadata()
        );
    }

    public SpawnPlayerS2CPacket(int entityId, UUID playerId, int x, int y, int z, byte yaw, byte pitch, int currentItem, List<Metadata> metadata) {
        this.entityId = entityId;
        this.playerId = playerId;

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;

        this.currentItem = currentItem;

        this.metadata = metadata;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);

        buf.writeLong(playerId.getMostSignificantBits());
        buf.writeLong(playerId.getLeastSignificantBits());

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeByte(yaw);
        buf.writeByte(pitch);

        buf.writeShort(currentItem);

        buf.writeMetadata(metadata);
    }

    @Override
    public String toString() {
        return "SpawnPlayerS2CPacket{" +
                "entityId=" + entityId +
                ", playerId=" + playerId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", currentItem=" + currentItem +
                ", metadata=" + metadata +
                '}';
    }
}
