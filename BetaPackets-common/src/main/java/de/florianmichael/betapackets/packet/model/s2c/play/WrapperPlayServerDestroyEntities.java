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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;

public class WrapperPlayServerDestroyEntities extends PacketWrapper<WrapperPlayServerDestroyEntities> {

    private TIntList entityIds;

    public WrapperPlayServerDestroyEntities(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerDestroyEntities(TIntList entityIds) {
        this.entityIds = entityIds;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityIds.size());
        for (int i = 0; i < entityIds.size(); i++)
            buf.writeVarInt(entityIds.get(i));
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        int amount = buf.readVarInt();
        entityIds = new TIntArrayList(amount);
        for (int i = 0; i < amount; i++)
            entityIds.add(buf.readVarInt());
    }

    @Override
    public void copyFrom(WrapperPlayServerDestroyEntities base) {
        entityIds = base.entityIds;
    }

    public TIntList getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(TIntList entityIds) {
        this.entityIds = entityIds;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerDestroyEntities{" +
                "entityIds=" + entityIds +
                '}';
    }
}
