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
import de.florianmichael.betapackets.model.item.ItemStackV1_3;

import java.io.IOException;
import java.util.Objects;

public class EntityEquipmentS2CPacket extends Packet {

    public int entityId;
    public int slot;
    public ItemStackV1_3 item;

    public EntityEquipmentS2CPacket(final FunctionalByteBuf transformer) throws IOException {
        this(
                transformer.readVarInt(),
                transformer.readShort(),
                transformer.readItemStack()
        );
    }

    public EntityEquipmentS2CPacket(int entityId, int slot, ItemStackV1_3 item) {
        this.entityId = entityId;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        buf.writeShort(this.slot);
        buf.writeItemStack(this.item);
    }

    @Override
    public String toString() {
        return "EntityEquipmentS2CPacket{" +
                "entityId=" + entityId +
                ", slot=" + slot +
                ", item=" + item +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityEquipmentS2CPacket that = (EntityEquipmentS2CPacket) o;

        if (entityId != that.entityId) return false;
        if (slot != that.slot) return false;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + slot;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
