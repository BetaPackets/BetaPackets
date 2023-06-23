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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class PlayerAbilitiesS2CPacket extends Packet {
    public byte flags;
    public float flyingSpeed;
    public float walkingSpeed;

    public PlayerAbilitiesS2CPacket(final FunctionalByteBuf buf) {
        this(
                buf.readByte(),

                buf.readFloat(),
                buf.readFloat()
        );
    }

    public PlayerAbilitiesS2CPacket(byte flags, float flyingSpeed, float walkingSpeed) {
        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    public boolean isInvulnerable() {
        return (flags & 1) > 0;
    }

    public void setInvulnerable() {
        flags = (byte) (flags | 1);
    }

    public boolean isFlying() {
        return (flags & 2) > 0;
    }

    public void setFlying() {
        flags = (byte) (flags | 2);
    }

    public boolean isAllowFlying() {
        return (flags & 4) > 0;
    }

    public void setAllowFlying() {
        flags = (byte) (flags | 4);
    }

    public boolean isCreativeMode() {
        return (flags & 8) > 0;
    }

    public void setCreativeMode() {
        flags = (byte) (flags | 8);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(flags);

        buf.writeFloat(flyingSpeed);
        buf.writeFloat(walkingSpeed);
    }

    @Override
    public String toString() {
        return "PlayerAbilitiesS2CPacket{" +
                "flags=" + flags +

                "isInvulnerable()=" + flags +
                "isFlying()=" + flags +
                "isAllowFlying()=" + flags +
                ", isCreativeMode()=" + flyingSpeed +

                ", flyingSpeed=" + flyingSpeed +
                ", walkingSpeed=" + walkingSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerAbilitiesS2CPacket that = (PlayerAbilitiesS2CPacket) o;

        if (flags != that.flags) return false;
        if (Float.compare(that.flyingSpeed, flyingSpeed) != 0) return false;
        return Float.compare(that.walkingSpeed, walkingSpeed) == 0;
    }

    @Override
    public int hashCode() {
        int result = flags;
        result = 31 * result + (flyingSpeed != +0.0f ? Float.floatToIntBits(flyingSpeed) : 0);
        result = 31 * result + (walkingSpeed != +0.0f ? Float.floatToIntBits(walkingSpeed) : 0);
        return result;
    }
}
