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
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.v1_9.Hand1_9;
import de.florianmichael.betapackets.model.hud.chat.ChatVisibility;

import java.util.Objects;

public class ClientSettingsC2SPacket extends Packet {

    public String lang;
    public int view;
    public ModelMapper<Byte, ChatVisibility> chatVisibility = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ChatVisibility::getById);
    public boolean enableColors;

    /**
     * You can use the SkinPartsFlagsConverter class to get these fields
     */
    public int modelPartFlags;
    public ModelMapper<Integer, Hand1_9> hand1_9 = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, Hand1_9::getById);

    public ClientSettingsC2SPacket(final FunctionalByteBuf buf) {
        this.lang = buf.readString(7);
        this.view = buf.readByte();
        this.chatVisibility.read(buf);
        this.enableColors = buf.readBoolean();
        this.modelPartFlags = buf.readUnsignedByte();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand1_9.read(buf);
        }
    }

    public ClientSettingsC2SPacket(String lang, int view, ChatVisibility chatVisibility, boolean enableColors, int modelPartFlags) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = new ModelMapper<>(FunctionalByteBuf::writeByte, chatVisibility);
        this.enableColors = enableColors;
        this.modelPartFlags = modelPartFlags;
    }

    public ClientSettingsC2SPacket(String lang, int view, ChatVisibility chatVisibility, boolean enableColors, int modelPartFlags, Hand1_9 hand1_9) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = new ModelMapper<>(FunctionalByteBuf::writeByte, chatVisibility);
        this.enableColors = enableColors;
        this.modelPartFlags = modelPartFlags;
        this.hand1_9 = new ModelMapper<>(FunctionalByteBuf::writeVarInt, hand1_9);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.lang);
        buf.writeByte(this.view);
        this.chatVisibility.write(buf);
        buf.writeBoolean(this.enableColors);
        buf.writeByte(this.modelPartFlags);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand1_9.write(buf);
        }
    }

    @Override
    public String toString() {
        return "ClientSettingsC2SPacket{" +
                "lang='" + lang + '\'' +
                ", view=" + view +
                ", chatVisibility=" + chatVisibility +
                ", enableColors=" + enableColors +
                ", modelPartFlags=" + modelPartFlags +
                ", hand1_9=" + hand1_9 +
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
        if (!Objects.equals(chatVisibility, that.chatVisibility))
            return false;
        return Objects.equals(hand1_9, that.hand1_9);
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + view;
        result = 31 * result + (chatVisibility != null ? chatVisibility.hashCode() : 0);
        result = 31 * result + (enableColors ? 1 : 0);
        result = 31 * result + modelPartFlags;
        result = 31 * result + (hand1_9 != null ? hand1_9.hashCode() : 0);
        return result;
    }
}
