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

package de.florianmichael.betapackets.event;

import de.florianmichael.betapackets.connection.UserConnection;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PacketSendEvent extends PacketEvent {

    private final List<PacketEvent> bundle = new LinkedList<>();

    public PacketSendEvent(Packet type, FunctionalByteBuf byteBuf, UserConnection connection) {
        super(type, byteBuf, connection);
        bundle.add(this);
    }

    public PacketEvent getWrapperAsEvent(PacketWrapper<?> wrapper) {
        try {
            FunctionalByteBuf byteBuf = new FunctionalByteBuf(getConnection().getChannel().alloc().buffer(), getConnection());
            wrapper.write(wrapper.getType(), byteBuf);
            PacketEvent event = new PacketEvent(wrapper.getType(), byteBuf, getConnection());
            event.setLastPacketWrapper(wrapper);
            return event;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPacketToBundle(int index, PacketWrapper<?> wrapper) {
        bundle.add(index, getWrapperAsEvent(wrapper));
    }

    public void addPacketToBundle(PacketWrapper<?> wrapper) {
        bundle.add(getWrapperAsEvent(wrapper));
    }

    public List<PacketEvent> getBundle() {
        return Collections.unmodifiableList(bundle);
    }
}
