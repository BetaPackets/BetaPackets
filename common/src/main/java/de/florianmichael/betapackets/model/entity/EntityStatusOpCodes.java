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

public enum EntityStatusOpCodes {

    // v1.8
    RESET_MOB_SPAWN_MINE_CART_TIMER_OR_RABBIT_JUMP_ANIMATION,
    LIVING_ENTITY_HURT,
    LIVING_ENTITY_DEAD,
    IRON_GOLEM_THROWING_UP_ARMS,
    WOLF_OCELOT_HORSE_TAMING_SPAWN_HEART_PARTICLES,
    WOLF_OCELOT_HORSE_TAMED_SPAWN_SMOKE_PARTICLES,
    WOLF_SHAKING_WATER_TRIGGER_THE_SHAKING_ANIMATION,
    PLAYER_SELF_EATING_ACCEPTED_BY_SERVER,
    SHEEP_EATING_GRASS,
    PLAY_TNT_IGNITE_SOUND,
    IRON_GOLEM_HANDING_OVER_A_ROSE,
    VILLAGER_MATING_SPAWN_HEART_PARTICLES,
    SPAWN_PARTICLES_INDICATING_THAT_A_VILLAGER_IS_ANGRY_AND_SEEKING_REVENGE,
    SPAWN_HAPPY_PARTICLES_NEAR_A_VILLAGER,
    WITCH_ANIMATION_SPAWN_MAGIC_PARTICLES,
    PLAY_ZOMBIE_CONVERTING_INTO_A_VILLAGER_SOUND,
    FIREWORK_EXPLODING,
    ANIMAL_IN_LOVE_READY_TO_MATE_SPAWN_HEART_PARTICLES,
    RESET_SQUID_ROTATION,
    SPAWN_EXPLOSION_PARTICLE_WORKS_FOR_SOME_LIVING_ENTITIES,
    PLAY_GUARDIAN_SOUND_WORKS_FOR_ONLY_GUARDIANS,
    ENABLES_REDUCED_DEBUG_FOR_PLAYERS,
    DISABLES_REDUCED_DEBUG_FOR_PLAYERS,

    // v1.9
    SET_OP_PERMISSION_LEVEL_0_FOR_PLAYERS,
    SET_OP_PERMISSION_LEVEL_1_FOR_PLAYERS,
    SET_OP_PERMISSION_LEVEL_2_FOR_PLAYERS,
    SET_OP_PERMISSION_LEVEL_3_FOR_PLAYERS,
    SET_OP_PERMISSION_LEVEL_4_FOR_PLAYERS;

    public static EntityStatusOpCodes getById(final ProtocolCollection version, final byte id) {
        for (EntityStatusOpCodes value : values()) {
            if (value.ordinal() == id) return value;
        }
        return null;
    }
}
