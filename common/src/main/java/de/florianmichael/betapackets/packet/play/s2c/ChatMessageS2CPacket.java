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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.chat.ChatPosition;

public class ChatMessageS2CPacket extends Packet {

    public String jsonData;
    public ChatPosition position;

    public ChatMessageS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readString(32767),
                ChatPosition.byId(transformer.readByte())
        );
    }

    public ChatMessageS2CPacket(final String jsonData, final ChatPosition position) {
        this.jsonData = jsonData;
        this.position = position;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeString(this.jsonData);
        buf.writeByte(this.position.ordinal());
    }
}
