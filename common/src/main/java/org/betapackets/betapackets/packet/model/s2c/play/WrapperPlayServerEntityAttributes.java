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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.entity.attribute.AttributeModifier;
import org.betapackets.betapackets.model.entity.attribute.EntityAttribute;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WrapperPlayServerEntityAttributes extends PacketWrapper<WrapperPlayServerEntityAttributes> {

    private int entityId;
    private List<SerializedAttribute> attributes;

    public WrapperPlayServerEntityAttributes(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);
        buf.writeVarInt(attributes.size());
        for (SerializedAttribute attribute : attributes) {
            buf.writeIdentifier(attribute.getAttribute().getIdentifier());
            buf.writeDouble(attribute.getBaseValue());
            buf.writeVarInt(attribute.getModifiers().size());
            for (AttributeModifier modifier : attribute.getModifiers()) {
                buf.writeUUID(modifier.getUuid());
                buf.writeDouble(modifier.getAmount());
                buf.writeByte(modifier.getOperation().getId());
            }
        }
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();

        int amount = buf.readVarInt();
        attributes = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            EntityAttribute attribute = EntityAttribute.getByIdentifier(buf.readIdentifier());
            double value = buf.readDouble();

            int modifierAmount = buf.readVarInt();
            List<AttributeModifier> modifiers = new ArrayList<>(modifierAmount);
            for (int j = 0; j < modifierAmount; j++) {
                modifiers.add(new AttributeModifier(buf.readUUID(), buf.readDouble(), AttributeModifier.Operation.getById(buf.readByte())));
            }

            attributes.add(new SerializedAttribute(attribute, value, modifiers));
        }
    }

    @Override
    public void copyFrom(WrapperPlayServerEntityAttributes base) {
        entityId = base.entityId;
        attributes = base.attributes;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public List<SerializedAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SerializedAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerEntityAttributes{" +
                "entityId=" + entityId +
                ", attributes=" + attributes +
                '}';
    }

    static class SerializedAttribute {

        private EntityAttribute attribute;
        private double baseValue;
        private List<AttributeModifier> modifiers;

        public SerializedAttribute(EntityAttribute attribute, double baseValue, List<AttributeModifier> modifiers) {
            this.attribute = attribute;
            this.baseValue = baseValue;
            this.modifiers = modifiers;
        }

        public EntityAttribute getAttribute() {
            return attribute;
        }

        public double getBaseValue() {
            return baseValue;
        }

        public List<AttributeModifier> getModifiers() {
            return modifiers;
        }

        public void setAttribute(EntityAttribute attribute) {
            this.attribute = attribute;
        }

        public void setBaseValue(double baseValue) {
            this.baseValue = baseValue;
        }

        public void setModifiers(List<AttributeModifier> modifiers) {
            this.modifiers = modifiers;
        }
    }

}
