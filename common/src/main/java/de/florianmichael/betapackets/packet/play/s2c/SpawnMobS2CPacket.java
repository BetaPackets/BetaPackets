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

public class SpawnMobS2CPacket extends Packet {

    public int entityId;
    public int type;

    public int x;
    public int y;
    public int z;

    public byte yaw;
    public byte pitch;
    public byte headPitch;

    public int velocityX;
    public int velocityY;
    public int velocityZ;

    public List<Metadata> metadata;

    public SpawnMobS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readVarInt(),
                transformer.readByte() & 255,

                transformer.readInt(),
                transformer.readInt(),
                transformer.readInt(),

                transformer.readByte(),
                transformer.readByte(),
                transformer.readByte(),

                transformer.readShort(),
                transformer.readShort(),
                transformer.readShort(),

                transformer.readMetadata()
        );
    }

    public SpawnMobS2CPacket(int entityId, int type, int x, int y, int z, byte yaw, byte pitch, byte headPitch, int velocityX, int velocityY, int velocityZ, List<Metadata> metadata) {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.metadata = metadata;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeByte(type);

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeByte(headPitch);

        buf.writeShort(velocityX);
        buf.writeShort(velocityY);
        buf.writeShort(velocityZ);

        buf.writeMetadata(metadata);
    }

    @Override
    public String toString() {
        return "SpawnMobS2CPacket{" +
                "entityId=" + entityId +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", headPitch=" + headPitch +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                ", metadata=" + metadata +
                '}';
    }
}
