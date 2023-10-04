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

package org.betapackets.betapackets.model.block.shape.shape;

import org.betapackets.betapackets.model.block.shape.VoxelSet;
import org.betapackets.betapackets.model.block.shape.VoxelShape;

import java.util.Arrays;

public class ArrayVoxelShape extends VoxelShape {

    private final double[] xPoints;
    private final double[] yPoints;
    private final double[] zPoints;

    public ArrayVoxelShape(VoxelSet voxelSet, double[] xPoints, double[] yPoints, double[] zPoints) {
        super(voxelSet);
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.zPoints = zPoints;
    }

    public double[] getxPoints() {
        return xPoints;
    }

    public double[] getyPoints() {
        return yPoints;
    }

    public double[] getzPoints() {
        return zPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrayVoxelShape that = (ArrayVoxelShape) o;
        return Arrays.equals(xPoints, that.xPoints) && Arrays.equals(yPoints, that.yPoints) && Arrays.equals(zPoints, that.zPoints);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(xPoints);
        result = 31 * result + Arrays.hashCode(yPoints);
        result = 31 * result + Arrays.hashCode(zPoints);
        return result;
    }
}
