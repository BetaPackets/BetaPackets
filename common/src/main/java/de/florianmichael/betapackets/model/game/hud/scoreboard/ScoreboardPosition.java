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

package de.florianmichael.betapackets.model.game.hud.scoreboard;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum ScoreboardPosition {

    LIST,
    SIDEBAR,
    BELOW_NAME;

    public static ScoreboardPosition getById(final ProtocolCollection version, final byte id) {
        for (ScoreboardPosition value : values()) {
            if (value.getId(version) == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        return this.ordinal();
    }
}
