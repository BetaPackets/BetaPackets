/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;

public class WrapperPlayServerRemoveEntities extends PacketWrapper<WrapperPlayServerRemoveEntities> {

    private TIntList entityIds;

    public WrapperPlayServerRemoveEntities(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerRemoveEntities(TIntList entityIds) {
        super(PacketType.Play.Server.REMOVE_ENTITIES);
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
    public void copyFrom(WrapperPlayServerRemoveEntities base) {
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
