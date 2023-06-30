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

package de.florianmichael.betapackets.model.entity.metadata;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum MetadataAction {

    SET_SPAWN_POTENTIALS_OF_MOB_SPAWNER,
    SET_COMMAND_BLOCK_TEXT_AND_LAST_EXECUTION_STATUS,
    SET_LEVEL_AND_PRIMARY_AND_SECONDARY_POWERS_OF_BEACON,
    SET_ROTATION_AND_SKIN_MOB_HEAD,
    SET_TYPE_OF_FLOWER_FLOWER_POT,
    SET_BASE_COLOR_AND_PATTERNS_BANNER;

    public static MetadataAction getById(final ProtocolCollection version, final short id) {
        for (MetadataAction value : values()) {
            if (value.getId() == id) return value;
        }
        return null;
    }

    public int getId() {
        return ordinal();
    }
}
