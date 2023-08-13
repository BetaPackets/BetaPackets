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

package de.florianmichael.betapackets.connection.ack;

import java.util.function.Consumer;

public class Ack {

    private long sent;
    private long received;
    private long delay;
    private final Consumer<Ack> callback;

    public Ack(Consumer<Ack> callback) {
        this.callback = callback;
    }

    public void onSend() {
        sent = System.currentTimeMillis();
    }

    public void onReceive() {
        received = System.currentTimeMillis();
        delay = received - sent;
        callback.accept(this);
    }

    public long getSent() {
        return sent;
    }

    public long getReceived() {
        return received;
    }

    public long getDelay() {
        return delay;
    }
}
