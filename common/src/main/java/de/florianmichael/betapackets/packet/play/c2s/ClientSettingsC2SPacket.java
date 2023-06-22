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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.hud.chat.ChatVisibility;

import java.util.Objects;

public class ClientSettingsC2SPacket extends Packet {

    public String lang;
    public int view;
    public ModelMapper<Byte, ChatVisibility> chatVisibility = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ChatVisibility::getById);
    public boolean enableColors;

    /**
     * You can use the SkinPartsFlagsConverter class to get these fields
     * @see de.florianmichael.betapackets.converter.SkinPartsFlagsConverter
     */
    public int modelPartFlags;

    public ClientSettingsC2SPacket(final FunctionalByteBuf buf) {
        this.lang = buf.readString(7);
        this.view = buf.readByte();
        this.chatVisibility.read(buf);
        this.enableColors = buf.readBoolean();
        this.modelPartFlags = buf.readUnsignedByte();
    }

    public ClientSettingsC2SPacket(String lang, int view, ChatVisibility chatVisibility, boolean enableColors, int modelPartFlags) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = new ModelMapper<>(FunctionalByteBuf::writeByte, chatVisibility);
        this.enableColors = enableColors;
        this.modelPartFlags = modelPartFlags;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.lang);
        buf.writeByte(this.view);
        this.chatVisibility.write(buf);
        buf.writeBoolean(this.enableColors);
        buf.writeByte(this.modelPartFlags);
    }

    @Override
    public String toString() {
        return "ClientSettingsC2SPacket{" +
                "lang='" + lang + '\'' +
                ", view=" + view +
                ", chatVisibility=" + chatVisibility +
                ", enableColors=" + enableColors +
                ", modelPartFlags=" + modelPartFlags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientSettingsC2SPacket that = (ClientSettingsC2SPacket) o;

        if (view != that.view) return false;
        if (enableColors != that.enableColors) return false;
        if (modelPartFlags != that.modelPartFlags) return false;
        if (!Objects.equals(lang, that.lang)) return false;
        return Objects.equals(chatVisibility, that.chatVisibility);
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + view;
        result = 31 * result + (chatVisibility != null ? chatVisibility.hashCode() : 0);
        result = 31 * result + (enableColors ? 1 : 0);
        result = 31 * result + modelPartFlags;
        return result;
    }
}
