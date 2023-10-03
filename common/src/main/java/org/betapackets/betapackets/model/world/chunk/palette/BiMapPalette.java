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
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

public class BiMapPalette implements Palette {

    private int maxId;
    private int[] storage;
    private TIntIntMap registry = new TIntIntHashMap();
    private int newSize;

    public BiMapPalette(int bits) {
        maxId = 1 << bits;
        storage = new int[maxId];
    }

    public BiMapPalette(FunctionalByteBuf buf, int bits) {
        this(bits);
        newSize = buf.readVarInt();
        for (int i = 0; i < newSize; i++) {
            int registryEntry = buf.readVarInt();
            storage[i] = registryEntry;
            registry.put(registryEntry, i);
        }
    }

    @Override
    public int getSize() {
        return newSize;
    }

    @Override
    public int getStorageId(int registryId) {
        int id = -1;
        if (!registry.containsKey(registryId) && newSize < maxId) {
            id = newSize++;
            storage[id] = registryId;
            registry.put(registryId, id);
        } else {
            id = registry.get(registryId);
        }
        return id;
    }

    @Override
    public int getRegistryId(int storageId) {
        if (storageId >= 0 && storageId < newSize) {
            return storage[storageId];
        }
        return 0;
    }
}
