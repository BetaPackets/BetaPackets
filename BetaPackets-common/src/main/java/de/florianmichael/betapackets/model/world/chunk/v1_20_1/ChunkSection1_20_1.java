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

package de.florianmichael.betapackets.model.world.chunk.v1_20_1;

import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.block.BlockState;
import de.florianmichael.betapackets.model.world.chunk.ChunkSection;
import de.florianmichael.betapackets.model.world.chunk.palette.PaletteProvider;
import de.florianmichael.betapackets.model.world.chunk.palette.PalettedContainer;
import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;

public class ChunkSection1_20_1 implements ChunkSection {

    private ProtocolCollection version;
    private short nonEmptyBlockCount;
    private PalettedContainer blockStateContainer = new PalettedContainer();
    private PalettedContainer biomeContainer = new PalettedContainer();

    public void read(FunctionalByteBuf buf) {
        version = buf.getProtocolVersion();
        nonEmptyBlockCount = buf.readShort();
        blockStateContainer.read(buf, PaletteProvider.CHUNK);
        biomeContainer.read(buf, PaletteProvider.BIOME);
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return version.getBlockStateMapping().getById(blockStateContainer.get(x, y, z));
    }

    public PalettedContainer getBlockStateContainer() {
        return blockStateContainer;
    }

    public PalettedContainer getBiomeContainer() {
        return biomeContainer;
    }
}
