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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;

public class WrapperPlayServerEntityTeleport extends PacketWrapper<WrapperPlayServerEntityTeleport> {

    private int entityId;
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public WrapperPlayServerEntityTeleport(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerEntityTeleport(int entityId, double x, double y, double z, float yaw, float pitch) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeByte((byte) Math.round(yaw * 256.0f / 360.0f));
        buf.writeByte((byte) Math.round(pitch * 256.0f / 360.0f));
        buf.writeBoolean(onGround);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        yaw = (float) (buf.readByte() * 360) / 256.0f;
        pitch = (float) (buf.readByte() * 360) / 256.0f;
        onGround = buf.readBoolean();
    }

    @Override
    public void copyFrom(WrapperPlayServerEntityTeleport base) {
        x = base.x;
        y = base.y;
        z = base.z;
        yaw = base.yaw;
        pitch = base.pitch;
        onGround = base.onGround;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
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

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerEntityTeleport{" +
                "entityId=" + entityId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                '}';
    }
}
