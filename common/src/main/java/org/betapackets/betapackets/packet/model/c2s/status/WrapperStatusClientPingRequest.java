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

package org.betapackets.betapackets.packet.model.c2s.status;

import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperStatusClientPingRequest extends PacketWrapper<WrapperStatusClientPingRequest> {

    private long time;

    public WrapperStatusClientPingRequest(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperStatusClientPingRequest(long time) {
        super(PacketType.Status.Client.PING_REQUEST);
        this.time = time;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) {
        buf.writeLong(time);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) {
        time = buf.readLong();
    }

    @Override
    public void copyFrom(WrapperStatusClientPingRequest base) {
        time = base.time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "WrapperStatusClientPingRequest{" +
                "time=" + time +
                '}';
    }
}
