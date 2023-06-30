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

package de.florianmichael.betapackets.model.potion;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum ParticleTypes {

    // v1.8
    EXPLOSION_NORMAL("explode", true),
    EXPLOSION_LARGE("largeexplode", true),
    EXPLOSION_HUGE("hugeexplosion", true),
    FIREWORKS_SPARK("fireworksSpark", false),
    WATER_BUBBLE("bubble",  false),
    WATER_SPLASH("splash",  false),
    WATER_WAKE("wake", false),
    SUSPENDED("suspended", false),
    SUSPENDED_DEPTH("depthsuspend", false),
    CRIT("crit", false),
    CRIT_MAGIC("magicCrit", false),
    SMOKE_NORMAL("smoke", false),
    SMOKE_LARGE("largesmoke", false),
    SPELL("spell", false),
    SPELL_INSTANT("instantSpell", false),
    SPELL_MOB("mobSpell", false),
    SPELL_MOB_AMBIENT("mobSpellAmbient", false),
    SPELL_WITCH("witchMagic", false),
    DRIP_WATER("dripWater", false),
    DRIP_LAVA("dripLava", false),
    VILLAGER_ANGRY("angryVillager", false),
    VILLAGER_HAPPY("happyVillager", false),
    TOWN_AURA("townaura", false),
    NOTE("note", false),
    PORTAL("portal", false),
    ENCHANTMENT_TABLE("enchantmenttable", false),
    FLAME("flame", false),
    LAVA("lava", false),
    FOOTSTEP("footstep", false),
    CLOUD("cloud", false),
    REDSTONE("reddust", false),
    SNOWBALL("snowballpoof", false),
    SNOW_SHOVEL("snowshovel", false),
    SLIME("slime", false),
    HEART("heart", false),
    BARRIER("barrier", false),
    ITEM_CRACK("iconcrack_", false, 2),
    BLOCK_CRACK("blockcrack_",  false, 1),
    BLOCK_DUST("blockdust_", false, 1),
    WATER_DROP("droplet", false),
    ITEM_TAKE("take", false),
    MOB_APPEARANCE("mobappearance", true),

    //v1.9
    DRAGON_BREATH("dragonbreath", false),
    END_ROD("endRod", false),
    DAMAGE_INDICATOR("damageIndicator", false),
    SWEEP_ATTACK("sweepAttack", false);

    public final String particleName;
    public final boolean shouldIgnoreRange;
    public final int argumentCount;

    ParticleTypes(String particleName, boolean shouldIgnoreRange) {
        this(particleName, shouldIgnoreRange, 0);
    }

    ParticleTypes(String particleName, boolean shouldIgnoreRange, int argumentCount) {
        this.particleName = particleName;
        this.shouldIgnoreRange = shouldIgnoreRange;
        this.argumentCount = argumentCount;
    }

    public static ParticleTypes getById(final ProtocolCollection version, final int id) {
        for (ParticleTypes value : values()) {
            if (value.getId(version) == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        return ordinal();
    }

    public String getParticleName(final ProtocolCollection version) {
        if (version.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            if (this == ITEM_CRACK || this == BLOCK_CRACK || this == BLOCK_DUST) {
                return particleName.replace("_", "");
            }
        }
        return particleName;
    }
}
