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
import java.util.Objects;

public class ExtendedBlockStorage {

    public int y;
    public boolean hasSkyLight;

    public char[] blockStates = new char[4096];

    public NibbleArray blockLightArray = new NibbleArray();
    public NibbleArray skyLightArray;

    public ExtendedBlockStorage(int y, boolean hasSkyLight) {
        this.y = y;
        this.hasSkyLight = hasSkyLight;
        if (hasSkyLight) {
            skyLightArray = new NibbleArray();
        }
    }

    @Override
    public String toString() {
        return "ExtendedBlockStorage{" +
                "y=" + y +
                ", hasSkyLight=" + hasSkyLight +
                ", blockStates=" + Arrays.toString(blockStates) +
                ", blockLightArray=" + blockLightArray +
                ", skyLightArray=" + skyLightArray +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtendedBlockStorage that = (ExtendedBlockStorage) o;

        if (y != that.y) return false;
        if (hasSkyLight != that.hasSkyLight) return false;
        if (!Arrays.equals(blockStates, that.blockStates)) return false;
        if (!Objects.equals(blockLightArray, that.blockLightArray))
            return false;
        return Objects.equals(skyLightArray, that.skyLightArray);
    }

    @Override
    public int hashCode() {
        int result = y;
        result = 31 * result + (hasSkyLight ? 1 : 0);
        result = 31 * result + Arrays.hashCode(blockStates);
        result = 31 * result + (blockLightArray != null ? blockLightArray.hashCode() : 0);
        result = 31 * result + (skyLightArray != null ? skyLightArray.hashCode() : 0);
        return result;
    }
}
