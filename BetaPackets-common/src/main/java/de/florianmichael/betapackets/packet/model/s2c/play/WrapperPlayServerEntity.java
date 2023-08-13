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

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import de.florianmichael.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayServerEntity extends PacketWrapper<WrapperPlayServerEntity> {

    private int entityId;

    private double deltaX, deltaY, deltaZ;
    private float yaw, pitch;
    private boolean onGround;

    private boolean positionUpdate, lookUpdate;

    public WrapperPlayServerEntity(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerEntity(int entityId) {
        this(entityId, 0, 0, 0, 0, 0, false, false, false);
    }

    public WrapperPlayServerEntity(int entityId, float yaw, float pitch, boolean onGround) {
        this(entityId, 0, 0, 0, yaw, pitch, onGround, false, true);
    }

    public WrapperPlayServerEntity(int entityId, double deltaX, double deltaY, double deltaZ, boolean onGround) {
        this(entityId, deltaX, deltaY, deltaZ, 0, 0, onGround, true, false);
    }

    public WrapperPlayServerEntity(int entityId, double deltaX, double deltaY, double deltaZ, float yaw, float pitch, boolean onGround) {
        this(entityId, deltaX, deltaY, deltaZ, yaw, pitch, onGround, true, true);
    }

    public WrapperPlayServerEntity(int entityId, double deltaX, double deltaY, double deltaZ, float yaw, float pitch, boolean onGround, boolean positionUpdate, boolean lookUpdate) {
        super(positionUpdate && lookUpdate ? PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION : positionUpdate
                ? PacketType.Play.Server.UPDATE_ENTITY_POSITION : lookUpdate ? PacketType.Play.Server.UPDATE_ENTITY_ROTATION
                : PacketType.Play.Server.UPDATE_ENTITY);
        this.entityId = entityId;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.positionUpdate = positionUpdate;
        this.lookUpdate = lookUpdate;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);

        boolean updatedSomething = false;

        if (type == PacketType.Play.Server.UPDATE_ENTITY_POSITION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION) {
            buf.writeShort((short) Math.round(deltaX * 4096.0));
            buf.writeShort((short) Math.round(deltaY * 4096.0));
            buf.writeShort((short) Math.round(deltaZ * 4096.0));
            updatedSomething = true;
        }
        if (type == PacketType.Play.Server.UPDATE_ENTITY_ROTATION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION) {
            buf.writeByte((byte) Math.round(yaw * 256.0f / 360.0f));
            buf.writeByte((byte) Math.round(pitch * 256.0f / 360.0f));
            updatedSomething = true;
        }
        if (updatedSomething)
            onGround = buf.readBoolean();
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        positionUpdate = type == PacketType.Play.Server.UPDATE_ENTITY_POSITION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION;
        lookUpdate = type == PacketType.Play.Server.UPDATE_ENTITY_ROTATION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION;

        buf.writeVarInt(entityId);
        if (positionUpdate) {
            deltaX = (double) buf.readShort() / 4096.0;
            deltaY = (double) buf.readShort() / 4096.0;
            deltaZ = (double) buf.readShort() / 4096.0;
        }
        if (lookUpdate) {
            yaw = (float) (buf.readByte() * 360) / 256.0f;
            pitch = (float) (buf.readByte() * 360) / 256.0f;
        }

        if (positionUpdate || lookUpdate)
            onGround = buf.readBoolean();
    }

    @Override
    public void copyFrom(WrapperPlayServerEntity base) {
        entityId = base.entityId;
        deltaX = base.deltaX;
        deltaY = base.deltaY;
        deltaZ = base.deltaZ;
        yaw = base.yaw;
        pitch = base.pitch;
        onGround = base.onGround;
        positionUpdate = base.positionUpdate;
        lookUpdate = base.lookUpdate;
    }

    public static boolean isEntityPacket(Packet type) {
        return type == PacketType.Play.Server.UPDATE_ENTITY
                || type == PacketType.Play.Server.UPDATE_ENTITY_ROTATION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION
                || type == PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public void setDeltaZ(double deltaZ) {
        this.deltaZ = deltaZ;
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

    public boolean isPositionUpdate() {
        return positionUpdate;
    }

    public boolean isLookUpdate() {
        return lookUpdate;
    }

    @Override
    public String toString() {
        if (positionUpdate && lookUpdate) {
            return "WrapperPlayServerEntity{" +
                    "entityId=" + entityId +
                    ", deltaX=" + deltaX +
                    ", deltaY=" + deltaY +
                    ", deltaZ=" + deltaZ +
                    ", yaw=" + yaw +
                    ", pitch=" + pitch +
                    ", onGround=" + onGround +
                    '}';
        } else if (positionUpdate) {
            return "WrapperPlayServerEntity{" +
                    "entityId=" + entityId +
                    ", deltaX=" + deltaX +
                    ", deltaY=" + deltaY +
                    ", deltaZ=" + deltaZ +
                    ", onGround=" + onGround +
                    '}';
        } else if (lookUpdate) {
            return "WrapperPlayServerEntity{" +
                    "entityId=" + entityId +
                    ", yaw=" + yaw +
                    ", pitch=" + pitch +
                    ", onGround=" + onGround +
                    '}';
        }
        return "WrapperPlayServerEntity{" +
                "entityId=" + entityId +
                '}';
    }
}
