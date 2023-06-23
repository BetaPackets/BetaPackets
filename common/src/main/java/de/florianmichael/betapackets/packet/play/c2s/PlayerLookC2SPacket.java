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

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class PlayerLookC2SPacket extends PlayerC2SPacket {

    public float yaw;
    public float pitch;

    public PlayerLookC2SPacket(final FunctionalByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public PlayerLookC2SPacket(float yaw, float pitch, boolean onGround) {
        super(onGround);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.pitch);

        super.write(buf);
    }

    @Override
    public String toString() {
        return "PlayerLookC2SPacket{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PlayerLookC2SPacket that = (PlayerLookC2SPacket) o;

        if (Float.compare(that.yaw, yaw) != 0) return false;
        return Float.compare(that.pitch, pitch) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (yaw != +0.0f ? Float.floatToIntBits(yaw) : 0);
        result = 31 * result + (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
        return result;
    }
}
