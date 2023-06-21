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

public class EntityTeleportS2CPacket extends EntityS2CPacket {

    public int x;
    public int y;
    public int z;

    public byte yaw;
    public byte pitch;

    public boolean onGround;

    public EntityTeleportS2CPacket(FunctionalByteBuf buf) {
        super(buf);

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        this.yaw = buf.readByte();
        this.pitch = buf.readByte();

        this.onGround = buf.readBoolean();
    }

    public EntityTeleportS2CPacket(int entityId, int x, int y, int z, byte yaw, byte pitch, boolean onGround) {
        super(entityId);

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;

        this.onGround = onGround;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeByte(yaw);
        buf.writeByte(pitch);

        buf.writeBoolean(onGround);
    }

    @Override
    public String toString() {
        return "EntityTeleportS2CPacket{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                ", entityId=" + entityId +
                '}';
    }
}
