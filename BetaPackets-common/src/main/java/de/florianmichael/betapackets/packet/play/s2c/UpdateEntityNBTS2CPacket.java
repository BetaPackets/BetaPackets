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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.io.IOException;
import java.util.Objects;

public class UpdateEntityNBTS2CPacket extends Packet {

    public int entityId;
    public CompoundTag tag;

    public UpdateEntityNBTS2CPacket(final FunctionalByteBuf buf) throws IOException {
        this(buf.readVarInt(), buf.readCompoundTag());
    }

    public UpdateEntityNBTS2CPacket(int entityId, CompoundTag tag) {
        this.entityId = entityId;
        this.tag = tag;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);
        buf.writeCompoundTag(this.tag);
    }

    @Override
    public String toString() {
        return "UpdateEntityNBTS2CPacket{" +
                "entityId=" + entityId +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateEntityNBTS2CPacket that = (UpdateEntityNBTS2CPacket) o;

        if (entityId != that.entityId) return false;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
