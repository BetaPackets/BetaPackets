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

package de.florianmichael.betapackets.model.game;

import java.util.EnumSet;
import java.util.Set;

public enum PositionFlags {

    X,
    Y,
    Z,
    Y_ROT,
    X_ROT;

    private int getId() {
        return 1 << this.ordinal();
    }

    private boolean isSameID(int inputId) {
        return (inputId & this.getId()) == this.getId();
    }

    public static Set<PositionFlags> getFlags(int flags) {
        final Set<PositionFlags> set = EnumSet.noneOf(PositionFlags.class);
        for (PositionFlags positionFlag : values()) {
            if (positionFlag.isSameID(flags)) set.add(positionFlag);
        }
        return set;
    }

    public static int merge(final Set<PositionFlags> flags) {
        int i = 0;
        for (PositionFlags positionFlag : flags) {
            i |= positionFlag.getId();
        }
        return i;
    }
}
