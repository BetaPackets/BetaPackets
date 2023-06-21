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
import net.lenni0451.mcstructs.text.ATextComponent;

public class OpenWindowS2CPacket extends Packet {

    public int windowId;
    public String inventoryType;
    public ATextComponent windowTitle;
    public int slotCount;
    public int entityId;

    public OpenWindowS2CPacket(final FunctionalByteBuf buf) {
        this.windowId = buf.readUnsignedByte();
        this.inventoryType = buf.readString(32);
        this.windowTitle = buf.readComponent();
        this.slotCount = buf.readUnsignedByte();

        if (this.inventoryType.equals("EntityHorse")) {
            this.entityId = buf.readInt();
        }
    }

    public OpenWindowS2CPacket(int windowId, String inventoryType, ATextComponent windowTitle, int slotCount) {
        this(windowId, inventoryType, windowTitle, slotCount, 0);
    }

    public OpenWindowS2CPacket(int windowId, String inventoryType, ATextComponent windowTitle, int slotCount, int entityId) {
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.slotCount = slotCount;
        this.entityId = entityId;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(windowId);
        buf.writeString(inventoryType);
        buf.writeComponent(windowTitle);
        buf.writeByte(slotCount);

        if (this.inventoryType.equals("EntityHorse")) {
            buf.writeInt(entityId);
        }
    }

    @Override
    public String toString() {
        return "OpenWindowS2CPacket{" +
                "windowId=" + windowId +
                ", inventoryType='" + inventoryType + '\'' +
                ", windowTitle=" + windowTitle +
                ", slotCount=" + slotCount +
                ", entityId=" + entityId +
                '}';
    }
}
