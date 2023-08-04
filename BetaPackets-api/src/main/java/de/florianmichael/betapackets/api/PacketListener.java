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

package de.florianmichael.betapackets.api;

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.packet.type.Packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PacketListener {

    private final Object plugin;
    private final List<Packet> whitelist;

    public PacketListener(Object plugin, Packet packet, Packet... packets) {
        this.plugin = plugin;

        whitelist = new ArrayList<>();
        whitelist.add(packet);
        whitelist.addAll(Arrays.asList(packets));
    }

    public void onRead(PacketEvent event) {
        throw new UnsupportedOperationException("onRead(event: PacketEvent) has to be implemented without super-call");
    }

    public void onWrite(PacketEvent event) {
        throw new UnsupportedOperationException("onWrite(event: PacketEvent) has to be implemented without super-call");
    }

    public Object getPlugin() {
        return plugin;
    }

    public List<Packet> getWhitelist() {
        return Collections.unmodifiableList(whitelist);
    }
}
