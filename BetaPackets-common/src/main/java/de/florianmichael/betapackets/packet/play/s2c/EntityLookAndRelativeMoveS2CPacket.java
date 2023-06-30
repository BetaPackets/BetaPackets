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
import de.florianmichael.betapackets.model.base.ProtocolCollection;

public class EntityLookAndRelativeMoveS2CPacket extends EntityS2CPacket {

    public short deltaX;
    public short deltaY;
    public short deltaZ;

    public byte yaw;
    public byte pitch;

    public boolean onGround;

    public EntityLookAndRelativeMoveS2CPacket(FunctionalByteBuf buf) {
        super(buf);

        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.deltaX = buf.readShort();
            this.deltaY = buf.readShort();
            this.deltaZ = buf.readShort();
        } else {
            this.deltaX = buf.readByte();
            this.deltaY = buf.readByte();
            this.deltaZ = buf.readByte();
        }

        this.yaw = buf.readByte();
        this.pitch = buf.readByte();

        this.onGround = buf.readBoolean();
    }

    public EntityLookAndRelativeMoveS2CPacket(int entityId, short deltaX, short deltaY, short deltaZ, byte yaw, byte pitch, boolean onGround) {
        super(entityId);

        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;

        this.yaw = yaw;
        this.pitch = pitch;

        this.onGround = onGround;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeShort(deltaX);
            buf.writeShort(deltaY);
            buf.writeShort(deltaZ);
        } else {
            buf.writeByte(deltaX);
            buf.writeByte(deltaY);
            buf.writeByte(deltaZ);
        }

        buf.writeByte(yaw);
        buf.writeByte(pitch);

        buf.writeBoolean(onGround);
    }

    @Override
    public String toString() {
        return "EntityLookAndRelativeMoveS2CPacket{" +
                "deltaX=" + deltaX +
                ", deltaY=" + deltaY +
                ", deltaZ=" + deltaZ +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                ", entityId=" + entityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityLookAndRelativeMoveS2CPacket that = (EntityLookAndRelativeMoveS2CPacket) o;

        if (deltaX != that.deltaX) return false;
        if (deltaY != that.deltaY) return false;
        if (deltaZ != that.deltaZ) return false;
        if (yaw != that.yaw) return false;
        if (pitch != that.pitch) return false;
        return onGround == that.onGround;
    }

    @Override
    public int hashCode() {
        int result = deltaX;
        result = 31 * result + (int) deltaY;
        result = 31 * result + (int) deltaZ;
        result = 31 * result + (int) yaw;
        result = 31 * result + (int) pitch;
        result = 31 * result + (onGround ? 1 : 0);
        return result;
    }
}
