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

package de.florianmichael.betapackets.packet.model.play.s2c;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.UUID;

public class WrapperPlayServerSpawnPlayer extends PacketWrapper<WrapperPlayServerSpawnPlayer> {

    private int entityId;
    private UUID uuid;
    private double x, y, z;
    private float yaw, pitch;

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);
        buf.writeUUID(uuid);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeByte((byte) Math.round(yaw * 256.0f / 360.0f));
        buf.writeByte((byte) Math.round(pitch * 256.0f / 360.0f));
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        uuid = buf.readUUID();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        z = buf.readDouble();
        yaw = (float) (buf.readByte() * 360) / 256.0f;
        pitch = (float) (buf.readByte() * 360) / 256.0f;
    }

    @Override
    public void copyFrom(WrapperPlayServerSpawnPlayer base) {
        entityId = base.entityId;
        uuid = base.uuid;
        x = base.x;
        y = base.y;
        z = base.z;
        yaw = base.yaw;
        pitch = base.pitch;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerSpawnPlayer{" +
                "entityId=" + entityId +
                ", uuid=" + uuid +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }
}
