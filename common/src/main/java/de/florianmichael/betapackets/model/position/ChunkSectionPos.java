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

package de.florianmichael.betapackets.model.position;

public class ChunkSectionPos extends BlockPos {

    public ChunkSectionPos(BlockPos pos) {
        super(pos.x, pos.y, pos.z);
    }

    public ChunkSectionPos(int x, int y, int z) {
        super(x, y, z);
    }

    public short getIdByPos(BlockPos pos) {
        return (short) ((pos.x & 15) << 8 | (pos.y & 15) << 4 | (pos.z & 15) << 0);
    }

    public BlockPos getPosById(short id) {
        return new BlockPos(
                getBlockX() + (id >>> 8 & 15),
                getBlockY() + (id >>> 0 & 15),
                getBlockZ() + (id >>> 4 & 15)
        );
    }

    public int getBlockX() {
        return x << 4;
    }

    public int getBlockY() {
        return y << 4;
    }

    public int getBlockZ() {
        return z << 4;
    }

}
