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

package de.florianmichael.betapackets.model.world.chunk.palette;

import de.florianmichael.betapackets.model.world.chunk.storage.BitStorage;
import de.florianmichael.betapackets.model.world.chunk.storage.Storage;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;

public class PalettedContainer {

    private Palette palette;
    private PaletteProvider provider;
    private Storage storage;

    public void read(FunctionalByteBuf buf, PaletteProvider paletteProvider) {
        provider = paletteProvider;
        int bits = buf.readByte();
        palette = paletteProvider.readPalette(buf, bits);
        int storageLength = buf.readVarInt();
        if (!(palette instanceof SingularPalette)) {
            long[] longs = new long[storageLength];
            for (int i = 0; i < longs.length; i++)
                longs[i] = buf.readLong();
            storage = new BitStorage(bits, paletteProvider.getStorage(), longs);
        }
    }

    // https://github.com/retrooper/packetevents/blob/2.0/api/src/main/java/com/github/retrooper/packetevents/protocol/world/chunk/palette/DataPalette.java#L152
    private int sanitizeBitsPerEntry(int bitsPerEntry) {
        if (bitsPerEntry <= provider.getMaxBits()) {
            return Math.max(provider.getMinBits(), bitsPerEntry);
        } else {
            return 14;
        }
    }

    // https://github.com/retrooper/packetevents/blob/2.0/api/src/main/java/com/github/retrooper/packetevents/protocol/world/chunk/palette/DataPalette.java#L160
    public void set(int x, int y, int z, int registryId) {
        int storageId = palette.getStorageId(registryId);
        if (storageId == -1) {
            Palette oldPalette = this.palette;
            Storage oldData = this.storage;

            int bitsPerEntry = sanitizeBitsPerEntry(oldPalette instanceof SingularPalette ? 1 : oldData.getBitsPerEntry() + 1);
            this.palette = provider.createPalette(bitsPerEntry);
            this.storage = new BitStorage(bitsPerEntry, provider.getStorage(), null);

            if (oldPalette instanceof SingularPalette) {
                this.palette.getStorageId(oldPalette.getRegistryId(0));
            } else {
                for (int i = 0; i < provider.getStorage(); i++) {
                    this.storage.set(i, this.palette.getStorageId(oldPalette.getRegistryId(oldData.get(i))));
                }
            }
            storageId = palette.getStorageId(registryId);
        }
        if (storage != null) {
            storage.set(y << 8 | z << 4 | x, storageId);
        }
    }

    public int get(int x, int y, int z) {
        if (storage != null) {
            int id = this.storage.get(y << 8 | z << 4 | x);
            return this.palette.getRegistryId(id);
        } else {
            return palette.getRegistryId(0);
        }
    }

    public Palette getPalette() {
        return palette;
    }

    public Storage getStorage() {
        return storage;
    }
}
