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

public class EntityHeadLookS2CPacket extends EntityS2CPacket {

    public byte yaw;

    public EntityHeadLookS2CPacket(FunctionalByteBuf buf) {
        super(buf);

        this.yaw = buf.readByte();
    }

    public EntityHeadLookS2CPacket(int entityId, byte yaw) {
        super(entityId);

        this.yaw = yaw;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeByte(yaw);
    }

    @Override
    public String toString() {
        return "EntityHeadLookS2CPacket{" +
                "yaw=" + yaw +
                ", entityId=" + entityId +
                '}';
    }
}
