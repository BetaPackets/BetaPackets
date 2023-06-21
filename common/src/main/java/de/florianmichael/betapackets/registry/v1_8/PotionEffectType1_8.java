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

package de.florianmichael.betapackets.registry.v1_8;

import de.florianmichael.betapackets.base.registry.model.IPotionEffectType;

public enum PotionEffectType1_8 implements IPotionEffectType {
    SPEED,
    SLOWNESS,
    HASTE,
    MINING_FATIGUE,
    STRENGTH,
    INSTANT_HEALTH,
    INSTANT_DAMAGE,
    JUMP_BOOST,
    NAUSEA,
    REGENERATION,
    RESISTANCE,
    FIRE_RESISTANCE,
    WATER_BREATHING,
    INVISIBILITY,
    BLINDNESS,
    NIGHT_VISION,
    HUNGER,
    WEAKNESS,
    POISON,
    WITHER,
    HEALTH_BOOST,
    ABSORPTION,
    SATURATION,
    GLOWING,
    LEVITATION,
    LUCK,
    UNLUCK,
    SLOW_FALLING,
    CONDUIT_POWER,
    DOLPHINS_GRACE,
    BAD_OMEN,
    HERO_OF_THE_VILLAGE,
    DARKNESS,

    NONE;

    public final String name;

    PotionEffectType1_8() {
        this.name = this.name().toLowerCase();
    }

    @Override
    public IPotionEffectType getByIndex(int id) {
        for (IPotionEffectType type : values()) {
            if (type.getIndex() == id && type != NONE) return type;
        }
        return null;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }
}
