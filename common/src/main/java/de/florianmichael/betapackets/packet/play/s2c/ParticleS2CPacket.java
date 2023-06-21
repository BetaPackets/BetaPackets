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
import de.florianmichael.betapackets.model.potion.ParticleTypes;

import java.util.Arrays;

public class ParticleS2CPacket extends Packet {

    public ParticleTypes particleType;
    public boolean longDistance;

    public float x;
    public float y;
    public float z;

    public float offsetX;
    public float offsetY;
    public float offsetZ;

    public float particleData;
    public int particleCount;

    public int[] data;

    public ParticleS2CPacket(final FunctionalByteBuf buf) {
        this.particleType = ParticleTypes.getById(buf.getProtocolVersion(), buf.readInt());
        if (this.particleType == null) {
            this.particleType = ParticleTypes.BARRIER;
        }
        this.longDistance = buf.readBoolean();

        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();

        this.offsetX = buf.readFloat();
        this.offsetY = buf.readFloat();
        this.offsetZ = buf.readFloat();

        this.particleData = buf.readFloat();
        this.particleCount = buf.readInt();

        this.data = new int[this.particleType.argumentCount];
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = buf.readVarInt();
        }
    }

    public ParticleS2CPacket(ParticleTypes particleType, boolean longDistance, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, int[] data) {
        this.particleType = particleType;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.particleCount = particleCount;
        this.data = data;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(this.particleType.getId(buf.getProtocolVersion()));
        buf.writeBoolean(this.longDistance);

        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        buf.writeFloat(this.z);

        buf.writeFloat(this.offsetX);
        buf.writeFloat(this.offsetY);
        buf.writeFloat(this.offsetZ);

        buf.writeFloat(this.particleData);
        buf.writeInt(this.particleCount);

        for (int datum : this.data) {
            buf.writeVarInt(datum);
        }
    }

    @Override
    public String toString() {
        return "ParticleS2CPacket{" +
                "particleType=" + particleType +
                ", longDistance=" + longDistance +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", offsetZ=" + offsetZ +
                ", particleData=" + particleData +
                ", particleCount=" + particleCount +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
