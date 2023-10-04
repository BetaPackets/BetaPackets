/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.block.BlockState;
import org.betapackets.betapackets.model.position.BlockPos;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;

import java.io.IOException;

public class WrapperPlayServerBlockUpdate extends PacketWrapper<WrapperPlayServerBlockUpdate> {

    private BlockPos pos;
    private BlockState state;

    public WrapperPlayServerBlockUpdate(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeBlockPos(pos);
        buf.writeVarInt(state.getId());
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        pos = buf.readBlockPos();
        state = buf.getProtocolVersion().getBlockStateMapping().getById(buf.readVarInt());
    }

    @Override
    public void copyFrom(WrapperPlayServerBlockUpdate base) {
        pos = base.pos;
        state = base.state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public BlockState getState() {
        return state;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerBlockUpdate{" +
                "pos=" + pos +
                ", state=" + state +
                '}';
    }
}
