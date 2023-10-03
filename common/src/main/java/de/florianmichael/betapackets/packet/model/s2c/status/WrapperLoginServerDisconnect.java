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

package de.florianmichael.betapackets.packet.model.s2c.status;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import de.florianmichael.betapackets.packet.type.PacketType;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.io.IOException;

public class WrapperLoginServerDisconnect extends PacketWrapper<WrapperLoginServerDisconnect> {

    private ATextComponent reason;

    public WrapperLoginServerDisconnect(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) {
        buf.writeComponent(reason);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) {
        reason = buf.readComponent();
    }

    @Override
    public void copyFrom(WrapperLoginServerDisconnect base) {
        reason = base.reason;
    }

    public WrapperLoginServerDisconnect(ATextComponent reason) {
        super(PacketType.Play.Server.DISCONNECT);
        this.reason = reason;
    }

    public ATextComponent getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "WrapperLoginServerDisconnect{" +
                "reason=" + reason +
                '}';
    }
}