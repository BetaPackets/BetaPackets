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

package org.betapackets.betapackets.model.world.chunk.palette;

import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;

public class ArrayPalette implements Palette {

    private int[] ids;
    private int maxId;
    private int newSize;

    public ArrayPalette(int bits) {
        maxId = 1 << bits;
        ids = new int[maxId];
    }

    public ArrayPalette(FunctionalByteBuf buf, int bits) {
        this(bits);
        newSize = buf.readVarInt();
        for (int i = 0; i < newSize; i++)
            ids[i] = buf.readVarInt();
    }

    @Override
    public int getSize() {
        return newSize;
    }

    @Override
    public int getStorageId(int registryId) {
        int id = -1;
        for (int i = 0; i < newSize; i++) { // Linear search for state
            if (this.ids[i] == registryId) {
                return i;
            }
        }
        if (newSize < this.maxId) {
            id = newSize++;
            ids[id] = registryId;
        }

        return id;
    }

    @Override
    public int getRegistryId(int storageId) {
        if (storageId >= 0 && storageId < ids.length) {
            return ids[storageId];
        }
        return 0;
    }
}
