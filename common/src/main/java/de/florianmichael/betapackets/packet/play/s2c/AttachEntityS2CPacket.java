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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;

public class AttachEntityS2CPacket extends Packet {

    public int entityId;
    public int vehicleId;
    public int leash_1_8;

    public AttachEntityS2CPacket(FunctionalByteBuf buf) {
        this.entityId = buf.readInt();
        this.vehicleId = buf.readInt();
        if (buf.getProtocolVersion().isOlderThanOrEqualTo(ProtocolCollection.R1_8)) {
            this.leash_1_8 = buf.readUnsignedByte();
        }
    }

    public AttachEntityS2CPacket(int entityId, int vehicleId) {
        this.entityId = entityId;
        this.vehicleId = vehicleId;
    }

    public AttachEntityS2CPacket(int entityId, int vehicleId, int leash) {
        this.entityId = entityId;
        this.vehicleId = vehicleId;
        this.leash_1_8 = leash;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(entityId);
        buf.writeInt(vehicleId);
        if (buf.getProtocolVersion().isOlderThanOrEqualTo(ProtocolCollection.R1_8)) {
            buf.writeByte(leash_1_8);
        }
    }

    public boolean shouldLeashed() {
        return leash_1_8 == 1;
    }

    @Override
    public String toString() {
        return "AttachEntityS2CPacket{" +
                "entityId=" + entityId +
                ", vehicleId=" + vehicleId +
                ", leash=" + leash_1_8 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttachEntityS2CPacket that = (AttachEntityS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (vehicleId != that.vehicleId) return false;
        return leash_1_8 == that.leash_1_8;
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + vehicleId;
        result = 31 * result + leash_1_8;
        return result;
    }
}
