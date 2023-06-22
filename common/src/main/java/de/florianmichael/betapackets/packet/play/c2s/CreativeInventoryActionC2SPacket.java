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

package de.florianmichael.betapackets.packet.play.c2s;

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.item.ItemStackV1_3;

import java.util.Objects;

public class CreativeInventoryActionC2SPacket extends Packet {

    public int slotId;
    public ItemStackV1_3 itemStack;

    public CreativeInventoryActionC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readShort(), buf.readItemStack());
    }

    public CreativeInventoryActionC2SPacket(int slotId, ItemStackV1_3 itemStack) {
        this.slotId = slotId;
        this.itemStack = itemStack;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeShort(this.slotId);
        buf.writeItemStack(this.itemStack);
    }

    @Override
    public String toString() {
        return "CreativeInventoryActionC2SPacket{" +
                "slotId=" + slotId +
                ", itemStack=" + itemStack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreativeInventoryActionC2SPacket that = (CreativeInventoryActionC2SPacket) o;

        if (slotId != that.slotId) return false;
        return Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        int result = slotId;
        result = 31 * result + (itemStack != null ? itemStack.hashCode() : 0);
        return result;
    }
}
