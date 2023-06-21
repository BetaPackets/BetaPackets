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

import de.florianmichael.betapackets.model.position.BlockPos;

public class BlockChunkRecord1_8 {

    public short chunkPosCrammed;
    public int blockState;

    public BlockChunkRecord1_8(short chunkPosCrammed, int blockState) {
        this.chunkPosCrammed = chunkPosCrammed;
        this.blockState = blockState;
    }

    public BlockPos getPos() {
        return new BlockPos(chunkPosCrammed >> 12 & 15, chunkPosCrammed & 255, chunkPosCrammed >> 8 & 15);
    }

    @Override
    public String toString() {
        return "BlockChunkRecord1_8{" +
                "chunkPosCrammed=" + chunkPosCrammed +
                ", blockState=" + blockState +
                '}';
    }
}
