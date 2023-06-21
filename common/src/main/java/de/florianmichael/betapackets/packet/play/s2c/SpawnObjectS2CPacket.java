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

public class SpawnObjectS2CPacket extends Packet {

    public int entityId;
    public int type;

    public int x;
    public int y;
    public int z;

    public int pitch;
    public int yaw;

    public int data;

    public int speedX;
    public int speedY;
    public int speedZ;

    public SpawnObjectS2CPacket(final FunctionalByteBuf transformer) {
        this.entityId = transformer.readVarInt();
        this.type = transformer.readByte();

        this.x = transformer.readInt();
        this.y = transformer.readInt();
        this.z = transformer.readInt();

        this.pitch = transformer.readByte();
        this.yaw = transformer.readByte();

        this.data = transformer.readInt();
        if (this.data > 0) {
            this.speedX = transformer.readShort();
            this.speedY = transformer.readShort();
            this.speedZ = transformer.readShort();
        }
    }

    public SpawnObjectS2CPacket(int entityId, int type, int x, int y, int z, int pitch, int yaw, int data, int speedX, int speedY, int speedZ) {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.data = data;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeByte(type);

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeByte(pitch);
        buf.writeByte(yaw);

        buf.writeInt(data);

        if (data > 0) {
            buf.writeShort(speedX);
            buf.writeShort(speedY);
            buf.writeShort(speedZ);
        }
    }

    @Override
    public String toString() {
        return "SpawnObjectS2CPacket{" +
                "entityId=" + entityId +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", data=" + data +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", speedZ=" + speedZ +
                '}';
    }
}
