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

package org.betapackets.betapackets.model.block.shape;

import java.util.Objects;

public class VoxelShape {

    private final VoxelSet voxelSet;

    public VoxelShape(VoxelSet voxelSet) {
        this.voxelSet = voxelSet;
    }

    public VoxelSet getVoxelSet() {
        return voxelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoxelShape that = (VoxelShape) o;
        return Objects.equals(voxelSet, that.voxelSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voxelSet);
    }
}
