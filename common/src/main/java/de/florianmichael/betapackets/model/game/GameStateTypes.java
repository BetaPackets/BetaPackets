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

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum GameStateTypes {

    INVALID_BED(0),
    END_RAINING(1),
    BEGIN_RAINING(2),
    CHANGE_GAMEMODE(3),
    ENTER_CREDITS(4),
    DEMO_MESSAGE(5),
    ARROW_HITTING_PLAYER(6),
    FADE_VALUE(7),
    FADE_TIME(8),
    PLAY_MOB_APPEARANCE_EFFECT_AND_SOUND(10);

    public final int id;

    GameStateTypes(final int id) {
        this.id = id;
    }

    public static GameStateTypes getById(final ProtocolCollection version, final short id) {
        for (GameStateTypes value : values()) {
            if (value.getId(version) == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        return this.id;
    }
}
