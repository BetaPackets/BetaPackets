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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.model.block.Block;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import de.florianmichael.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayServerBlockAction extends PacketWrapper<WrapperPlayServerBlockAction> {

    private BlockPos pos;
    private int action;
    private int data;
    private Block block;

    public WrapperPlayServerBlockAction(BlockPos pos, int action, int data, Block block) {
        super(PacketType.Play.Server.BLOCK_ACTION);
        this.pos = pos;
        this.action = action;
        this.data = data;
        this.block = block;
    }

    public WrapperPlayServerBlockAction(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeBlockPos(pos);
        buf.writeByte(action);
        buf.writeByte(data);
        buf.writeVarInt(block.getId());
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        pos = buf.readBlockPos();
        action = buf.readUnsignedByte();
        data = buf.readUnsignedByte();
        block = buf.getProtocolVersion().getBlockMapping().getById(buf.readVarInt());
    }

    @Override
    public void copyFrom(WrapperPlayServerBlockAction base) {
        pos = base.pos;
        action = base.action;
        data = base.data;
        block = base.block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerBlockAction{" +
                "pos=" + pos +
                ", action=" + action +
                ", data=" + data +
                ", block=" + block +
                '}';
    }
}
