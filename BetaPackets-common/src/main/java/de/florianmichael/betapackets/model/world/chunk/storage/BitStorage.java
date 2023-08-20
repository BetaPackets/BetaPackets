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
 *
 * This class was copied from
 * https://github.com/GeyserMC/MCProtocolLib/blob/master/src/main/java/com/github/steveice10/mc/protocol/data/game/chunk/BitStorage.java
 */

package de.florianmichael.betapackets.model.world.chunk.storage;

import java.util.Arrays;

public class BitStorage implements Storage {

    private static final int[] MAGIC_VALUES = {
            -1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE,
            0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756,
            0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0,
            390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378,
            306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135,
            0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0,
            204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970,
            178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862,
            0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0,
            138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567,
            126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197,
            0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0,
            104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893,
            97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282,
            0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0,
            84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431,
            79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303,
            0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0,
            70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE,
            0, 5
    };

    private long[] data;
    private int bitsPerEntry;
    private int size;
    private  long maxValue;
    private  int valuesPerLong;
    private  long divideMultiply;
    private  long divideAdd;
    private  int divideShift;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getBitsPerEntry() {
        return bitsPerEntry;
    }

    @Override
    public long[] getData() {
        return data;
    }

    public BitStorage(int bitsPerEntry, int size, long[] data) {
        if (bitsPerEntry < 1 || bitsPerEntry > 32) {
            throw new IllegalArgumentException("bitsPerEntry must be between 1 and 32, inclusive.");
        }

        this.bitsPerEntry = bitsPerEntry;
        this.size = size;

        this.maxValue = (1L << bitsPerEntry) - 1L;
        this.valuesPerLong = (char) (64 / bitsPerEntry);
        int expectedLength = (size + this.valuesPerLong - 1) / this.valuesPerLong;
        if (data != null) {
            if (data.length != expectedLength) {
                // Hypixel as of 1.19.0
                data = Arrays.copyOf(data, expectedLength);
            }

            this.data = data;
        } else {
            this.data = new long[expectedLength];
        }

        int magicIndex = 3 * (this.valuesPerLong - 1);
        this.divideMultiply = Integer.toUnsignedLong(MAGIC_VALUES[magicIndex]);
        this.divideAdd = Integer.toUnsignedLong(MAGIC_VALUES[magicIndex + 1]);
        this.divideShift = MAGIC_VALUES[magicIndex + 2];
    }

    @Override
    public int get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        int cellIndex = cellIndex(index);
        int bitIndex = bitIndex(index, cellIndex);
        return (int) (this.data[cellIndex] >> bitIndex & this.maxValue);
    }

    @Override
    public void set(int index, int value) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (value < 0 || value > this.maxValue) {
            throw new IllegalArgumentException("Value cannot be outside of accepted range.");
        }

        int cellIndex = cellIndex(index);
        int bitIndex = bitIndex(index, cellIndex);
        this.data[cellIndex] = this.data[cellIndex] & ~(this.maxValue << bitIndex) | ((long) value & this.maxValue) << bitIndex;
    }

    private int cellIndex(int index) {
        return (int) (index * this.divideMultiply + this.divideAdd >> 32 >> this.divideShift);
    }

    private int bitIndex(int index, int cellIndex) {
        return (index - cellIndex * this.valuesPerLong) * this.bitsPerEntry;
    }
}
