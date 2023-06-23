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

package de.florianmichael.betapackets.model.game.hud;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum InventorySlot {
    MAIN_HAND,
    OFF_HAND, // added in v1.9
    ARMOR_SLOT_BOOTS,
    ARMOR_SLOT_LEGGINGS,
    ARMOR_SLOT_CHESTPLATE,
    ARMOR_SLOT_HELMET;

    public static InventorySlot getById(final ProtocolCollection version, final short id) {
        for (InventorySlot value : values()) {
            final int fieldId = value.getId(version);
            if (fieldId == -1) continue;

            if (fieldId == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        if (version.isOlderThanOrEqualTo(ProtocolCollection.R1_8) && this.ordinal() > 0) {
            if (this == OFF_HAND) return -1;

            return this.ordinal() - 1;
        }
        return this.ordinal();
    }
}
