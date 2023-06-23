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

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class TimeUpdateS2CPacket extends Packet {

    public long worldAge;
    public long timeOfDay;

    public TimeUpdateS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readLong(),
                transformer.readLong()
        );
    }

    public TimeUpdateS2CPacket(long worldAge, long timeOfDay) {
        this.worldAge = worldAge;
        this.timeOfDay = timeOfDay;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeLong(this.worldAge);
        buf.writeLong(this.timeOfDay);
    }

    @Override
    public String toString() {
        return "TimeUpdateS2CPacket{" +
                "worldAge=" + worldAge +
                ", timeOfDay=" + timeOfDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeUpdateS2CPacket that = (TimeUpdateS2CPacket) o;

        if (worldAge != that.worldAge) return false;
        return timeOfDay == that.timeOfDay;
    }

    @Override
    public int hashCode() {
        int result = (int) (worldAge ^ (worldAge >>> 32));
        result = 31 * result + (int) (timeOfDay ^ (timeOfDay >>> 32));
        return result;
    }
}
