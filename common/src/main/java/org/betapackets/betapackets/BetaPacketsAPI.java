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

package org.betapackets.betapackets;

import org.betapackets.betapackets.connection.ConnectionList;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.event.PacketListener;
import org.betapackets.betapackets.event.PacketSendEvent;
import org.betapackets.betapackets.packet.type.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetaPacketsAPI {

    private final Map<Packet, List<PacketListener>> listenerByType = new HashMap<>();
    private final Map<Object, List<PacketListener>> listenerByPlugin = new HashMap<>();

    private final ConnectionList connections = new ConnectionList();

    public void registerListener(PacketListener listener) {
        for (Packet packet : listener.getWhitelist()) {
            listenerByType.computeIfAbsent(packet, k -> new ArrayList<>()).add(listener);
        }
        listenerByPlugin.computeIfAbsent(listener.getPlugin(), k -> new ArrayList<>()).add(listener);
    }

    public PacketEvent fireReadEvent(PacketEvent event) throws IOException {
        final List<PacketListener> packetListeners = listenerByType.get(event.getType());
        if (packetListeners == null) return event;

        for (PacketListener listener : packetListeners) {
            listener.onRead(event);
            if (event.isAbort()) return event;
        }

        return event;
    }

    public PacketSendEvent fireWriteEvent(PacketSendEvent event) throws IOException {
        final List<PacketListener> packetListeners = listenerByType.get(event.getType());
        if (packetListeners == null) return event;

        for (PacketListener listener : packetListeners) {
            listener.onWrite(event);
            if (event.isAbort()) return event;
        }

        return event;
    }

    public void unregisterListener(PacketListener listener) {
        for (Packet packet : listener.getWhitelist()) {
            listenerByType.get(packet).remove(listener);
        }
        listenerByPlugin.get(listener.getPlugin()).remove(listener);
    }

    public void unloadPlugin(Object plugin) {
        final List<PacketListener> packetListeners = listenerByPlugin.get(plugin);
        if (packetListeners == null) return;

        packetListeners.forEach(this::unregisterListener);
        listenerByPlugin.remove(plugin);
    }

    public ConnectionList getConnections() {
        return connections;
    }
}
