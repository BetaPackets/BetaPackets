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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

import java.util.Objects;
import java.util.UUID;

public class SpectateC2SPacket extends Packet {

    public UUID uuid;

    public SpectateC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readUUID());
    }

    public SpectateC2SPacket(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeUUID(this.uuid);
    }

    @Override
    public String toString() {
        return "SpectateC2SPacket{" +
                "uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpectateC2SPacket that = (SpectateC2SPacket) o;

        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
