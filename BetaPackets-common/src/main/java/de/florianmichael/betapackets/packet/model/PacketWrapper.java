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

package de.florianmichael.betapackets.packet.model;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import io.netty.handler.codec.CodecException;

public abstract class PacketWrapper<T extends PacketWrapper<T>> {

    public PacketWrapper(PacketEvent event) {
        if (event.getLastPacketWrapper() != null) {
            if (!getClass().isInstance(event.getLastPacketWrapper())) {
                throw new CodecException("Duplicate implementation " + event.getLastPacketWrapper().getClass() + " " + getClass());
            }
            copyFrom((T) event.getLastPacketWrapper());
        } else {
            read(event.getByteBuf());
        }
        event.setLastPacketWrapper(this);
    }

    public PacketWrapper() {
    }

    public abstract void write(FunctionalByteBuf buf);

    public abstract void read(FunctionalByteBuf buf);

    public abstract void copyFrom(T base);
}
