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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.entity.EntityStatusOpCodes;

import java.util.Objects;

public class EntityStatusS2CPacket extends EntityS2CPacket {

    public ModelMapper<Byte, EntityStatusOpCodes> entityStatus = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, EntityStatusOpCodes::getById);

    public EntityStatusS2CPacket(FunctionalByteBuf buf) {
        super(buf);
        this.entityStatus.read(buf);
    }

    public EntityStatusS2CPacket(int entityId, EntityStatusOpCodes entityStatus) {
        super(entityId);
        this.entityStatus = new ModelMapper<>(FunctionalByteBuf::writeByte, entityStatus);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        this.entityStatus.write(buf);
    }

    @Override
    public String toString() {
        return "EntityStatusS2CPacket{" +
                "entityStatus=" + entityStatus +
                ", entityId=" + entityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EntityStatusS2CPacket that = (EntityStatusS2CPacket) o;

        return Objects.equals(entityStatus, that.entityStatus);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (entityStatus != null ? entityStatus.hashCode() : 0);
        return result;
    }
}
