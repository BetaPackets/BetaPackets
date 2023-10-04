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

package org.betapackets.betapackets.model.entity.attribute;

import net.lenni0451.mcstructs.core.Identifier;

public enum EntityAttribute {

    GENERIC_MAX_HEALTH("generic.max_health", 20, 1, 1024),
    GENERIC_FOLLOW_RANGE("generic.follow_range", 32, 0, 2048),
    GENERIC_KNOCKBACK_RESISTANCE("generic.knockback_resistance", 0, 0, 1),
    GENERIC_MOVEMENT_SPEED("generic.movement_speed", 0.7, 0, 1024),
    GENERIC_FLYING_SPEED("generic.flying_speed", 0.4, 0, 1024),
    GENERIC_ATTACK_DAMAGE("generic.attack_damage", 2.0, 0, 2048),
    GENERIC_ATTACK_KNOCKBACK("generic.attack_knockback", 0, 0, 5),
    GENERIC_ATTACK_SPEED("generic.attack_speed", 4.0, 0, 1024),
    GENERIC_ARMOR("generic.armor", 0, 0, 30),
    GENERIC_ARMOR_TOUGHNESS("generic.armor_toughness", 0, 0, 20),
    GENERIC_LUCK("generic.luck", 0, -1024, 1024),
    GENERIC_MAX_ABSORPTION("generic.max_absorption", 0, 0, 2048),
    ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawn_reinforcements", 0, 0, 1),
    HORSE_JUMP_STRENGTH("horse.jump_strength", 0.7, 0, 2);

    private String unlocalizedName;
    private Identifier identifier;
    private double fallback;
    private double min;
    private double max;

    EntityAttribute(String unlocalizedName, double fallback, double min, double max) {
        this.unlocalizedName = unlocalizedName;
        this.identifier = Identifier.of(unlocalizedName);
        this.fallback = fallback;
        this.min = min;
        this.max = max;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public double getFallback() {
        return fallback;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public static EntityAttribute getByIdentifier(Identifier identifier) {
        for (EntityAttribute attribute : values()) {
            if (attribute.getIdentifier().equals(identifier))
                return attribute;
        }
        return null;
    }
}
