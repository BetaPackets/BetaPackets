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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.ClickTypes;
import de.florianmichael.betapackets.model.game.item.ItemStackV1_3;

import java.io.IOException;
import java.util.Objects;

public class ClickWindowC2SPacket extends Packet {

    public int windowId;
    public int slotId;
    public int usedButton;
    public short actionNumber;
    public ModelMapper<Byte, ClickTypes> mode = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ClickTypes::getById);
    public ItemStackV1_3 clickedItem;

    public ClickWindowC2SPacket(final FunctionalByteBuf buf) throws IOException {
        this.windowId = buf.readByte();
        this.slotId = buf.readShort();
        this.usedButton = buf.readByte();
        this.actionNumber = buf.readShort();
        this.mode.read(buf);
        this.clickedItem = buf.readItemStack();
    }

    public ClickWindowC2SPacket(int windowId, int slotId, int usedButton, short actionNumber, ClickTypes mode, ItemStackV1_3 clickedItem) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.usedButton = usedButton;
        this.actionNumber = actionNumber;
        this.mode = new ModelMapper<>(FunctionalByteBuf::writeByte, mode);
        this.clickedItem = clickedItem;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(this.windowId);
        buf.writeShort(this.slotId);
        buf.writeByte(this.usedButton);
        buf.writeShort(this.actionNumber);
        this.mode.write(buf);
        buf.writeItemStack(this.clickedItem);
    }

    @Override
    public String toString() {
        return "ClickWindowC2SPacket{" +
                "windowId=" + windowId +
                ", slotId=" + slotId +
                ", usedButton=" + usedButton +
                ", actionNumber=" + actionNumber +
                ", mode=" + mode +
                ", clickedItem=" + clickedItem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClickWindowC2SPacket that = (ClickWindowC2SPacket) o;

        if (windowId != that.windowId) return false;
        if (slotId != that.slotId) return false;
        if (usedButton != that.usedButton) return false;
        if (actionNumber != that.actionNumber) return false;
        if (!Objects.equals(mode, that.mode)) return false;
        return Objects.equals(clickedItem, that.clickedItem);
    }

    @Override
    public int hashCode() {
        int result = windowId;
        result = 31 * result + slotId;
        result = 31 * result + usedButton;
        result = 31 * result + (int) actionNumber;
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (clickedItem != null ? clickedItem.hashCode() : 0);
        return result;
    }
}
