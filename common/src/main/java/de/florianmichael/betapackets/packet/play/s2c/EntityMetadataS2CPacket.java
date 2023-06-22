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
import de.florianmichael.betapackets.model.entity.metadata.Metadata;

import java.util.List;
import java.util.Objects;

public class EntityMetadataS2CPacket extends EntityS2CPacket {
    public List<Metadata> metadata;

    public EntityMetadataS2CPacket(FunctionalByteBuf buf) {
        super(buf);

        this.metadata = buf.readMetadata();
    }

    public EntityMetadataS2CPacket(int entityId, List<Metadata> metadata) {
        super(entityId);

        this.metadata = metadata;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        super.write(buf);

        buf.writeMetadata(metadata);
    }

    @Override
    public String toString() {
        return "EntityMetadataS2CPacket{" +
                "metadata=" + metadata +
                ", entityId=" + entityId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityMetadataS2CPacket that = (EntityMetadataS2CPacket) o;

        return Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return metadata != null ? metadata.hashCode() : 0;
    }
}
