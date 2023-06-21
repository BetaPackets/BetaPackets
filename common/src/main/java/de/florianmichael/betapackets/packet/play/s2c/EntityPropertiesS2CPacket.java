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
import de.florianmichael.betapackets.model.entity.properties.EntityProperty;
import de.florianmichael.betapackets.model.entity.properties.EntityPropertyModifier;
import de.florianmichael.betapackets.model.entity.properties.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityPropertiesS2CPacket extends EntityS2CPacket {

    public List<EntityProperty> entityProperties;

    public EntityPropertiesS2CPacket(FunctionalByteBuf buf) {
        super(buf);
        entityProperties = new ArrayList<>();

        final int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            final String key = buf.readString(64);
            final double value = buf.readDouble();

            final List<EntityPropertyModifier> entityPropertyModifiers = new ArrayList<>();
            final int modifierSize = buf.readVarInt();
            for (int j = 0; j < modifierSize; j++) {
                final UUID uuid = buf.readUUID();
                final double amount = buf.readDouble();
                final Operation operation = Operation.getById(buf.readByte());

                entityPropertyModifiers.add(new EntityPropertyModifier(uuid, amount, operation));
            }

            entityProperties.add(new EntityProperty(key, value, entityPropertyModifiers));
        }
    }

    public EntityPropertiesS2CPacket(int entityId, List<EntityProperty> entityProperties) {
        super(entityId);

        this.entityProperties = entityProperties;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeInt(entityProperties.size());
        for (EntityProperty entityProperty : entityProperties) {
            buf.writeString(entityProperty.key);
            buf.writeDouble(entityProperty.value);

            buf.writeVarInt(entityProperty.entityPropertyModifiers.size());
            for (EntityPropertyModifier entityPropertyModifier : entityProperty.entityPropertyModifiers) {
                buf.writeUUID(entityPropertyModifier.uuid);

                buf.writeDouble(entityPropertyModifier.amount);
                buf.writeByte(entityPropertyModifier.operation.ordinal());
            }
        }
    }

    @Override
    public String toString() {
        return "EntityPropertiesS2CPacket{" +
                "entityProperties=" + entityProperties +
                ", entityId=" + entityId +
                '}';
    }
}