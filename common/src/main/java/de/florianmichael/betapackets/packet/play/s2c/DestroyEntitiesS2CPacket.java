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

import de.florianmichael.betapackets.base.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

import java.util.Arrays;

public class DestroyEntitiesS2CPacket extends Packet {

    public int[] entityIds;

    public DestroyEntitiesS2CPacket(final FunctionalByteBuf buf) {
        entityIds = new int[buf.readVarInt()];
        for (int i = 0; i < entityIds.length; i++) {
            entityIds[i] = buf.readVarInt();
        }
    }

    public DestroyEntitiesS2CPacket(int[] entityIds) {
        this.entityIds = entityIds;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(entityIds.length);
        for (int entityId : entityIds) {
            buf.writeVarInt(entityId);
        }
    }

    @Override
    public String toString() {
        return "DestroyEntitiesS2CPacket{" +
                "entityIds=" + Arrays.toString(entityIds) +
                '}';
    }
}
