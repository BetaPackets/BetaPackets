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

package de.florianmichael.betapackets.model.world.chunk;

import java.util.Arrays;

public class ChunkData {

    public int dataSize;
    public byte[] data;

    public ChunkData() {
    }

    public ChunkData(int dataSize, byte[] data) {
        this.dataSize = dataSize;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChunkData{" +
                "dataSize=" + dataSize +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkData chunkData = (ChunkData) o;

        if (dataSize != chunkData.dataSize) return false;
        return Arrays.equals(data, chunkData.data);
    }

    @Override
    public int hashCode() {
        int result = dataSize;
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
