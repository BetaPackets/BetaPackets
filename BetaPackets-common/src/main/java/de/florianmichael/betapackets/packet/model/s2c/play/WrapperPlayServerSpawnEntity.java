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
import de.florianmichael.betapackets.model.entity.Entity;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import io.netty.handler.codec.EncoderException;

import java.io.IOException;
import java.util.UUID;

public class WrapperPlayServerSpawnEntity extends PacketWrapper<WrapperPlayServerSpawnEntity> {

    private int entityId;
    private UUID uuid;
    private Entity entity;
    private double x, y, z;
    private double velocityX, velocityY, velocityZ;
    private float yaw, pitch, headYaw;
    private int data;

    public WrapperPlayServerSpawnEntity(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerSpawnEntity(int entityId, UUID uuid, Entity entity, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float yaw, float pitch, float headYaw, int data) {
        this.entityId = entityId;
        this.uuid = uuid;
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headYaw = headYaw;
        this.data = data;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        if (entity == null || entity.getEntityId() == -1) throw new EncoderException("unknown entity-id for "+ entity);
        buf.writeVarInt(entityId);
        buf.writeUUID(uuid);
        buf.writeVarInt(entity.getEntityId());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeByte((byte) Math.round(pitch * 256.0f / 360.0f));
        buf.writeByte((byte) Math.round(yaw * 256.0f / 360.0f));
        buf.writeByte((byte) Math.round(headYaw * 256.0f / 360.0f));
        buf.writeVarInt(data);
        buf.writeShort((short) Math.round(velocityX * 8000.0));
        buf.writeShort((short) Math.round(velocityY * 8000.0));
        buf.writeShort((short) Math.round(velocityZ * 8000.0));
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        uuid = buf.readUUID();
        entity = buf.getProtocolVersion().getEntityMapping().getByEntityId(buf.readVarInt());
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        pitch = (float) (buf.readByte() * 360) / 256.0f;
        yaw = (float) (buf.readByte() * 360) / 256.0f;
        headYaw = (float) (buf.readByte() * 360) / 256.0f;
        data = buf.readVarInt();
        velocityX = (double) buf.readShort() / 8000.0;
        velocityY = (double) buf.readShort() / 8000.0;
        velocityZ = (double) buf.readShort() / 8000.0;
    }

    @Override
    public void copyFrom(WrapperPlayServerSpawnEntity base) {
        entityId = base.entityId;
        uuid = base.uuid;
        x = base.x;
        y = base.y;
        z = base.z;
        pitch = base.pitch;
        yaw = base.yaw;
        headYaw = base.headYaw;
        data = base.data;
        velocityX = base.velocityX;
        velocityY = base.velocityY;
        velocityZ = base.velocityZ;
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

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public void setVelocityZ(double velocityZ) {
        this.velocityZ = velocityZ;
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

    public float getHeadYaw() {
        return headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerSpawnEntity{" +
                "entityId=" + entityId +
                ", uuid=" + uuid +
                ", entity=" + entity +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", headYaw=" + headYaw +
                ", data=" + data +
                '}';
    }
}
