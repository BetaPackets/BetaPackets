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

import java.util.Arrays;

public class WindowItemsS2CPacket extends Packet {

    public int windowId;
    public ItemStackV1_3[] itemStacks;

    public WindowItemsS2CPacket(final FunctionalByteBuf buf) {
        this.windowId = buf.readUnsignedByte();

        this.itemStacks = new ItemStackV1_3[buf.readShort()];
        for (int i = 0; i < this.itemStacks.length; i++) {
            this.itemStacks[i] = buf.readItemStack();
        }
    }

    public WindowItemsS2CPacket(int windowId, ItemStackV1_3[] itemStacks) {
        this.windowId = windowId;
        this.itemStacks = itemStacks;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(windowId);
        buf.writeShort(itemStacks.length);

        for (ItemStackV1_3 itemStack : itemStacks) {
            buf.writeItemStack(itemStack);
        }
    }

    @Override
    public String toString() {
        return "WindowItemsS2CPacket{" +
                "windowId=" + windowId +
                ", itemStacks=" + Arrays.toString(itemStacks) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WindowItemsS2CPacket that = (WindowItemsS2CPacket) o;

        if (windowId != that.windowId) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(itemStacks, that.itemStacks);
    }

    @Override
    public int hashCode() {
        int result = windowId;
        result = 31 * result + Arrays.hashCode(itemStacks);
        return result;
    }
}
