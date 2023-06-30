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

package de.florianmichael.betapackets.packet.play.c2s.v1_9;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;

public class SteerBoatC2SPacket extends Packet {

    public boolean leftPaddleTurning;
    public boolean rightPaddleTurning;

    public SteerBoatC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readBoolean(), buf.readBoolean());
    }

    public SteerBoatC2SPacket(boolean leftPaddleTurning, boolean rightPaddleTurning) {
        this.leftPaddleTurning = leftPaddleTurning;
        this.rightPaddleTurning = rightPaddleTurning;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeBoolean(leftPaddleTurning);
        buf.writeBoolean(rightPaddleTurning);
    }

    @Override
    public String toString() {
        return "SteerBoatC2SPacket{" +
                "leftPaddleTurning=" + leftPaddleTurning +
                ", rightPaddleTurning=" + rightPaddleTurning +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SteerBoatC2SPacket that = (SteerBoatC2SPacket) o;

        if (leftPaddleTurning != that.leftPaddleTurning) return false;
        return rightPaddleTurning == that.rightPaddleTurning;
    }

    @Override
    public int hashCode() {
        int result = (leftPaddleTurning ? 1 : 0);
        result = 31 * result + (rightPaddleTurning ? 1 : 0);
        return result;
    }
}
