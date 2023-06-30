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
import de.florianmichael.betapackets.model.game.ClientStatus;

import java.util.Objects;

public class ClientStatusC2SPacket extends Packet {

    public ModelMapper<Integer, ClientStatus> status = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, ClientStatus::getById);

    public ClientStatusC2SPacket(final FunctionalByteBuf buf) {
        this.status.read(buf);
    }

    public ClientStatusC2SPacket(ClientStatus status) {
        this.status = new ModelMapper<>(FunctionalByteBuf::writeVarInt, status);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.status.write(buf);
    }

    @Override
    public String toString() {
        return "ClientStatusC2SPacket{" +
                "status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientStatusC2SPacket that = (ClientStatusC2SPacket) o;

        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }
}
