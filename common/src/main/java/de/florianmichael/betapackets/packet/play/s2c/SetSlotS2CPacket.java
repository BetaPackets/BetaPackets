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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.item.ItemStackV1_3;

import java.util.Objects;

public class SetSlotS2CPacket extends Packet {

    public int windowId;
    public int slot;
    public ItemStackV1_3 itemStack;

    public SetSlotS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readByte(), buf.readShort(), buf.readItemStack());
    }

    public SetSlotS2CPacket(int windowId, int slot, ItemStackV1_3 itemStack) {
        this.windowId = windowId;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(windowId);
        buf.writeShort(slot);
        buf.writeItemStack(itemStack);
    }

    @Override
    public String toString() {
        return "SetSlotS2CPacket{" +
                "windowId=" + windowId +
                ", slot=" + slot +
                ", itemStack=" + itemStack +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetSlotS2CPacket that = (SetSlotS2CPacket) o;

        if (windowId != that.windowId) return false;
        if (slot != that.slot) return false;
        return Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        int result = windowId;
        result = 31 * result + slot;
        result = 31 * result + (itemStack != null ? itemStack.hashCode() : 0);
        return result;
    }
}
