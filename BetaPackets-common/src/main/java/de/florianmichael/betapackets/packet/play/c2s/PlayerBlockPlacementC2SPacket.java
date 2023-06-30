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

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.v1_9.Hand1_9;
import de.florianmichael.betapackets.model.world.item.ItemStackV1_3;
import de.florianmichael.betapackets.model.position.BlockPos;
import de.florianmichael.betapackets.model.position.Facing;

import java.io.IOException;
import java.util.Objects;

public class PlayerBlockPlacementC2SPacket extends Packet {

    public BlockPos blockPos;
    public ModelMapper<Byte, Facing> facing = new ModelMapper<>(buf -> (byte) buf.readUnsignedByte(), FunctionalByteBuf::writeByte, Facing::getById);
    public ModelMapper<Integer, Hand1_9> hand_1_9 = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, Hand1_9::getById);
    public ItemStackV1_3 itemStack_1_8;
    public float facingX;
    public float facingY;
    public float facingZ;

    public PlayerBlockPlacementC2SPacket(final FunctionalByteBuf buf) throws IOException {
        this.blockPos = BlockPos.fromLong(buf.readLong());
        this.facing.read(buf);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand_1_9.read(buf);
        }
        if (buf.getProtocolVersion().isOlderThanOrEqualTo(ProtocolCollection.R1_8)) {
            this.itemStack_1_8 = buf.readItemStack();
        }
        this.facingX = (float) buf.readUnsignedByte() / 16.0F;
        this.facingY = (float) buf.readUnsignedByte() / 16.0F;
        this.facingZ = (float) buf.readUnsignedByte() / 16.0F;
    }

    public PlayerBlockPlacementC2SPacket(BlockPos blockPos, Facing facing, ItemStackV1_3 itemStack, float facingX, float facingY, float facingZ) {
        this.blockPos = blockPos;
        this.facing = new ModelMapper<>(FunctionalByteBuf::writeByte, facing);
        this.itemStack_1_8 = itemStack;
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }

    public PlayerBlockPlacementC2SPacket(BlockPos blockPos, Facing facing, Hand1_9 hand, float facingX, float facingY, float facingZ) {
        this.blockPos = blockPos;
        this.facing = new ModelMapper<>(FunctionalByteBuf::writeByte, facing);
        this.hand_1_9 = new ModelMapper<>(FunctionalByteBuf::writeVarInt, hand);
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeLong(this.blockPos.toLong());
        this.facing.write(buf);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.hand_1_9.write(buf);
        }
        if (buf.getProtocolVersion().isOlderThanOrEqualTo(ProtocolCollection.R1_8)) {
            buf.writeItemStack(this.itemStack_1_8);
        }
        buf.writeByte((int) (this.facingX * 16.0F));
        buf.writeByte((int) (this.facingY * 16.0F));
        buf.writeByte((int) (this.facingZ * 16.0F));
    }

    @Override
    public String toString() {
        return "PlayerBlockPlacementC2SPacket{" +
                "blockPos=" + blockPos +
                ", facing=" + facing +
                ", hand_1_9=" + hand_1_9 +
                ", itemStack_1_8=" + itemStack_1_8 +
                ", facingX=" + facingX +
                ", facingY=" + facingY +
                ", facingZ=" + facingZ +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerBlockPlacementC2SPacket that = (PlayerBlockPlacementC2SPacket) o;

        if (Float.compare(that.facingX, facingX) != 0) return false;
        if (Float.compare(that.facingY, facingY) != 0) return false;
        if (Float.compare(that.facingZ, facingZ) != 0) return false;
        if (!Objects.equals(blockPos, that.blockPos)) return false;
        if (!Objects.equals(facing, that.facing)) return false;
        if (!Objects.equals(hand_1_9, that.hand_1_9)) return false;
        return Objects.equals(itemStack_1_8, that.itemStack_1_8);
    }

    @Override
    public int hashCode() {
        int result = blockPos != null ? blockPos.hashCode() : 0;
        result = 31 * result + (facing != null ? facing.hashCode() : 0);
        result = 31 * result + (hand_1_9 != null ? hand_1_9.hashCode() : 0);
        result = 31 * result + (itemStack_1_8 != null ? itemStack_1_8.hashCode() : 0);
        result = 31 * result + (facingX != +0.0f ? Float.floatToIntBits(facingX) : 0);
        result = 31 * result + (facingY != +0.0f ? Float.floatToIntBits(facingY) : 0);
        result = 31 * result + (facingZ != +0.0f ? Float.floatToIntBits(facingZ) : 0);
        return result;
    }
}
