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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.position.BlockPos;

import java.util.Objects;

public class TabCompleteC2SPacket extends Packet {

    public String message;
    public boolean hasTargetBlock1_9;
    public BlockPos targetBlock;

    public TabCompleteC2SPacket(final FunctionalByteBuf buf) {
        this.message = buf.readString(32767);

        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hasTargetBlock1_9 = buf.readBoolean();
        }
        if (buf.readBoolean()) {
            this.targetBlock = BlockPos.fromLong(buf.readLong());
        }
    }

    public TabCompleteC2SPacket(String message, BlockPos targetBlock) {
        this(message, false, targetBlock);
    }

    public TabCompleteC2SPacket(String message, boolean hasTargetBlock, BlockPos targetBlock) {
        this.message = message;
        this.hasTargetBlock1_9 = hasTargetBlock;
        this.targetBlock = targetBlock;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.message);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeBoolean(this.hasTargetBlock1_9);
        }
        if (targetBlock != null) {
            buf.writeBoolean(true);
            buf.writeLong(this.targetBlock.toLong());
        } else {
            buf.writeBoolean(false);
        }
    }

    @Override
    public String toString() {
        return "TabCompleteC2SPacket{" +
                "message='" + message + '\'' +
                ", hasTargetBlock1_9=" + hasTargetBlock1_9 +
                ", targetBlock=" + targetBlock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabCompleteC2SPacket that = (TabCompleteC2SPacket) o;

        if (hasTargetBlock1_9 != that.hasTargetBlock1_9) return false;
        if (!Objects.equals(message, that.message)) return false;
        return Objects.equals(targetBlock, that.targetBlock);
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (hasTargetBlock1_9 ? 1 : 0);
        result = 31 * result + (targetBlock != null ? targetBlock.hashCode() : 0);
        return result;
    }
}
