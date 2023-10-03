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

package org.betapackets.betapackets.packet.model;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.packet.type.Packet;
import io.netty.handler.codec.CodecException;

import java.io.IOException;

public abstract class PacketWrapper<T extends PacketWrapper<T>> {

    private final Packet type;

    public PacketWrapper(PacketEvent event) throws IOException {
        type = event.getType();
        if (event.getLastPacketWrapper() != null) {
            if (!getClass().isInstance(event.getLastPacketWrapper())) {
                throw new CodecException("Duplicate implementation " + event.getLastPacketWrapper().getClass() + " " + getClass());
            }
            copyFrom((T) event.getLastPacketWrapper());
        } else {
            read(event.getType(), event.getByteBuf());
        }
        event.setLastPacketWrapper(this);
    }

    public PacketWrapper(Packet type) {
        this.type = type;
    }

    public abstract void write(Packet type, FunctionalByteBuf buf) throws IOException;
    public abstract void read(Packet type, FunctionalByteBuf buf) throws IOException;

    public abstract void copyFrom(T base);

    public Packet getType() {
        return type;
    }
}
