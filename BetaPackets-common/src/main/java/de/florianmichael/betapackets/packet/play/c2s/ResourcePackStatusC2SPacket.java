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
import de.florianmichael.betapackets.model.game.ResourcePackAction;

import java.util.Objects;

public class ResourcePackStatusC2SPacket extends Packet {

    public String hash;
    public ModelMapper<Integer, ResourcePackAction> status = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, ResourcePackAction::getById);

    public ResourcePackStatusC2SPacket(final FunctionalByteBuf buf) {
        this.hash = buf.readString(40);
        this.status.read(buf);
    }

    public ResourcePackStatusC2SPacket(String hash, ResourcePackAction status) {
        this.hash = hash;
        this.status = new ModelMapper<>(FunctionalByteBuf::writeVarInt, status);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.hash);
        this.status.write(buf);
    }

    @Override
    public String toString() {
        return "ResourcePackStatusC2SPacket{" +
                "hash='" + hash + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcePackStatusC2SPacket that = (ResourcePackStatusC2SPacket) o;

        if (!Objects.equals(hash, that.hash)) return false;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = hash != null ? hash.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
