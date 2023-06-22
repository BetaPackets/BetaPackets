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
import de.florianmichael.betapackets.model.position.BlockPos;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Arrays;
import java.util.Objects;

public class UpdateSignC2SPacket extends Packet {

    public BlockPos blockPos;
    public ATextComponent[] lines = new ATextComponent[4];

    public UpdateSignC2SPacket(final FunctionalByteBuf buf) {
        this.blockPos = BlockPos.fromLong(buf.readLong());
        for (int i = 0; i < 4; i++) {
            lines[i] = buf.readComponent();
        }
    }

    public UpdateSignC2SPacket(BlockPos blockPos, ATextComponent[] lines) {
        this.blockPos = blockPos;
        this.lines = lines;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(this.blockPos.toLong());
        for (ATextComponent line : lines) {
            buf.writeComponent(line);
        }
    }

    @Override
    public String toString() {
        return "UpdateSignC2SPacket{" +
                "blockPos=" + blockPos +
                ", lines=" + Arrays.toString(lines) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateSignC2SPacket that = (UpdateSignC2SPacket) o;

        if (!Objects.equals(blockPos, that.blockPos)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(lines, that.lines);
    }

    @Override
    public int hashCode() {
        int result = blockPos != null ? blockPos.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(lines);
        return result;
    }
}
