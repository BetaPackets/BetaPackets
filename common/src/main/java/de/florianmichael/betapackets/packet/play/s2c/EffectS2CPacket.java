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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.position.BlockPos;

public class EffectS2CPacket extends Packet {

    public int effectId;
    public BlockPos blockPos;
    public int data;
    public boolean disableRelativeVolume;

    public EffectS2CPacket(final FunctionalByteBuf transformer) {
        this(
                transformer.readInt(),
                BlockPos.fromLong(transformer.readLong()),
                transformer.readInt(),
                transformer.readBoolean()
        );
    }

    public EffectS2CPacket(int effectId, BlockPos blockPos, int data, boolean disableRelativeVolume) {
        this.effectId = effectId;
        this.blockPos = blockPos;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeInt(effectId);
        buf.writeLong(blockPos.toLong());
        buf.writeInt(data);
        buf.writeBoolean(disableRelativeVolume);
    }

    @Override
    public String toString() {
        return "EffectS2CPacket{" +
                "effectId=" + effectId +
                ", blockPos=" + blockPos +
                ", data=" + data +
                ", disableRelativeVolume=" + disableRelativeVolume +
                '}';
    }
}
