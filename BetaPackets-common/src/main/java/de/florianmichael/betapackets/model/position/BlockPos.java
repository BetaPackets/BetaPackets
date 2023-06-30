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

package de.florianmichael.betapackets.model.position;

public class BlockPos extends Vec3i {

    private final static int NUM_X_BITS = 26 /* 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000)) */;
    private final static int NUM_Z_BITS = NUM_X_BITS;
    private final static int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
    private final static int X_SHIFT = NUM_Z_BITS + NUM_Y_BITS;

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public static BlockPos fromLong(long serialized) {
        final int x = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
        final int y = (int)(serialized << 64 - NUM_Z_BITS - NUM_Y_BITS >> 64 - NUM_Y_BITS);
        final int z = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);

        return new BlockPos(x, y, z);
    }

    private final static long X_MASK = (1L << NUM_X_BITS) - 1L;
    private final static long Y_MASK = (1L << NUM_Y_BITS) - 1L;
    private final static long Z_MASK = (1L << NUM_Z_BITS) - 1L;

    public long toLong() {
        return ((long)this.x & X_MASK) << X_SHIFT | ((long)this.y & Y_MASK) << NUM_Z_BITS | ((long) this.z & Z_MASK);
    }

    @Override
    public String toString() {
        return "BlockPos{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
