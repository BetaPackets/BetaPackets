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

package org.betapackets.betapackets.model.world.chunk.v1_20_1;

import org.betapackets.betapackets.model.base.VersionEnum;
import org.betapackets.betapackets.model.block.BlockState;
import org.betapackets.betapackets.model.world.chunk.ChunkSection;
import org.betapackets.betapackets.model.world.chunk.palette.PaletteProvider;
import org.betapackets.betapackets.model.world.chunk.palette.PalettedContainer;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;

public class ChunkSection1_20_1 implements ChunkSection {

    private VersionEnum version;
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

    @Override
    public void setBlockState(int x, int y, int z, BlockState state) {
        blockStateContainer.set(x, y, z, state.getId());
    }

    public PalettedContainer getBlockStateContainer() {
        return blockStateContainer;
    }

    public PalettedContainer getBiomeContainer() {
        return biomeContainer;
    }
}
