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

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.model.base.Writer;
import de.florianmichael.betapackets.model.entity.metadata.MetadataCodecType;
import de.florianmichael.betapackets.model.entity.metadata.SerializedMetadata;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WrapperPlayServerEntityMetadata extends PacketWrapper<WrapperPlayServerEntityMetadata> {

    private int entityId;
    private List<SerializedMetadata> serializedMetadata;

    public WrapperPlayServerEntityMetadata(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    @SuppressWarnings("all")
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);

        for (SerializedMetadata metadata : serializedMetadata) {
            buf.writeByte(metadata.getIndex());
            buf.writeVarInt(buf.getProtocolVersion().getMetadataCodecMapping().getId(metadata.getType()));
            ((Writer) metadata.getType().getWriter()).write(buf, metadata.getValue());
        }
        buf.writeByte(0xFF);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        serializedMetadata = new ArrayList<>();

        int index;
        while ((index = buf.readUnsignedByte()) != 0xFF) {
            MetadataCodecType codecType = buf.getProtocolVersion().getMetadataCodecMapping().getCodecType(buf.readVarInt());
            serializedMetadata.add(new SerializedMetadata(index, codecType, codecType.getReader().read(buf)));
        }
    }

    @Override
    public void copyFrom(WrapperPlayServerEntityMetadata base) {
        entityId = base.entityId;
        serializedMetadata = base.serializedMetadata;
    }

    public int getEntityId() {
        return entityId;
    }

    public List<SerializedMetadata> getSerializedMetadata() {
        return serializedMetadata;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setSerializedMetadata(List<SerializedMetadata> serializedMetadata) {
        this.serializedMetadata = serializedMetadata;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerEntityMetadata{" +
                "entityId=" + entityId +
                ", serializedMetadata=" + serializedMetadata +
                '}';
    }
}
