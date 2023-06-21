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
import de.florianmichael.betapackets.model.position.BlockPos;
import net.lenni0451.mcstructs.text.ATextComponent;

public class UpdateSignS2CPacket extends Packet {

    public BlockPos blockPos;
    public ATextComponent line1;
    public ATextComponent line2;
    public ATextComponent line3;
    public ATextComponent line4;

    public UpdateSignS2CPacket(final FunctionalByteBuf buf) {
        this(
                BlockPos.fromLong(buf.readLong()),
                buf.readComponent(),
                buf.readComponent(),
                buf.readComponent(),
                buf.readComponent()
        );
    }

    public UpdateSignS2CPacket(BlockPos blockPos, ATextComponent line1, ATextComponent line2, ATextComponent line3, ATextComponent line4) {
        this.blockPos = blockPos;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(blockPos.toLong());

        buf.writeComponent(line1);
        buf.writeComponent(line2);
        buf.writeComponent(line3);
        buf.writeComponent(line4);
    }

    public ATextComponent[] getLines() {
        return new ATextComponent[] { line1, line2, line3, line4 };
    }

    @Override
    public String toString() {
        return "UpdateSignS2CPacket{" +
                "blockPos=" + blockPos +
                ", line1=" + line1 +
                ", line2=" + line2 +
                ", line3=" + line3 +
                ", line4=" + line4 +
                '}';
    }
}
