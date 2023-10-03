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

public class WrapperPlayServerEntityVelocity extends PacketWrapper<WrapperPlayServerEntityVelocity> {

    private int entityId;
    private double velocityX, velocityY, velocityZ;

    public WrapperPlayServerEntityVelocity(int entityId, double velocityX, double velocityY, double velocityZ) {
        super(PacketType.Play.Server.SET_ENTITY_VELOCITY);
        this.entityId = entityId;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public WrapperPlayServerEntityVelocity(PacketEvent event) throws IOException {
        super(event);
    }


    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);
        buf.writeShort((int) Math.round(velocityX * 8000));
        buf.writeShort((int) Math.round(velocityY * 8000));
        buf.writeShort((int) Math.round(velocityZ * 8000));
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        velocityX = (double) buf.readShort() / 8000.0;
        velocityX = (double) buf.readShort() / 8000.0;
        velocityX = (double) buf.readShort() / 8000.0;
    }

    @Override
    public void copyFrom(WrapperPlayServerEntityVelocity base) {
        entityId = base.entityId;
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

    @Override
    public String toString() {
        return "WrapperPlayServerEntityVelocity{" +
                "entityId=" + entityId +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                ", velocityZ=" + velocityZ +
                '}';
    }
}
