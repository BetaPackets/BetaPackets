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

package org.betapackets.betapackets.model.position;

import org.betapackets.betapackets.model.base.VersionEnum;

public enum Facing {

    DOWN(1, new Vec3i(0, -1, 0)),
    UP(0, new Vec3i(0, 1, 0)),
    NORTH(3, new Vec3i(0, 0, -1)),
    SOUTH(2, new Vec3i(0, 0, 1)),
    WEST(5, new Vec3i(-1, 0, 0)),
    EAST(4, new Vec3i(1, 0, 0));

    public final int opposite;
    public final Vec3i offset;

    public final static Facing[] HORIZONTALS = new Facing[]{ SOUTH, WEST, NORTH, EAST };

    Facing(int opposite, Vec3i offset) {
        this.opposite = opposite;
        this.offset = offset;
    }

    public static Facing getHorizontalById(final int id) {
        return HORIZONTALS[Math.abs(id % HORIZONTALS.length)];
    }

    public static Facing getByName(String name) {
        return valueOf(name.toUpperCase());
    }

    public static Facing getById(final VersionEnum version, final byte id) {
        for (Facing facing : values()) {
            if (facing.ordinal() == id) return facing;
        }

        return null;
    }
}
