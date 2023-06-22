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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class PlayerListHeaderFooterS2CPacket extends Packet {

    public ATextComponent header;
    public ATextComponent footer;

    public PlayerListHeaderFooterS2CPacket(final FunctionalByteBuf buf) {
        this(buf.readComponent(), buf.readComponent());
    }

    public PlayerListHeaderFooterS2CPacket(ATextComponent header, ATextComponent footer) {
        this.header = header;
        this.footer = footer;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeComponent(header);
        buf.writeComponent(footer);
    }

    @Override
    public String toString() {
        return "PlayerListHeaderFooterS2CPacket{" +
                "header=" + header +
                ", footer=" + footer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerListHeaderFooterS2CPacket that = (PlayerListHeaderFooterS2CPacket) o;

        if (!Objects.equals(header, that.header)) return false;
        return Objects.equals(footer, that.footer);
    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + (footer != null ? footer.hashCode() : 0);
        return result;
    }
}
