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

package de.florianmichael.betapackets.model.world;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum LevelType {

    DEFAULT,
    FLAT,
    LARGE_BIOMES,
    AMPLIFIED,
    CUSTOMIZED,
    DEBUG_WORLD,
    DEFAULT_1_1;

    private String name;

    LevelType() {
        this.name = name().toLowerCase();
        if (this.name.equals("large_biomes")) {
            this.name = "largeBiomes";
        }
        if (this.name.equals("debug_world")) {
            this.name = "debug_all_block_states";
        }
    }

    public static LevelType getByType(final ProtocolCollection version, final String levelType) {
        for (LevelType value : values()) {
            if (value.getName().equals(levelType)) return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
