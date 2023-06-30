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
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.hud.chat.ChatPosition;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class ChatMessageS2CPacket extends Packet {

    public ATextComponent textComponent;
    public ModelMapper<Byte, ChatPosition> position = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ChatPosition::getById);

    public ChatMessageS2CPacket(final FunctionalByteBuf buf) {
        this.textComponent = buf.readComponent();
        this.position.read(buf);
    }

    public ChatMessageS2CPacket(final ATextComponent textComponent, final ChatPosition position) {
        this.textComponent = textComponent;
        this.position = new ModelMapper<>(FunctionalByteBuf::writeByte, position);
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeComponent(this.textComponent);
        this.position.write(buf);
    }

    @Override
    public String toString() {
        return "ChatMessageS2CPacket{" +
                "textComponent=" + textComponent +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessageS2CPacket that = (ChatMessageS2CPacket) o;

        if (!Objects.equals(textComponent, that.textComponent))
            return false;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = textComponent != null ? textComponent.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
