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

package de.florianmichael.betapackets.model.base.ping;

import java.util.Arrays;

public class Players {

    public int max;
    public int online;
    public Player[] sample;

    public Players(int max, int online, Player[] sample) {
        this.max = max;
        this.online = online;
        this.sample = sample;
    }

    @Override
    public String toString() {
        return "Players{" +
                "max=" + max +
                ", online=" + online +
                ", sample=" + Arrays.toString(sample) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Players players = (Players) o;

        if (max != players.max) return false;
        if (online != players.online) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(sample, players.sample);
    }

    @Override
    public int hashCode() {
        int result = max;
        result = 31 * result + online;
        result = 31 * result + Arrays.hashCode(sample);
        return result;
    }
}
