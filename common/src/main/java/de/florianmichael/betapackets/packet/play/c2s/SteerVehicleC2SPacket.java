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

package de.florianmichael.betapackets.packet.play.c2s;

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class SteerVehicleC2SPacket extends Packet {

    public float strafeSpeed;
    public float forwardSpeed;
    public byte flags;

    public SteerVehicleC2SPacket(final FunctionalByteBuf buf) {
        this(
                buf.readFloat(),
                buf.readFloat(),

                buf.readByte()
        );
    }

    public SteerVehicleC2SPacket(float strafeSpeed, float forwardSpeed, byte flags) {
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.flags = flags;
    }

    public boolean isJumping() {
        return (this.flags & 1) > 0;
    }

    public void setJumping() {
        this.flags = (byte) (this.flags | 1);
    }

    public boolean isSneaking() {
        return (this.flags & 2) > 0;
    }

    public void setSneaking() {
        this.flags = (byte) (this.flags | 2);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeFloat(this.strafeSpeed);
        buf.writeFloat(this.forwardSpeed);

        buf.writeByte(this.flags);
    }

    @Override
    public String toString() {
        return "SteerVehicleC2SPacket{" +
                "strafeSpeed=" + strafeSpeed +
                ", forwardSpeed=" + forwardSpeed +
                ", flags=" + flags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SteerVehicleC2SPacket that = (SteerVehicleC2SPacket) o;

        if (Float.compare(that.strafeSpeed, strafeSpeed) != 0) return false;
        if (Float.compare(that.forwardSpeed, forwardSpeed) != 0) return false;
        return flags == that.flags;
    }

    @Override
    public int hashCode() {
        int result = (strafeSpeed != +0.0f ? Float.floatToIntBits(strafeSpeed) : 0);
        result = 31 * result + (forwardSpeed != +0.0f ? Float.floatToIntBits(forwardSpeed) : 0);
        result = 31 * result + (int) flags;
        return result;
    }
}
