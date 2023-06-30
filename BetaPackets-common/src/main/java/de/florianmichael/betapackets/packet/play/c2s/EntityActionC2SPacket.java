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
import de.florianmichael.betapackets.model.entity.EntityActionIDs;

import java.util.Objects;

public class EntityActionC2SPacket extends Packet {
    public int entityId;
    public ModelMapper<Integer, EntityActionIDs> entityAction = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, EntityActionIDs::getById);
    public int auxValue;

    public EntityActionC2SPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.entityAction.read(buf);
        this.auxValue = buf.readVarInt();
    }

    public EntityActionC2SPacket(int entityId, EntityActionIDs entityAction, int auxValue) {
        this.entityId = entityId;
        this.entityAction = new ModelMapper<>(FunctionalByteBuf::writeVarInt, entityAction);
        this.auxValue = auxValue;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        this.entityAction.write(buf);
        buf.writeVarInt(this.auxValue);
    }

    @Override
    public String toString() {
        return "EntityActionC2SPacket{" +
                "entityId=" + entityId +
                ", entityAction=" + entityAction +
                ", auxValue=" + auxValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityActionC2SPacket that = (EntityActionC2SPacket) o;

        if (entityId != that.entityId) return false;
        if (auxValue != that.auxValue) return false;
        return Objects.equals(entityAction, that.entityAction);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (entityAction != null ? entityAction.hashCode() : 0);
        result = 31 * result + auxValue;
        return result;
    }
}
