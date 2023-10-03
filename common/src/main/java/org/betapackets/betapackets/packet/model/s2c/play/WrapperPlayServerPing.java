/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayServerPing extends PacketWrapper<WrapperPlayServerPing> {

    private int id;

    public WrapperPlayServerPing(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerPing(int id) {
        super(PacketType.Play.Server.PING);
        this.id = id;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeInt(id);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        id = buf.readInt();
    }

    @Override
    public void copyFrom(WrapperPlayServerPing base) {
        id = base.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerPing{" +
                "id=" + id +
                '}';
    }
}
