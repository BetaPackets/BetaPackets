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

package de.florianmichael.betapackets.packet.play.s2c;

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.Statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatisticsS2CPacket extends Packet {

    public List<Statistic> statistics;

    public StatisticsS2CPacket(final FunctionalByteBuf buf) {
        statistics = new ArrayList<>();
        final int count = buf.readVarInt();
        for (int i = 0; i < count; i++) {
            statistics.add(new Statistic(buf.readString(32767), buf.readVarInt()));
        }
    }

    public StatisticsS2CPacket(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(statistics.size());

        for (Statistic statistic : statistics) {
            buf.writeString(statistic.name);
            buf.writeVarInt(statistic.value);
        }
    }

    @Override
    public String toString() {
        return "StatisticsS2CPacket{" +
                "statistics=" + statistics +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticsS2CPacket that = (StatisticsS2CPacket) o;

        return Objects.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return statistics != null ? statistics.hashCode() : 0;
    }
}
