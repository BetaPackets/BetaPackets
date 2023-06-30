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

package de.florianmichael.betapackets.model.entity;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum EntityActionIDs {

    // v1.8
    START_SNEAKING(0, 0),
    STOP_SNEAKING(1, 1),
    LEAVE_BED(2, -1),
    START_SPRINTING(3, 3),
    STOP_SPRINTING(4, 4),
    JUMP_WITH_HORSE(5, -1),
    OPEN_RIDDEN_HORSE_INVENTORY(6, 7),

    // v1.9
    STOP_SLEEPING(-1, 2),
    START_RIDING_JUMP(-1, 5),
    STOP_RIDING_JUMP(-1, 6),
    START_FALL_FLYING(-1, 8);

    private final int v1_8Id;
    private final int v1_9Id;

    EntityActionIDs(int v1_8Id, int v1_9Id) {
        this.v1_8Id = v1_8Id;
        this.v1_9Id = v1_9Id;
    }

    public static EntityActionIDs getById(final ProtocolCollection version, final int id) {
        for (EntityActionIDs value : values()) {
            final int fieldId = value.getId(version);
            if (fieldId == -1) continue;
            if (fieldId == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        if (version.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return v1_9Id;
        }
        return v1_8Id;
    }
}
