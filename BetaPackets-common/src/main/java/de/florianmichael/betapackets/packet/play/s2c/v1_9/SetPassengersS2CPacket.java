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

package de.florianmichael.betapackets.packet.play.s2c.v1_9;

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

import java.util.Arrays;

public class SetPassengersS2CPacket extends Packet {

    public int entityId;
    public int[] passengerIds;

    public SetPassengersS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readVarInt(), buf.readVarIntArray());
    }

    public SetPassengersS2CPacket(int entityId, int[] passengerIds) {
        this.entityId = entityId;
        this.passengerIds = passengerIds;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityId);
        buf.writeVarIntArray(passengerIds);
    }

    @Override
    public String toString() {
        return "SetPassengersS2CPacket{" +
                "entityId=" + entityId +
                ", passengerIds=" + Arrays.toString(passengerIds) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetPassengersS2CPacket that = (SetPassengersS2CPacket) o;

        if (entityId != that.entityId) return false;
        return Arrays.equals(passengerIds, that.passengerIds);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + Arrays.hashCode(passengerIds);
        return result;
    }
}
