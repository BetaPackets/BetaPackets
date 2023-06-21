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

import de.florianmichael.betapackets.base.registry.model.IParticleType;

public enum ParticleType1_8 implements IParticleType {
    EXPLOSION_NORMAL("explode", 0, true),
    EXPLOSION_LARGE("largeexplode", 1, true),
    EXPLOSION_HUGE("hugeexplosion", 2, true),
    FIREWORKS_SPARK("fireworksSpark", 3, false),
    WATER_BUBBLE("bubble", 4, false),
    WATER_SPLASH("splash", 5, false),
    WATER_WAKE("wake", 6, false),
    SUSPENDED("suspended", 7, false),
    SUSPENDED_DEPTH("depthsuspend", 8, false),
    CRIT("crit", 9, false),
    CRIT_MAGIC("magicCrit", 10, false),
    SMOKE_NORMAL("smoke", 11, false),
    SMOKE_LARGE("largesmoke", 12, false),
    SPELL("spell", 13, false),
    SPELL_INSTANT("instantSpell", 14, false),
    SPELL_MOB("mobSpell", 15, false),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false),
    SPELL_WITCH("witchMagic", 17, false),
    DRIP_WATER("dripWater", 18, false),
    DRIP_LAVA("dripLava", 19, false),
    VILLAGER_ANGRY("angryVillager", 20, false),
    VILLAGER_HAPPY("happyVillager", 21, false),
    TOWN_AURA("townaura", 22, false),
    NOTE("note", 23, false),
    PORTAL("portal", 24, false),
    ENCHANTMENT_TABLE("enchantmenttable", 25, false),
    FLAME("flame", 26, false),
    LAVA("lava", 27, false),
    FOOTSTEP("footstep", 28, false),
    CLOUD("cloud", 29, false),
    REDSTONE("reddust", 30, false),
    SNOWBALL("snowballpoof", 31, false),
    SNOW_SHOVEL("snowshovel", 32, false),
    SLIME("slime", 33, false),
    HEART("heart", 34, false),
    BARRIER("barrier", 35, false),
    ITEM_CRACK("iconcrack_", 36, false, 2),
    BLOCK_CRACK("blockcrack_", 37, false, 1),
    BLOCK_DUST("blockdust_", 38, false, 1),
    WATER_DROP("droplet", 39, false),
    ITEM_TAKE("take", 40, false),
    MOB_APPEARANCE("mobappearance", 41, true),

    NONE("none", Integer.MAX_VALUE, false);

    public final String particleName;
    public final int particleID;
    public final boolean shouldIgnoreRange;
    public final int argumentCount;

    ParticleType1_8(String particleName, int particleID, boolean shouldIgnoreRange) {
        this(particleName, particleID, shouldIgnoreRange, 0);
    }

    ParticleType1_8(String particleName, int particleID, boolean shouldIgnoreRange, int argumentCount) {
        this.particleName = particleName;
        this.particleID = particleID;
        this.shouldIgnoreRange = shouldIgnoreRange;
        this.argumentCount = argumentCount;
    }

    @Override
    public IParticleType getByIndex(int index) {
        for (ParticleType1_8 value : values()) {
            if (value.particleID == index && value != NONE) return value;
        }
        return null;
    }

    @Override
    public int getIndex() {
        return particleID;
    }

    @Override
    public String getName() {
        return particleName;
    }

    @Override
    public boolean shouldIgnoreRange() {
        return shouldIgnoreRange;
    }

    @Override
    public int getArgumentCount() {
        return argumentCount;
    }
}
