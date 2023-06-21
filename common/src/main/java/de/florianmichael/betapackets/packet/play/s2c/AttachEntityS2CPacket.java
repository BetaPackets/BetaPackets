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

public class AttachEntityS2CPacket extends EntityS2CPacket {

    public int vehicleId;
    public int leash;

    public AttachEntityS2CPacket(FunctionalByteBuf buf) {
        super(buf);

        this.vehicleId = buf.readInt();
        this.leash = buf.readUnsignedByte();
    }

    public AttachEntityS2CPacket(int entityId, int vehicleId, int leash) {
        super(entityId);

        this.vehicleId = vehicleId;
        this.leash = leash;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeInt(vehicleId);
        buf.writeByte(leash);
    }

    public boolean shouldLeashed() {
        return leash == 1;
    }

    @Override
    public String toString() {
        return "AttachEntityS2CPacket{" +
                "vehicleId=" + vehicleId +
                ", leash=" + leash +
                ", entityId=" + entityId +
                '}';
    }
}
