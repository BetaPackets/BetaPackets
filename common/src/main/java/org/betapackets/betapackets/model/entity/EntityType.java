/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.model.entity;

public enum EntityType {

    ALLAY(EntityBase.LIVING),
    AREA_EFFECT_CLOUD(EntityBase.BASE),
    ARMOR_STAND(EntityBase.LIVING),
    ARROW(EntityBase.BASE),
    AXOLOTL(EntityBase.LIVING),
    BAT(EntityBase.LIVING),
    BEE(EntityBase.LIVING),
    BLAZE(EntityBase.LIVING),
    BLOCK_DISPLAY(EntityBase.BASE),
    BOAT(EntityBase.BASE),
    CAMEL(EntityBase.LIVING),
    CAT(EntityBase.LIVING),
    CAVE_SPIDER(EntityBase.LIVING),
    CHEST_BOAT(EntityBase.BASE),
    CHEST_MINECART(EntityBase.BASE),
    MUSHROOM_COW(EntityBase.LIVING),
    CHICKEN(EntityBase.LIVING),
    COD(EntityBase.LIVING),
    COMMAND_BLOCK_MINECART(EntityBase.BASE),
    COW(EntityBase.LIVING),
    CREEPER(EntityBase.LIVING),
    DOLPHIN(EntityBase.LIVING),
    DONKEY(EntityBase.LIVING),
    DRAGON_FIREBALL(EntityBase.BASE),
    DROWNED(EntityBase.LIVING),
    EGG(EntityBase.BASE),
    ELDER_GUARDIAN(EntityBase.LIVING),
    END_CRYSTAL(EntityBase.BASE),
    ENDER_DRAGON(EntityBase.LIVING),
    ENDER_PEARL(EntityBase.BASE),
    ENDERMAN(EntityBase.LIVING),
    ENDERMITE(EntityBase.LIVING),
    EVOKER(EntityBase.LIVING),
    EVOKER_FANGS(EntityBase.BASE),
    EXPERIENCE_BOTTLE(EntityBase.BASE),
    EXPERIENCE_ORB(EntityBase.EXPERIENCE_ORB),
    EYE_OF_ENDER(EntityBase.BASE),
    FALLING_BLOCK(EntityBase.BASE),
    FIREBALL(EntityBase.BASE),
    FIREWORK_ROCKET(EntityBase.BASE),
    FISHING_BOBBER(EntityBase.BASE),
    FOX(EntityBase.LIVING),
    FROG(EntityBase.LIVING),
    FURNACE_MINECART(EntityBase.BASE),
    GHAST(EntityBase.LIVING),
    GIANT(EntityBase.LIVING),
    GLOW_ITEM_FRAME(EntityBase.BASE),
    GLOW_SQUID(EntityBase.LIVING),
    GOAT(EntityBase.LIVING),
    GUARDIAN(EntityBase.LIVING),
    HOGLIN(EntityBase.LIVING),
    HOPPER_MINECART(EntityBase.BASE),
    HORSE(EntityBase.LIVING),
    HUSK(EntityBase.LIVING),
    ILLUSIONER(EntityBase.LIVING),
    INTERACTION(EntityBase.BASE),
    IRON_GOLEM(EntityBase.LIVING),
    ITEM(EntityBase.BASE),
    ITEM_DISPLAY(EntityBase.BASE),
    ITEM_FRAME(EntityBase.BASE),
    LEASH_KNOT(EntityBase.BASE),
    LIGHTNING_BOLT(EntityBase.BASE),
    LLAMA(EntityBase.LIVING),
    LLAMA_SPIT(EntityBase.BASE),
    MAGMA_CUBE(EntityBase.LIVING),
    MARKER(EntityBase.BASE),
    MINECART(EntityBase.BASE),
    MOOSHROOM(EntityBase.LIVING),
    MULE(EntityBase.LIVING),
    OCELOT(EntityBase.LIVING),
    PAINTING(EntityBase.PAINTING),
    PANDA(EntityBase.LIVING),
    PARROT(EntityBase.LIVING),
    PHANTOM(EntityBase.LIVING),
    PIG(EntityBase.LIVING),
    PIGLIN(EntityBase.LIVING),
    PIGLIN_BRUTE(EntityBase.LIVING),
    PILLAGER(EntityBase.LIVING),
    PLAYER(EntityBase.PLAYER),
    POLAR_BEAR(EntityBase.LIVING),
    POTION(EntityBase.BASE),
    PUFFERFISH(EntityBase.LIVING),
    RABBIT(EntityBase.LIVING),
    RAVAGER(EntityBase.LIVING),
    SALMON(EntityBase.LIVING),
    SHEEP(EntityBase.LIVING),
    SHULKER(EntityBase.LIVING),
    SHULKER_BULLET(EntityBase.BASE),
    SILVERFISH(EntityBase.LIVING),
    SKELETON(EntityBase.LIVING),
    SKELETON_HORSE(EntityBase.LIVING),
    SLIME(EntityBase.LIVING),
    SMALL_FIREBALL(EntityBase.BASE),
    SNIFFER(EntityBase.LIVING),
    SNOW_GOLEM(EntityBase.LIVING),
    SNOWBALL(EntityBase.BASE),
    SPAWNER_MINECART(EntityBase.BASE),
    SPECTRAL_ARROW(EntityBase.BASE),
    SPIDER(EntityBase.LIVING),
    SQUID(EntityBase.LIVING),
    STRAY(EntityBase.LIVING),
    STRIDER(EntityBase.LIVING),
    TADPOLE(EntityBase.LIVING),
    TEXT_DISPLAY(EntityBase.BASE),
    TNT(EntityBase.BASE),
    TNT_MINECART(EntityBase.BASE),
    TRADER_LLAMA(EntityBase.LIVING),
    TRIDENT(EntityBase.BASE),
    TROPICAL_FISH(EntityBase.LIVING),
    TURTLE(EntityBase.LIVING),
    VEX(EntityBase.LIVING),
    VILLAGER(EntityBase.LIVING),
    VINDICATOR(EntityBase.LIVING),
    WANDERING_TRADER(EntityBase.LIVING),
    WARDEN(EntityBase.LIVING),
    WITCH(EntityBase.LIVING),
    WITHER(EntityBase.LIVING),
    WITHER_SKELETON(EntityBase.LIVING),
    WITHER_SKULL(EntityBase.BASE),
    WOLF(EntityBase.LIVING),
    ZOGLIN(EntityBase.LIVING),
    ZOMBIE(EntityBase.LIVING),
    ZOMBIE_HORSE(EntityBase.LIVING),
    ZOMBIE_VILLAGER(EntityBase.LIVING),
    ZOMBIFIED_PIGLIN(EntityBase.LIVING);

    private EntityBase base;

    EntityType(EntityBase base) {
        this.base = base;
    }

    public EntityBase getBase() {
        return base;
    }
}
