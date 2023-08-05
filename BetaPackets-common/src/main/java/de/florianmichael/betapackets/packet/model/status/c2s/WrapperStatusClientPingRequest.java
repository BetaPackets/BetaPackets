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

package de.florianmichael.betapackets.packet.model.status.c2s;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.model.PacketWrapper;

import java.io.IOException;

public class WrapperStatusClientPingRequest extends PacketWrapper<WrapperStatusClientPingRequest> {

    private long time;

    public WrapperStatusClientPingRequest(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperStatusClientPingRequest(long time) {
        this.time = time;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeLong(time);
    }

    @Override
    public void read(FunctionalByteBuf buf) {
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
