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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import de.florianmichael.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayServerBundleDelimiter extends PacketWrapper<WrapperPlayServerBundleDelimiter> {

    public static WrapperPlayServerBundleDelimiter INSTANCE = new WrapperPlayServerBundleDelimiter();

    public WrapperPlayServerBundleDelimiter() {
        super(PacketType.Play.Server.BUNDLE_DELIMITER);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
    }

    @Override
    public void copyFrom(WrapperPlayServerBundleDelimiter base) {
    }
}
