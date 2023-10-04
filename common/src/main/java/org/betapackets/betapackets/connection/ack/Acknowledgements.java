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

package org.betapackets.betapackets.connection.ack;

import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.model.c2s.play.WrapperPlayClientKeepAlive;
import org.betapackets.betapackets.packet.model.c2s.play.WrapperPlayClientPong;
import org.betapackets.betapackets.packet.model.s2c.play.WrapperPlayServerKeepAlive;
import org.betapackets.betapackets.packet.model.s2c.play.WrapperPlayServerPing;
import gnu.trove.list.TIntList;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Acknowledgements {

    private final TIntList reservedTransactionIds = new TIntArrayList();
    private final TIntObjectMap<AckWithID> transactionById = new TIntObjectHashMap<>();

    private final TLongList reservedKeepAliveIds = new TLongArrayList();
    private final TLongObjectMap<AckWithID> keepAliveById = new TLongObjectHashMap<>();

    public void onPingSent(WrapperPlayServerPing ping) {
        AckWithID ack = transactionById.get(ping.getId());
        if (!reservedTransactionIds.contains(ping.getId()))
            reservedTransactionIds.add(ping.getId());

        if (ack != null)
            ack.onSend();
    }

    public void onKeepAliveSent(WrapperPlayServerKeepAlive keepAlive) {
        AckWithID ack = keepAliveById.get(keepAlive.getId());
        if (!reservedKeepAliveIds.contains(keepAlive.getId()))
            reservedKeepAliveIds.remove(keepAlive.getId());

        if (ack != null)
            ack.onSend();
    }

    public boolean onPongReceive(WrapperPlayClientPong pong) {
        reservedTransactionIds.remove(pong.getId());
        AckWithID ack = transactionById.remove(pong.getId());
        if (ack != null) {
            ack.onReceive();
            return true;
        }
        return false;
    }

    public boolean onKeepAliveReceive(WrapperPlayClientKeepAlive keepAlive) {
        reservedKeepAliveIds.remove(keepAlive.getId());
        AckWithID ack = keepAliveById.remove(keepAlive.getId());
        if (ack != null) {
            ack.onReceive();
            return true;
        }
        return false;
    }

    public PacketWrapper<?> createKeepAlive(Consumer<Ack> callback) {
        long minId = Long.MIN_VALUE;
        long maxId = Integer.MIN_VALUE;
        long id;
        do {
            id = ThreadLocalRandom.current().nextLong(minId, maxId);
        } while (reservedKeepAliveIds.contains(id)); // i'm too scared of duplicates

        AckWithID ack = new AckWithID(callback, id);
        reservedKeepAliveIds.add(id);
        keepAliveById.put(id, ack);

        return new WrapperPlayServerKeepAlive(id);
    }

    public PacketWrapper<?> createTransaction(Consumer<Ack> callback) {
        int minId = Integer.MIN_VALUE;
        int maxId = 0;
        int id;
        do {
            id = ThreadLocalRandom.current().nextInt(minId, maxId);
        } while (reservedTransactionIds.contains(id)); // i'm too scared of duplicates

        AckWithID ack = new AckWithID(callback, id);
        reservedTransactionIds.add(id);
        transactionById.put(id, ack);

        return new WrapperPlayServerPing(id);
    }
}
