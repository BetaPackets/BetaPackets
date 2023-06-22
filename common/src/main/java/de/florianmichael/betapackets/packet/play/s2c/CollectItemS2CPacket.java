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
import de.florianmichael.betapackets.base.Packet;

public class CollectItemS2CPacket extends Packet {

    public int collectedEntityId;
    public int collectorEntityId;

    public CollectItemS2CPacket(final FunctionalByteBuf transformer) {
        this(transformer.readVarInt(), transformer.readVarInt());
    }

    public CollectItemS2CPacket(int collectedEntityId, int collectorEntityId) {
        this.collectedEntityId = collectedEntityId;
        this.collectorEntityId = collectorEntityId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(collectedEntityId);
        buf.writeVarInt(collectorEntityId);
    }

    @Override
    public String toString() {
        return "CollectItemS2CPacket{" +
                "collectedEntityId=" + collectedEntityId +
                ", collectorEntityId=" + collectorEntityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectItemS2CPacket that = (CollectItemS2CPacket) o;

        if (collectedEntityId != that.collectedEntityId) return false;
        return collectorEntityId == that.collectorEntityId;
    }

    @Override
    public int hashCode() {
        int result = collectedEntityId;
        result = 31 * result + collectorEntityId;
        return result;
    }
}
